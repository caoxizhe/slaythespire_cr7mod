package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import cr7mod.characters.CR7Character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import cr7mod.powers.VolleyShootPower;

public class VolleyShoot extends CustomCard {
    public static final String ID = "CR7Mod:VolleyShoot";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/volley_shoot.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int BASE_DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int EVENT_DAMAGE = 5;
    private static final int EVENT_DAMAGE_UP = 3;

    public VolleyShoot() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = EVENT_DAMAGE;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                m, 
                new DamageInfo(p, this.damage, this.damageTypeForTurn), 
                AttackEffect.SLASH_HORIZONTAL
            )
        );

        // apply or stack VolleyShootPower with this card's event damage
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new VolleyShootPower(p, this.magicNumber), this.magicNumber)
        );
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(EVENT_DAMAGE_UP);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new VolleyShoot();
    }
}
