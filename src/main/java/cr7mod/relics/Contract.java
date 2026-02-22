package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Contract extends CustomRelic {
    public static final String ID = "CR7Mod:Contract";
    private static final String IMG_PATH = "yiwu/contract.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;

    private static final int GOLD_PER_HP = 3;

    public Contract() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0], GOLD_PER_HP);
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            int gold = damageAmount * GOLD_PER_HP;
            AbstractDungeon.actionManager.addToBottom(
                new GainGoldAction(gold)
            );
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Contract();
    }
}
