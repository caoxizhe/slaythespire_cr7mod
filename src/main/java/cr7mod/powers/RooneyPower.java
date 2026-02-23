package cr7mod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class RooneyPower extends AbstractPower {
    public static final String POWER_ID = "CR7Mod:RooneyPower";
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public RooneyPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.name = POWER_STRINGS.NAME;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = amount;
        loadIcon();
        updateDescription();
    }

    private void loadIcon() {
        Texture tex84 = new Texture("powers/rooney_84.png");
        Texture tex32 = new Texture("powers/rooney_32.png");
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
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
            this.onAfterBadCard();
        }
    }

    public void onAfterBadCard() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount)
        );
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount), this.amount)
        );
    }

    @Override
    public void updateDescription() {
        if (POWER_STRINGS.DESCRIPTIONS.length > 0) {
            this.description = String.format(POWER_STRINGS.DESCRIPTIONS[0], this.amount, this.amount);
        } else {
            this.description = "";
        }
    }
}
