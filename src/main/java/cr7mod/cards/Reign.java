package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class Reign extends CustomCard {
    public static final String ID = "CR7Mod:Reign";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/reign.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int FANS_LOSE = 5;
    private static final int BASE_MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Reign() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Subtract Fans
        if (p.hasPower(FansPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, FansPower.POWER_ID, FANS_LOSE)
            );
            // Apply Strength and Dexterity equal to magicNumber
            
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber)
            );
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber)
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
    public AbstractCard makeCopy() {
        return new Reign();
    }
}
