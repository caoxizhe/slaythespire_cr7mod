package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class KingOfDesert extends CustomCard {
    public static final String ID = "CR7Mod:KingOfDesert";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/king_of_desert.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int FANS_LOSE = 7;
    private static final int BASE_GOLD = 15;
    private static final int UPGRADE_GOLD = 5;

    public KingOfDesert() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_GOLD;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new ReducePowerAction(p, p, FansPower.POWER_ID, FANS_LOSE)
        );
        AbstractDungeon.actionManager.addToBottom(
            new GainGoldAction(this.magicNumber)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        AbstractPower power = p.getPower(FansPower.POWER_ID);
        int currentFans = 0;
        if (power != null) {
            currentFans = power.amount;
        }
        if (currentFans < FANS_LOSE) {
            UIStrings ui = CardCrawlGame.languagePack.getUIString("CR7Mod:NotEnoughFans");
            if (ui != null && ui.TEXT != null && ui.TEXT.length > 0) {
                this.cantUseMessage = ui.TEXT[0];
            }   
            return false;
        }
        return true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_GOLD);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new KingOfDesert();
    }
}
