package cr7mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import cr7mod.powers.FansPower;
import cr7mod.powers.RonaldoRushdownPower;
import cr7mod.powers.VolleyShootPower;
import cr7mod.utils.FansGainTracker;
import cr7mod.utils.MoroccoStatusTracker;

@SpirePatch(clz = ApplyPowerAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class})
public class ApplyPowerActionPatch {
    @SpirePostfixPatch
    public static void postfix(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int amount) {
        try {
            if (powerToApply != null && FansPower.POWER_ID.equals(powerToApply.ID) && target == AbstractDungeon.player && amount > 0 && !MoroccoStatusTracker.isBlocked()) {
                // record total fans gained this combat
                FansGainTracker.add(amount);
                AbstractPower vp = AbstractDungeon.player.getPower(VolleyShootPower.POWER_ID);
                if (vp instanceof VolleyShootPower) {
                    ((VolleyShootPower) vp).onFansApplied();
                }
                AbstractPower rushdown = AbstractDungeon.player.getPower(RonaldoRushdownPower.POWER_ID);
                if (rushdown instanceof RonaldoRushdownPower) {
                    ((RonaldoRushdownPower) rushdown).onFansApplied();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
