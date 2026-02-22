package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class HeavyShoot extends CustomCard {
    public static final String ID = "CR7Mod:HeavyShoot";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/heavy_shoot.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int BASE_DAMAGE = 18;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int ADD_DAMAGE = 20;
    private static final int BASE_FANS = 3;
    private static final int UPGRADE_FANS = 2;
    private static final int FANS_THRESHOLD = 15;

    public HeavyShoot() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = BASE_FANS;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int fans = 0;
        if (p.hasPower(FansPower.POWER_ID)) fans = p.getPower(FansPower.POWER_ID).amount;

        int actualDamage = this.damage;
        if (fans >= FANS_THRESHOLD) {
            actualDamage += ADD_DAMAGE; 
        }

        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                m,
                new DamageInfo(p, actualDamage, this.damageTypeForTurn),
                AttackEffect.BLUNT_HEAVY
            )
        );
        
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new FansPower(p, this.magicNumber), this.magicNumber)
        );
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            AbstractPower fp = p.getPower(FansPower.POWER_ID);
            if (fp != null && fp.amount >= FANS_THRESHOLD) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_FANS);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeavyShoot();
    }
}
