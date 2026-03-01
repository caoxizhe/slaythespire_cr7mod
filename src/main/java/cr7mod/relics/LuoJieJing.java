package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import cr7mod.powers.FansPower;

public class LuoJieJing extends CustomRelic {
    public static final String ID = "CR7Mod:LuoJieJing";
    private static final String IMG_PATH = "yiwu/jiejing.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;
    private static final int BASE_FANS_GAIN = 60;

    // current fans provided at battle start; stored in relic counter for persistence

    public LuoJieJing() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.counter = BASE_FANS_GAIN;
        // Ensure description and tooltips reflect the initialized counter
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0], this.counter);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FansPower(AbstractDungeon.player, this.counter), this.counter));
    }

    public void addFansGain(int amount) {
        this.counter += amount;
        if (this.counter < 0) this.counter = 0;
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LuoJieJing();
    }
}
