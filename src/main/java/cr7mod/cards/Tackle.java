package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import cr7mod.characters.CR7Character;

public class Tackle extends CustomCard {
    public static final String ID = "CR7Mod:Tackle";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/tackle.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int BASE_BLOCK = 14;
    private static final int BASE_STRENGTH_LOSS = 2;
    private static final int UPGRADE_BLOCK = 4;
    private static final int UPGRADE_STRENGTH_LOSS = 1;

    public Tackle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = BASE_STRENGTH_LOSS;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new GainBlockAction(p, p, this.block)
        );
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom().monsters.monsters)) {
            if (!mo.isDead && !mo.isDying) {
                AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber)
                );
                AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber)
                );
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_STRENGTH_LOSS);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tackle();
    }
}
