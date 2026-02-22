package cr7mod.relics;

import basemod.abstracts.CustomRelic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import cr7mod.cards.RedCard;
import cr7mod.utils.RedCardCostTracker;

public class Whistle extends CustomRelic {
    public static final String ID = "CR7Mod:Whistle";
    private static final String IMG_PATH = "yiwu/whistle.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;


    public Whistle() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void onEquip() {
        RedCardCostTracker.setRedCostZero();
    }

    @Override
    public void onUnequip() {
        RedCardCostTracker.reset();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (RedCard.ID.equals(c.cardID)) {
            this.flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Whistle();
    }
}
