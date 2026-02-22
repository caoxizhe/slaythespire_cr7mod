package cr7mod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import cr7mod.patches.CharacterCardChoiceAction;

public class CharacterPotion extends CustomPotion {
    public static final String POTION_ID = "CR7Mod:CharacterPotion";
    private static final PotionStrings STRINGS = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public CharacterPotion() {
        super(STRINGS.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, AbstractPotion.PotionColor.NONE);
        this.potency = getPotency();
        this.description = STRINGS.DESCRIPTIONS[0];
        this.isThrown = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
            new CharacterCardChoiceAction()
        );
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CharacterPotion();
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;
    }
}
