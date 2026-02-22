package cr7mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import cr7mod.cards.RedCard;

@SpirePatch(clz = AbstractCard.class, method = "canUse")
public class RedCardBlockPatch {
    @SpirePrefixPatch
    public static SpireReturn<Boolean> prefix(AbstractCard __instance, AbstractPlayer p, AbstractMonster m) {
        try {
            if (p == null || p.hand == null) return SpireReturn.Continue();
            boolean hasRed = p.hand.group.stream().anyMatch(c -> RedCard.ID.equals(c.cardID));
            if (hasRed && __instance.type == AbstractCard.CardType.ATTACK) {
                try {
                    String msg = CardCrawlGame.languagePack.getUIString("CR7Mod:RedCardBlocked").TEXT[0];
                    __instance.cantUseMessage = msg;
                } catch (Exception ignore) {
                }
                return SpireReturn.Return(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SpireReturn.Continue();
    }
}
