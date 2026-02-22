package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import cr7mod.characters.CR7Character;
import com.megacrit.cardcrawl.cards.AbstractCard;
import cr7mod.powers.FansPower;

public class BicycleKick extends CustomCard {
    public static final String ID = "CR7Mod:BicycleKick";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/bicycle_kick.png";
    private static final int COST = 4;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int BASE_DAMAGE = 25;
    private static final int UPGRADE_DAMAGE = 5;
    private static final int FANS_GAIN = 7;
    private static final int BASE_VULNERABLE = 1;
    private static final int UPGRADE_VULNERABLE = 1;

    public BicycleKick() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = BASE_VULNERABLE;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                m, 
                new DamageInfo(p, this.damage, this.damageTypeForTurn), 
                AttackEffect.BLUNT_HEAVY
            )
        );
        // Apply Vulnerable (magicNumber)
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber)
        );
        // Give player Fans
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new FansPower(p, FANS_GAIN), FANS_GAIN)
        );
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        // Dynamic cost reduction: each 7 Fans reduces cost by 1
        int fansAmount = 0;
        if (AbstractDungeon.player != null) {
            AbstractPower fans = AbstractDungeon.player.getPower(FansPower.POWER_ID);
            fansAmount = fans == null ? 0 : fans.amount;
        }
        int reduction = fansAmount / 7;
        int newCostForTurn = Math.max(0, this.cost - reduction);
        this.costForTurn = newCostForTurn;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_VULNERABLE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BicycleKick();
    }
}
