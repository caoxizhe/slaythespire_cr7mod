package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class BestNo7 extends CustomCard {
    public static final String ID = "CR7Mod:BestNo7";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/best7.png";
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int BLOCK = 7;
    private static final int DRAW = 1;

    public BestNo7() {
        super(ID, NAME, IMG_PATH,COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = DRAW;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int times = 1;
        if (p != null && p.hasPower(FansPower.POWER_ID)) {
            int fans = p.getPower(FansPower.POWER_ID).amount;
            if (fans > 0 && fans % 7 == 0) times = 7;
        }
         
        for (int i = 0; i < times; i++) {
            AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
            );
            AbstractDungeon.actionManager.addToBottom(
                new DrawCardAction(p, this.magicNumber)
            );
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BestNo7();
    }
}
