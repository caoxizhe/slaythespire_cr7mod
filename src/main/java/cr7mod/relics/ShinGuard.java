package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import cr7mod.powers.FansPower;

public class ShinGuard extends CustomRelic {
    public static final String ID = "CR7Mod:ShinGuard";
    private static final String IMG_PATH = "yiwu/shin_guard.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public ShinGuard() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info != null && info.owner instanceof AbstractMonster) {
            AbstractMonster source = (AbstractMonster) info.owner;
            AbstractPower fans = AbstractDungeon.player.getPower(FansPower.POWER_ID);
            int fansAmount = fans == null ? 0 : fans.amount;
            if (source.currentHealth < fansAmount) {
                this.flash();
                return 0;
            }
        }
        return damageAmount;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ShinGuard();
    }
}
