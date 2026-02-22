package cr7mod.patches;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import cr7mod.cards.Duel;
import cr7mod.characters.CR7Character;

import java.util.ArrayList;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

public class BrunoAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean upgraded;

    public BrunoAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } 
        else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (upgraded) {
                        disCard.setCostForTurn(0);
                    }
                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
                this.retrieveCard = true;
            }
            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        ArrayList<AbstractCard> skills = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.type == CardType.SKILL
                && c.color == CR7Character.Enums.CR7_COLOR
                && !Duel.ID.equals(c.cardID)
                && !UnlockTracker.isCardLocked(c.cardID)) {
                skills.add(c);
            }
        }

        if (skills.size() <= 3) {
            for (AbstractCard c : skills) {
                list.add(c.makeStatEquivalentCopy());
            }
            return list;
        }

        for (int i = 0; i < 3; i++) {
            int idx = AbstractDungeon.cardRandomRng.random(skills.size() - 1);
            AbstractCard chosen = skills.remove(idx);
            list.add(chosen.makeStatEquivalentCopy());
        }
        return list;
    }
}
