package cr7mod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import cr7mod.powers.FansPower;
import cr7mod.utils.MoroccoStatusTracker;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class MoroccoNoFansPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(ApplyPowerAction __instance) {
        try {
            if (__instance == null) return SpireReturn.Continue();
            AbstractCreature target = __instance.target;
            if (target == null || !target.isPlayer) return SpireReturn.Continue();
            AbstractPower power = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
            if (power == null || power.ID == null) return SpireReturn.Continue();
            if (!FansPower.POWER_ID.equals(power.ID)) return SpireReturn.Continue();
            if (power.amount <= 0) return SpireReturn.Continue();
            if (MoroccoStatusTracker.isBlocked()) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SpireReturn.Continue();
    }
}
