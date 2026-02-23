package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import com.megacrit.cardcrawl.cards.AbstractCard;
import java.util.ArrayList;
import java.util.Collections;

public class MadridistaStancePower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:MadridistaStancePower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String IMG_84 = "powers/madridista_84.png";
    private static final String IMG_32 = "powers/madridista_32.png";

    public MadridistaStancePower(AbstractCreature owner, int drawExtra) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = drawExtra; // draw extra and number of cards to set cost 0
        this.name = POWER_STRINGS.NAME;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
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
    public void updateDescription() {
        if (POWER_STRINGS.DESCRIPTIONS.length > 0) {
            this.description = String.format(POWER_STRINGS.DESCRIPTIONS[0], this.amount, this.amount);
        } else {
            this.description = "";
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new DrawCardAction(this.owner, this.amount)
        );

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> hand = new ArrayList<>(AbstractDungeon.player.hand.group);
                final int freeCount = MadridistaStancePower.this.amount;
                if (hand.isEmpty() || freeCount <= 0) {
                    this.isDone = true;
                    return;
                }
                Collections.shuffle(hand);
                int applied = 0;
                for (AbstractCard c : hand) {
                    if (applied >= freeCount) break;
                    // only set cost to 0 for cards whose cost is not already 0
                    if (c.costForTurn != 0) {
                        c.setCostForTurn(0);
                        applied++;
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount < 0) this.amount = 0;
        updateDescription();
    }
    
}
