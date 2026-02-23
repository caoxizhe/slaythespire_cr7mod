package cr7mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import cr7mod.cards.Morocco;
import cr7mod.cards.YellowCard;
import cr7mod.utils.MoroccoStatusTracker;
import cr7mod.utils.YellowCheck;
import cr7mod.powers.RooneyPower;
import java.lang.reflect.Field;


@SpirePatch(clz = MakeTempCardInHandAction.class, method = "update")
public class AddCardToHandPatch {
    @SpirePostfixPatch
    public static void postfix(MakeTempCardInHandAction __instance) {
        try {
            Field f = null;
            try {
                f = __instance.getClass().getDeclaredField("c");
            } catch (NoSuchFieldException e) {
            }
            if (f != null) {
                f.setAccessible(true);
                Object o = f.get(__instance);
                if (o instanceof AbstractCard) {
                    AbstractCard ac = (AbstractCard)o;
                    String cardId = ac.cardID;
                    if (cardId != null) {

                        if (YellowCard.ID.equals(cardId)) {
                            YellowCheck.handleYellowCheck();
                        }

                        if (Morocco.ID.equals(cardId)) {
                            MoroccoStatusTracker.blockFansThisTurn();
                        }

                        AbstractPower rn = AbstractDungeon.player.getPower(RooneyPower.POWER_ID);
                        if (rn instanceof RooneyPower) {
                            if (ac.type == AbstractCard.CardType.STATUS || ac.type == AbstractCard.CardType.CURSE) {
                                ((RooneyPower) rn).onAfterBadCard();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





