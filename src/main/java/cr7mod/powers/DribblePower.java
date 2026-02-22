package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DribblePower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:DribblePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String IMG_84 = "powers/dribble_84.png";
    private static final String IMG_32 = "powers/dribble_32.png";

    public DribblePower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = 1;
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
        if (this.owner != AbstractDungeon.player) return;
        FansPower fp = (FansPower) this.owner.getPower(FansPower.POWER_ID);
        int fans = fp != null ? fp.amount : 0;
        if (fans > 0) {
            int total = fans * this.amount;
            if (total > 0) {
                AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(this.owner, this.owner, total)
                );
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(
            new LoseEnergyAction(this.amount)
        );
        this.flash();
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
