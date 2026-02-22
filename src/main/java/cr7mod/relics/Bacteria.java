package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Bacteria extends CustomRelic {
    public static final String ID = "CR7Mod:Bacteria";
    private static final String IMG_PATH = "yiwu/bacteria.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    private static final int MAX_HEALTH_LOSS = 15;

    public Bacteria() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0], MAX_HEALTH_LOSS);
    }

    @Override
    public void onEquip() {
      AbstractDungeon.player.decreaseMaxHealth(MAX_HEALTH_LOSS);
      ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
      --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Bacteria();
    }
}
