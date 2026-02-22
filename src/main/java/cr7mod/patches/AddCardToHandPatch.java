package cr7mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;

import cr7mod.cards.Morocco;
import cr7mod.cards.RedCard;
import cr7mod.cards.YellowCard;
import cr7mod.utils.MoroccoStatusTracker;
import java.lang.reflect.Field;


public class AddCardToHandPatch {

    // Intercept MakeTempCardInHandAction.update 
    @SpirePatch(clz = MakeTempCardInHandAction.class, method = "update")
    public static class MakeTempCardInHandPatch {
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
                                handleYellowCheck();
                            }
                            if (Morocco.ID.equals(cardId)) {
                                MoroccoStatusTracker.blockFansThisTurn();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleYellowCheck() {
        if (AbstractDungeon.player == null || AbstractDungeon.player.hand == null) return;
        // Iterate hand once; every time we collect two YellowCards, exhaust them and add one RedCard.
        java.util.ArrayList<AbstractCard> pair = new java.util.ArrayList<>();
        // Iterate over a snapshot to avoid concurrent-modification issues
        java.util.ArrayList<AbstractCard> snapshot = new java.util.ArrayList<>(AbstractDungeon.player.hand.group);
        
        for (AbstractCard c : snapshot) {
            if (c == null) continue;
            if (YellowCard.ID.equals(c.cardID)) {
                pair.add(c);
                if (pair.size() >= 2) {
                    // Exhaust the two found yellow cards
                    for (AbstractCard yc : pair) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(yc, AbstractDungeon.player.hand));
                    }
                    // Add one Red Card to hand for this pair
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new RedCard(), 1));
                    pair.clear();
                }
            }
        }
    }
}


