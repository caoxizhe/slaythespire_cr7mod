package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GoldShoe extends CustomRelic {
    public static final String ID = "CR7Mod:GoldShoe";
    private static final String IMG_PATH = "yiwu/goldshoe.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;
    private static final int CARDS_DRAWN = 1;

    public GoldShoe() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0], CARDS_DRAWN);
    }

    @Override
    public void onShuffle() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new DrawCardAction(CARDS_DRAWN)
        );
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GoldShoe();
    }
}
