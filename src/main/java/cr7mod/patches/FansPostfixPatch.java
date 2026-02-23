package cr7mod.patches;

import org.lwjgl.Sys;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import cr7mod.powers.FansPower;
import cr7mod.powers.RonaldoRushdownPower;
import cr7mod.powers.VolleyShootPower;
import cr7mod.powers.YoungPower;
import cr7mod.utils.FansGainTracker;
import cr7mod.utils.MoroccoStatusTracker;

@SpirePatch(clz = ApplyPowerAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class})
public class FansPostfixPatch {
    @SpirePostfixPatch
    public static void postfix(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        try {
            if (powerToApply != null && FansPower.POWER_ID.equals(powerToApply.ID) && target == AbstractDungeon.player && stackAmount > 0 && !MoroccoStatusTracker.isBlocked()) {

                AbstractPower vp = AbstractDungeon.player.getPower(VolleyShootPower.POWER_ID);
                if (vp instanceof VolleyShootPower) {
                    ((VolleyShootPower) vp).onFansApplied();
                }

                AbstractPower rushdown = AbstractDungeon.player.getPower(RonaldoRushdownPower.POWER_ID);
                if (rushdown instanceof RonaldoRushdownPower) {
                    ((RonaldoRushdownPower) rushdown).onFansApplied();
                }

                AbstractPower yp = AbstractDungeon.player.getPower(YoungPower.POWER_ID);
                if (yp instanceof YoungPower) {
                    yp.flash();
                    int stacks = 1 << ((YoungPower) yp).amount; 
                    if (stacks > 0) {
                        stackAmount *= stacks;
                        powerToApply.amount = stackAmount;
                        ReflectionHacks.setPrivate(__instance, AbstractGameAction.class, "amount", stackAmount);
                        ReflectionHacks.setPrivate(__instance, AbstractGameAction.class, "powerToApply", powerToApply);
                    }
                }

                // record total fans gained this combat
                FansGainTracker.add(stackAmount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
