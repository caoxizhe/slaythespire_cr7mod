package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.utils.MoroccoStatusTracker;

public class Morocco extends CustomCard {
    public static final String ID = "CR7Mod:Morocco";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/morocco.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;


    public Morocco() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        markBlocked();
    }

    public void triggerWhenAddedToHand() {
        markBlocked();
    }

    private void markBlocked() {
        if (AbstractDungeon.player != null) {
            MoroccoStatusTracker.blockFansThisTurn();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Unplayable status card – no on-use effect.
    }

    @Override
    public void triggerOnExhaust() {
        MoroccoStatusTracker.reset();
    }

    @Override
    public void onMoveToDiscard() {
        MoroccoStatusTracker.reset();
    }

    @Override
    public void upgrade() {
        // No upgrade path for status cards
    }

    @Override
    public AbstractCard makeCopy() {
        return new Morocco();
    }
}
