package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class UnyieldPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:UnyieldPower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public UnyieldPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = POWER_STRINGS.NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.amount = amount;
        loadIcon();
        this.updateDescription();
    }

    private static final String IMG_84 = "powers/unyield_84.png";
    private static final String IMG_32 = "powers/unyield_32.png";

    private void loadIcon() {
        Texture tex84 = new Texture(Gdx.files.internal(IMG_84));
        Texture tex32 = new Texture(Gdx.files.internal(IMG_32));
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(this.owner, this.owner, new FansPower(this.owner, this.amount), this.amount)
        );
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        if (POWER_STRINGS.DESCRIPTIONS.length > 0) {
            this.description = String.format(POWER_STRINGS.DESCRIPTIONS[0], this.amount);
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
