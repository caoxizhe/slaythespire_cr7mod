package cr7mod.utils;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import cr7mod.cards.RedCard;
import cr7mod.cards.YellowCard;

public class YellowCheck {

    public static void handleYellowCheck() {
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
