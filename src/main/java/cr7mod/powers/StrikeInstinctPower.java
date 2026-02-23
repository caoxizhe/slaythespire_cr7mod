package cr7mod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class StrikeInstinctPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:StrikeInstinctPower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public StrikeInstinctPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = POWER_STRINGS.NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.amount = amount;
        loadIcon();
        this.updateDescription();
    }

    private static final String IMG_84 = "powers/strike_instinct_84.png";
    private static final String IMG_32 = "powers/strike_instinct_32.png";

    private void loadIcon() {
        Texture tex84 = new Texture(Gdx.files.internal(IMG_84));
        Texture tex32 = new Texture(Gdx.files.internal(IMG_32));
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            if (card.target == AbstractCard.CardTarget.ALL_ENEMY || card.target == AbstractCard.CardTarget.ALL) {
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if (!monster.isDead && !monster.isDying) {
                        this.flash();
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, owner, new StrengthPower(monster, -this.amount), -this.amount));
                    }
                }
            } else if (m != null && !m.isDead && !m.isDying) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, owner, new StrengthPower(m, -this.amount), -this.amount));
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
