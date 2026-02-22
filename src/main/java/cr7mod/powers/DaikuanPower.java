package cr7mod.powers;

import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import cr7mod.utils.RareCardList;

public class DaikuanPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:DaikuanPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String IMG_84 = "powers/daikuan_84.png";
    private static final String IMG_32 = "powers/daikuan_32.png";

    public DaikuanPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.name = powerStrings.NAME;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        loadIcon();
        updateDescription();
    }

    private void loadIcon() {
        Texture tex84 = new Texture(Gdx.files.internal(IMG_84));
        Texture tex32 = new Texture(Gdx.files.internal(IMG_32));
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.amount <= 0) return;

        ArrayList<String> ids = new ArrayList<>(RareCardList.getList());
        if (ids.isEmpty()) {
            return;
        }

        int idx = AbstractDungeon.cardRandomRng.random(ids.size() - 1);
        String cardId = ids.get(idx);
        AbstractCard card = CardLibrary.getCard(cardId);

        if (card != null) {
            AbstractCard copycard = card.makeCopy();
            copycard.setCostForTurn(0);
            this.addToBot(new MakeTempCardInHandAction(copycard, 1));
        }

        this.amount--;
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void updateDescription() {
        if (powerStrings.DESCRIPTIONS.length > 0) {
            this.description = powerStrings.DESCRIPTIONS[0];
        } else {
            this.description = "";
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount < 0) this.amount = 0;
        updateDescription();
    }
}
