package cr7mod.cards;

import basemod.abstracts.CustomCard;
import cr7mod.powers.FansPower;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Zhen extends CustomCard {
    public static final String ID = "CR7Mod:Zhen";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/zhen.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Zhen() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractPower power = AbstractDungeon.player.getPower(FansPower.POWER_ID);
        int currentFans = 0;
        if (power != null) {
            currentFans = power.amount;
        }
        if (currentFans < 3) {
            AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, FansPower.POWER_ID, currentFans)
            );
        }
        else {
            AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, FansPower.POWER_ID, 3)
            );
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Unplayable status; nothing happens when used.
    }

    @Override
    public void upgrade() {
        // No upgrade
    }

    @Override   
    public AbstractCard makeCopy() {
        return new Zhen();
    }
}
