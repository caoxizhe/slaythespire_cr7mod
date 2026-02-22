package cr7mod.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RareCardList {
    private static final Set<String> LIST = new HashSet<>();

    static {
        // Register rare card IDs here. Add more IDs when new rare cards are created.
        LIST.add("CR7Mod:DragonCurve");
        LIST.add("CR7Mod:WarriorSpirit");
        LIST.add("CR7Mod:ButterflyOverSea");
        LIST.add("CR7Mod:MadridistaStance");
        LIST.add("CR7Mod:RedDevil");
        LIST.add("CR7Mod:BianconeriGlory");
        LIST.add("CR7Mod:SIUUUUU");
        LIST.add("CR7Mod:Chess");
        LIST.add("CR7Mod:UnbalancedShoot");
        LIST.add("CR7Mod:PublicHero");
        LIST.add("CR7Mod:Dribble");
        LIST.add("CR7Mod:KingOfDesert");
        LIST.add("CR7Mod:ThreePeat");
        LIST.add("CR7Mod:Roar");
        LIST.add("CR7Mod:StrikeInstinct");
        LIST.add("CR7Mod:GOAT");
        LIST.add("CR7Mod:DoubleKing");
        LIST.add("CR7Mod:Ferguson");
    }

    public static boolean isRareCard(String cardId) {
        return LIST.contains(cardId);
    }

    public static void addRareCard(String cardId) {
        if (cardId != null) LIST.add(cardId);
    }

    public static Set<String> getList() {
        return Collections.unmodifiableSet(LIST);
    }
}
