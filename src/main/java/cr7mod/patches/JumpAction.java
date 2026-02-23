package cr7mod.patches;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;


public class JumpAction extends AbstractGameAction {
    private int increaseAmount;

    public JumpAction(int incAmount) {
        this.increaseAmount = incAmount;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F) {
            // Iterate master deck and increase misc/baseDamage for matching cards
            Iterator<AbstractCard> it = AbstractDungeon.player.masterDeck.group.iterator();
            while (it.hasNext()) {
                AbstractCard c = it.next();
                if ("CR7Mod:Header".equals(c.cardID) || "CR7Mod:BulletHeader".equals(c.cardID)) {
                    c.applyPowers();
                    c.baseDamage += this.increaseAmount;
                    c.isDamageModified = false;
                }
            }

            // Update all in-battle instances for each matching master card
            for (AbstractCard masterCard : AbstractDungeon.player.masterDeck.group) {
                if ("CR7Mod:Header".equals(masterCard.cardID) || "CR7Mod:BulletHeader".equals(masterCard.cardID)) {
                    for (AbstractCard inBattle : GetAllInBattleInstances.get(masterCard.uuid)) {
                        inBattle.applyPowers();
                        inBattle.baseDamage += this.increaseAmount;
                        inBattle.isDamageModified = false;
                    }
                }
            }

        }
        this.tickDuration();
    }
}
