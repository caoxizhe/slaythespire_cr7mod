package cr7mod.utils;

public class RedCardCostTracker {
    private static boolean RedCostZero = false;

    private RedCardCostTracker() {}

    public static void setRedCostZero() {
        RedCostZero = true;
    }

    public static boolean isRedCostZero() {
        return RedCostZero;
    }

    public static void reset() {
        RedCostZero = false;
    }
}
