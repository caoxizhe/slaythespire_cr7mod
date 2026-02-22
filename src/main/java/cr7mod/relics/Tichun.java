package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import cr7mod.powers.FansPower;

public class Tichun extends CustomRelic {
    public static final String ID = "CR7Mod:Tichun";
    private static final String IMG_PATH = "yiwu/tichun.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;
    private static final int BASE_FANS_GAIN = 12;

    public Tichun() {
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
    public boolean canSpawn() {
      return AbstractDungeon.player.hasRelic("LuoJieJing");
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
        return new Tichun();
    }
}
