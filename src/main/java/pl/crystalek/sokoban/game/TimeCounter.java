package pl.crystalek.sokoban.game;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.GameController;
import pl.crystalek.sokoban.controller.LevelFinishController;
import pl.crystalek.sokoban.controller.load.LoadGameController;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.ranking.Ranking;
import pl.crystalek.sokoban.util.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;

public final class TimeCounter {
    private final static int POINT_FOR_TIME = 20;
    private final MainLoader mainLoader;
    private int counting;
    private int playTime;
    private Timer timer;
    private boolean pause = false;

    public TimeCounter(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    public void start() {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final Label timeLabel = mainLoader.getController(GameController.class).getTimeLabel();
        final Object chosenObject = loadGameController.getLoadUtil().getChosenObject();
        if (!(chosenObject instanceof DefaultMap)) {
            counting = 0;
            final Progress progress = (Progress) chosenObject;
            for (final DefaultMap defaultMap : mainLoader.getMapManager().getDefaultMapList()) {
                if (defaultMap.getName().equalsIgnoreCase(progress.getMapName())) {
                    counting = defaultMap.getTimeInSeconds();
                }
            }
        } else {
            final DefaultMap defaultMap = (DefaultMap) chosenObject;
            counting = defaultMap.getTimeInSeconds();
        }

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                playTime++;
                if (!pause) {

                    if (counting >= 0) {
                        Platform.runLater(() -> timeLabel.setText(String.valueOf(counting + 1)));
                    }
                    counting--;
                }
                final LoadGameController controller = mainLoader.getController(LoadGameController.class);
            }
        }, 0, 1000);
        this.timer = timer;
    }

    public void stop() {
        timer.cancel();
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final Progress progress = loadGameController.getGame().getProgress();
        final Ranking ranking = progress.getRanking();
        final Object chosenObject = loadGameController.getLoadUtil().getChosenObject();
        int defaultPoint = 0;
        int maxBonusPoint = 0;

        if (chosenObject instanceof DefaultMap) {
            final DefaultMap defaultMap = (DefaultMap) chosenObject;
            defaultPoint = defaultMap.getDefaultPointNumber();
            maxBonusPoint = defaultMap.getBonus();
        }

        final int playTime = ranking.getPlayTime() + this.playTime;
        final int bonusPoint = counting * POINT_FOR_TIME;
        final int pointsForTime = counting < 0 ? Math.max(bonusPoint, -maxBonusPoint) : Math.min(bonusPoint, maxBonusPoint);
        final int totalPoints = defaultPoint + pointsForTime;

        ranking.setPlayTime(playTime);
        ranking.setPointsForTime(pointsForTime);
        ranking.setTotalPoints(totalPoints);
        mainLoader.getRankingManager().add(ranking);
        progress.setRanking(new Ranking());

        final LevelFinishController levelFinishController = mainLoader.getController(LevelFinishController.class);
        levelFinishController.getPlayTime().setText(TimeUtil.getDateInString(playTime * 1_000L, ", ", true));
        levelFinishController.getGainedPoints().setText(String.valueOf(totalPoints));
        mainLoader.getViewLoader().setWindow(LevelFinishController.class);
    }

    public void setPause(final boolean pause) {
        this.pause = pause;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getPlayTime() {
        return playTime;
    }
}
