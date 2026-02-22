package cr7mod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import cr7mod.powers.FansPower;

public class CocaColaPotion extends CustomPotion {
    public static final String POTION_ID = "CR7Mod:CocaColaPotion";
    private static final PotionStrings STRINGS = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final int FANS_GAIN = 20;

    public CocaColaPotion() {
        super(STRINGS.NAME, POTION_ID, PotionRarity.RARE, PotionSize.HEART, AbstractPotion.PotionColor.NONE);
        this.potency = getPotency();
        this.description = STRINGS.DESCRIPTIONS[0];
        this.isThrown = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FansPower(AbstractDungeon.player, this.potency), this.potency)
        );
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CocaColaPotion();
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return FANS_GAIN;
    }
}
