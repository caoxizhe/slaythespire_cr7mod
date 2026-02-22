package cr7mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.EventRoom;
import cr7mod.utils.FansGainTracker;
import cr7mod.utils.MoroccoStatusTracker;

@SpirePatch(clz = MonsterRoom.class, method = "onPlayerEntry", paramtypez = {})
final class ResetFansTrackerPatch_MonsterRoom {
    @SpirePostfixPatch
    public static void postfix() {
        FansGainTracker.reset();
        MoroccoStatusTracker.reset();
    }
}

@SpirePatch(clz = MonsterRoomBoss.class, method = "onPlayerEntry", paramtypez = {})
final class ResetFansTrackerPatch_MonsterRoomBoss {
    @SpirePostfixPatch
    public static void postfix() {
        FansGainTracker.reset();
        MoroccoStatusTracker.reset();
    }
}

@SpirePatch(clz = MonsterRoomElite.class, method = "onPlayerEntry", paramtypez = {})
final class ResetFansTrackerPatch_MonsterRoomElite {
    @SpirePostfixPatch
    public static void postfix() {
        FansGainTracker.reset();
        MoroccoStatusTracker.reset();
    }
}

@SpirePatch(clz = EventRoom.class, method = "onPlayerEntry", paramtypez = {})
final class ResetFansTrackerPatch_EventRoom {
    @SpirePostfixPatch
    public static void postfix() {
        FansGainTracker.reset();
        MoroccoStatusTracker.reset();
    }
}

