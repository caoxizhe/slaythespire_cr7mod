package cr7mod.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CharacterCardList {
    private static final Set<String> LIST = new HashSet<>();

    static {
        // Register character card IDs here. Add more IDs when new character cards are created.
        LIST.add("CR7Mod:Antony");
        LIST.add("CR7Mod:Mbappe");
        LIST.add("CR7Mod:Ramos");
        LIST.add("CR7Mod:Modric");
        LIST.add("CR7Mod:Bale");
        LIST.add("CR7Mod:Benzema");
        LIST.add("CR7Mod:Pepe");
        LIST.add("CR7Mod:Bruno");
        LIST.add("CR7Mod:Kaka");
        LIST.add("CR7Mod:Ferguson");
        LIST.add("CR7Mod:Dimaria");
        LIST.add("CR7Mod:Mourinho");
        LIST.add("CR7Mod:Rooney");
        LIST.add("CR7Mod:Marcelo");
    }

    public static boolean isCharacterCard(String cardId) {
        return LIST.contains(cardId);
    }

    public static void addCharacterCard(String cardId) {
        if (cardId != null) LIST.add(cardId);
    }

    public static Set<String> getList() {
        return Collections.unmodifiableSet(LIST);
    }
}
