package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import cr7mod.characters.CR7Character;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

public class Hired extends CustomCard {
    public static final String ID = "CR7Mod:Hired";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/hired.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int DAMAGE_PER_50 = 7;
    private static final int UPGRADE_DAMAGE_PER_50 = 3;

    public Hired() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 0; // computed on use
        this.damage = this.baseDamage;
        this.magicNumber = DAMAGE_PER_50;
        this.baseMagicNumber = this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int gold = AbstractDungeon.player.gold;
        int stacks = gold / 50;
        int per = DAMAGE_PER_50 + (upgraded ? UPGRADE_DAMAGE_PER_50 : 0);
        int total = stacks * per;

        if (total > 0) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                    m, 
                    new DamageInfo(p, total, this.damageTypeForTurn), 
                    AttackEffect.SLASH_HEAVY
                )
            );
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_DAMAGE_PER_50);
            
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hired();
    }
}
