package cr7mod.utils;

public class FansGainTracker {
    private static int totalGainedThisCombat = 0;

    public static void add(int amount) {
        if (amount <= 0) return;
        totalGainedThisCombat += amount;
    }

    public static int getTotal() {
        return totalGainedThisCombat;
    }

    public static void reset() {
        totalGainedThisCombat = 0;
    }
}
