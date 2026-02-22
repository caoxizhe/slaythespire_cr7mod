package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import cr7mod.characters.CR7Character;

public class CounterAttack extends CustomCard {
    public static final String ID = "CR7Mod:CounterAttack";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/counter_attack.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int DRAW = 2;
    private static final int UPGRADE_DRAW = 1;

    public CounterAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = DRAW;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // read current amounts
        int str = 0;
        int dex = 0;
        if (p.hasPower(StrengthPower.POWER_ID)) {
            str = p.getPower(StrengthPower.POWER_ID).amount;
        }
        if (p.hasPower(DexterityPower.POWER_ID)) {
            dex = p.getPower(DexterityPower.POWER_ID).amount;
        }

        // remove existing powers
        if (p.hasPower(StrengthPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(p, p, StrengthPower.POWER_ID)
            );
        }
        if (p.hasPower(DexterityPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(p, p, DexterityPower.POWER_ID)
            );
        }

        // apply swapped values
        if (dex != 0) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StrengthPower(p, dex), dex)
            );
        }
        if (str != 0) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new DexterityPower(p, str), str)
            );
        }

        // draw cards
        AbstractDungeon.actionManager.addToBottom(
            new DrawCardAction(p, this.magicNumber)
        );
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CounterAttack();
    }
}
