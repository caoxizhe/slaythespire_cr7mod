package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class Dive extends CustomCard {
    public static final String ID = "CR7Mod:Dive";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/dive.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int BASE_MAGIC = 1; 
    private static final int UPGRADE_MAGIC = 1;

    public Dive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Penalty();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dex = 0;
        if (p.getPower(DexterityPower.POWER_ID) != null) dex = p.getPower(DexterityPower.POWER_ID).amount;
        int fans = 0;
        if (p.getPower(FansPower.POWER_ID) != null) fans = p.getPower(FansPower.POWER_ID).amount;
        float chance = dex * 0.07f + fans * (this.magicNumber / 100f);
        if (chance < 0f) chance = 0f;
        if (chance > 1f) chance = 1f;
        float roll = (float)Math.random();
        boolean success = roll < chance;
        if (success) {
            AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(new Penalty(), 1)
            );
        } 
        else {
            AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(new YellowCard(), 1)
            );
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dive();
    }
}
