package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class BargeReferee extends CustomCard {
    public static final String ID = "CR7Mod:BargeReferee";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/badge_the_referee.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    // magicNumber represents percent per Fans (base 1 -> 1%)
    private static final int BASE_MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1; 

    public BargeReferee() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int str = 0;
        if (p.getPower(StrengthPower.POWER_ID) != null) str = p.getPower(StrengthPower.POWER_ID).amount;
        int fans = 0;
        if (p.getPower(FansPower.POWER_ID) != null) fans = p.getPower(FansPower.POWER_ID).amount;

        float chance = fans * (this.magicNumber / 100f);
        if (chance < 0f) chance = 0f;
        if (chance > 1f) chance = 1f;
        float roll = (float) Math.random();
        boolean success = roll < chance;

        if (success) {
            // Double current Strength 
            if (str > 0) {
                AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new StrengthPower(p, str), str)
                );
            }
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
        return new BargeReferee();
    }
}
