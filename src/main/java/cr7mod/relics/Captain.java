package cr7mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import cr7mod.utils.CharacterCardList;

import java.util.ArrayList;

public class Captain extends CustomRelic {
    public static final String ID = "CR7Mod:Captain";
    private static final String IMG_PATH = "yiwu/captain.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;

    public Captain() {
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
    public void atTurnStart() {
        ArrayList<String> ids = new ArrayList<>(CharacterCardList.getList());
        if (ids.isEmpty()) {
            return;
        }

        int idx = AbstractDungeon.cardRandomRng.random(ids.size() - 1);
        String cardId = ids.get(idx);
        AbstractCard card = CardLibrary.getCard(cardId);

        if (card != null) {
            this.flash();
            this.addToBot(new MakeTempCardInHandAction(card.makeCopy(), 1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Captain();
    }
}
