package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FansPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:Fans";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String IMG_84 = "powers/fan_84.png";
    private static final String IMG_32 = "powers/fan_32.png";

    public FansPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.name = POWER_STRINGS.NAME;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.temporary = false;
        loadIcon();
        updateDescription();
    }

    // When true, Fans will be set to 0 at end of round
    private boolean temporary;

    public void setTemporary(boolean temp) {
        this.temporary = temp;
    }

    @Override
    public void atEndOfRound() {
        if (this.temporary) {
            this.amount = 0;
            updateDescription();
            this.temporary = false;
        }
    }

    private void loadIcon() {
        Texture tex84 = new Texture(Gdx.files.internal(IMG_84));
        Texture tex32 = new Texture(Gdx.files.internal(IMG_32));
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    @Override
    public void updateDescription() {
        this.description = POWER_STRINGS.DESCRIPTIONS[0].replace("{0}", String.valueOf(this.amount));
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount < 0) this.amount = 0;
        updateDescription();
    }
}
