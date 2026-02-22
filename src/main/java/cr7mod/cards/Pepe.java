package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class Pepe extends CustomCard {
    public static final String ID = "CR7Mod:Pepe";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/pepe.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int FANS_LOSE = 30;
    private static final int BASE_DAMAGE = 70;
    private static final int UPG_DAMAGE = 30; 
    private static final int STRENGTH_LOSS = 7;

    public Pepe() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = STRENGTH_LOSS;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.cardsToPreview = new RedCard();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Lose Fans
        AbstractDungeon.actionManager.addToBottom(
            new ReducePowerAction(p, p, FansPower.POWER_ID, FANS_LOSE)
        );
        // Deal damage to target
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                m, 
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.BLUNT_HEAVY
            )
        );
        // Permanently reduce Strength of all enemies by magicNumber
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom().monsters.monsters)) {
            if (!mo.isDead && !mo.isDying) {
                AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber)
                );
            }
        }
        // Add a Red Card into your hand
        AbstractDungeon.actionManager.addToBottom(
            new MakeTempCardInHandAction(new RedCard(), 1)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        AbstractPower power = p.getPower(FansPower.POWER_ID);
        int currentFans = 0;
        if (power != null) {
            currentFans = power.amount;
        }
        if (currentFans < FANS_LOSE) {
            UIStrings ui = CardCrawlGame.languagePack.getUIString("CR7Mod:NotEnoughFans");
            if (ui != null && ui.TEXT != null && ui.TEXT.length > 0) {
                this.cantUseMessage = ui.TEXT[0];
            }   
            return false;
        }
        return true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pepe();
    }
}
