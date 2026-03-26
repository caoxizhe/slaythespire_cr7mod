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
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int BASE_DAMAGE = 18;
    private static final int UPGRADE_DAMAGE = 6;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final int BASE_FANS = 5;

    public HeavyShoot() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int actualDamage = this.damage;
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                m,
                new DamageInfo(p, actualDamage, this.damageTypeForTurn),
                AttackEffect.BLUNT_HEAVY
            )
        );
        
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new FansPower(p, BASE_FANS), BASE_FANS)
        );
    }

    @Override
    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        int oldBase = this.baseDamage;
        if (p != null) {
            int strength = 0;
            if (p.hasPower("Strength")) {
                AbstractPower sp = p.getPower("Strength");
                if (sp != null) strength = sp.amount;
            }
            this.baseDamage = oldBase + strength * (this.magicNumber - 1);
        }
        super.applyPowers();
        this.baseDamage = oldBase;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPlayer p = AbstractDungeon.player;
        int oldBase = this.baseDamage;
        if (p != null) {
            int strength = 0;
            if (p.hasPower("Strength")) {
                AbstractPower sp = p.getPower("Strength");
                if (sp != null) strength = sp.amount;
            }
            this.baseDamage = oldBase + strength * (this.magicNumber - 1);
        }
        super.calculateCardDamage(mo);
        this.baseDamage = oldBase;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeavyShoot();
    }
}
