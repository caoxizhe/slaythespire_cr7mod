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
import java.util.ArrayList;
import java.util.List;

public class VolleyShootPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:VolleyShootPower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private final List<Integer> damages = new ArrayList<>();

    public VolleyShootPower(AbstractCreature owner, int damage) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.name = POWER_STRINGS.NAME;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        if (damage > 0) {
            this.damages.add(damage);
            int total = 0;
            for (Integer d : this.damages) total += d;
            this.amount = total;
        } else {
            this.amount = 0;
        }
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
        if (stackAmount > 0) {
            this.damages.add(stackAmount);
            this.fontScale = 8.0F;
            int total = 0;
            for (Integer d : this.damages) total += d;
            this.amount = total;
            updateDescription();
        }
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void onFansApplied() {
        if (this.damages.isEmpty()) return;
        int total = 0;
        for (Integer dmg : this.damages) total += dmg;
        AbstractDungeon.actionManager.addToBottom(
            new DamageRandomEnemyAction(
                new DamageInfo(AbstractDungeon.player, total), 
                AttackEffect.FIRE
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
