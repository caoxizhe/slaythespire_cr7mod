package cr7mod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;

public class VolleyShootPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:VolleyShootPower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public VolleyShootPower(AbstractCreature owner, int damage) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.name = POWER_STRINGS.NAME;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = damage;
        loadIcon();
        updateDescription();
    }

    private void loadIcon() {
        Texture tex84 = new Texture("powers/volley_84.png");
        Texture tex32 = new Texture("powers/volley_32.png");
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount < 0) {
            this.amount = 0;
        }
        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void onFansApplied() {
        if (this.amount <= 0) return;
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new DamageRandomEnemyAction(
                new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS), 
                AttackEffect.LIGHTNING
            )
        );
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
