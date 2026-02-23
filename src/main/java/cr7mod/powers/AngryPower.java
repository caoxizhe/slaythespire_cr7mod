package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;

public class AngryPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:AngryPower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String IMG_84 = "powers/angry_84.png";
    private static final String IMG_32 = "powers/angry_32.png";

    public AngryPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = POWER_STRINGS.NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = amount;
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

        ArrayList<AbstractMonster> candidates = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster != null && !monster.isDead && !monster.isDying) {
                int blk = monster.currentBlock;
                if (blk > 0) candidates.add(monster);
            }
        }

        if (!candidates.isEmpty()) {
            Collections.shuffle(candidates);
            int toConvert = Math.min(this.amount, candidates.size());
            int totalFans = 0;
            for (int i = 0; i < toConvert; i++) {
                AbstractMonster chosen = candidates.get(i);
                int amt = 0;
                amt = chosen.currentBlock;
                chosen.currentBlock = 0;
                if (amt > 0) totalFans += amt;
            }

            if (totalFans > 0) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(this.owner, this.owner, new FansPower(this.owner, totalFans), totalFans)
                );
            }
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

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount < 0) this.amount = 0;
        updateDescription();
    }
}
