package pl.crystalek.sokoban.util;

import java.util.Timer;
import java.util.TimerTask;

public final class TimerUtil {
    public static void setTimer(final Runnable runnable, final long timeInSeconds) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, timeInSeconds * 1_000);
    }
}
