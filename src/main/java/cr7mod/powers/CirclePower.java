package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class CirclePower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:CirclePower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String IMG_84 = "powers/circle_84.png";
    private static final String IMG_32 = "powers/circle_32.png";

    public CirclePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.name = POWER_STRINGS.NAME;
        this.type = PowerType.DEBUFF;
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
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, this.amount, false), this.amount));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void updateDescription() {
        if (POWER_STRINGS.DESCRIPTIONS.length > 0) {
            this.description = String.format(POWER_STRINGS.DESCRIPTIONS[0], this.amount);
        } else {
            this.description = "";
        }
    }
}
