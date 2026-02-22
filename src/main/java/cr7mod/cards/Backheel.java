package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class Backheel extends CustomCard {
    public static final String ID = "CR7Mod:Backheel";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/backheel.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 4;
    private static final int THRESHOLD = 5;
    private static final int UPGRADE_THRESHOLD = -1;

    public Backheel() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = THRESHOLD;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new GainBlockAction(p, p, this.block)
        );

        // For every magicNumber Fans, gain 1 Dexterity
        FansPower fp = (FansPower) p.getPower(FansPower.POWER_ID);
        int fans = fp == null ? 0 : fp.amount;
        int dex = this.magicNumber > 0 ? fans / this.magicNumber : 0;
        if (dex > 0) {
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new DexterityPower(p, dex), dex)
            );
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_THRESHOLD);
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new Backheel();
    }
}
