package cr7mod.cards;

import basemod.abstracts.CustomCard;
import cr7mod.characters.CR7Character;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.powers.RooneyPower;


public class Rooney extends CustomCard {
    public static final String ID = "CR7Mod:Rooney";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/rooney.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int BASE_DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int HITS = 3;  
    private static final int BASE_MAGIC = 1;

    public Rooney() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (int i = 0; i < HITS; i++) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageRandomEnemyAction(
                    new DamageInfo(p, this.damage, DamageInfo.DamageType.THORNS), 
                    AttackEffect.LIGHTNING
                )
            );
        }

        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new RooneyPower(p, this.magicNumber), this.magicNumber)
        );
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rooney();
    }
}
