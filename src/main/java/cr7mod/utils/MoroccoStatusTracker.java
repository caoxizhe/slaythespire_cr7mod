package cr7mod.utils;

public class MoroccoStatusTracker {
    private static boolean blockedThisTurn = false;

    private MoroccoStatusTracker() {}

    public static void blockFansThisTurn() {
        blockedThisTurn = true;
    }

    public static boolean isBlocked() {
        return blockedThisTurn;
    }

    public static void reset() {
        blockedThisTurn = false;
    }
}
