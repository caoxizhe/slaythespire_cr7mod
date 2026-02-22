package cr7mod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import cr7mod.cards.BallonDor;

public class BallonDorPotion extends CustomPotion {
    public static final String POTION_ID = "CR7Mod:BallonDorPotion";
    private static final PotionStrings STRINGS = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public BallonDorPotion() {
        super(STRINGS.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, AbstractPotion.PotionColor.NONE);
        this.potency = getPotency();
        this.description = STRINGS.DESCRIPTIONS[0];
        this.isThrown = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        // 将两张金球加入手牌
        AbstractDungeon.actionManager.addToBottom(
            new MakeTempCardInHandAction(new BallonDor(), 2)
        );
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BallonDorPotion();
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;
    }
}
