package cr7mod.cards;

import basemod.abstracts.CustomCard;
import cr7mod.characters.CR7Character;
import cr7mod.powers.CirclePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;


public class Circle extends CustomCard {
    public static final String ID = "CR7Mod:Circle";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/circle.png";
    private static final int COST = 1; 
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final int END_VULN = 1;


    public Circle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber)
        );

        // 回合结束时自身获得 1 层易伤
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new CirclePower(p, END_VULN), END_VULN)
        );
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Circle();
    }
}
