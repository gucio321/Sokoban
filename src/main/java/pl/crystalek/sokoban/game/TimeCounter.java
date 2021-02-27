package pl.crystalek.sokoban.game;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.LevelFinishController;
import pl.crystalek.sokoban.controller.LevelLostController;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileManager;
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
        final GameController gameController = mainLoader.getController(GameController.class);
        final Label timeLabel = gameController.getTimeLabel();
        final Progress progress = gameController.getGame().getOldProgress();
        counting = progress.getTimeInSeconds();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!pause) {
                    if (counting > 0) {
                        Platform.runLater(() -> timeLabel.setText(String.valueOf(counting + 1)));
                    } else {
                        if (progress.isCloseGameWhenTimeEnd()) {
                            cancel();
                            Platform.runLater(() -> mainLoader.getController(LevelLostController.class).getPlayTime().setText(TimeUtil.getDateInString((progress.getRanking().getPlayTime() + playTime) * 1_000L, ", ", true)));
                            mainLoader.getViewLoader().setWindow(LevelLostController.class);
                        }
                    }

                    playTime++;
                    counting--;
                }
            }
        }, 0, 1000);
        this.timer = timer;
    }

    public void stop() {
        timer.cancel();
        final Progress progress = mainLoader.getController(GameController.class).getGame().getOldProgress();
        final Ranking ranking = progress.getRanking();

        final int playTime = ranking.getPlayTime() + this.playTime;
        final int pointsForTime = Math.min(counting * POINT_FOR_TIME, progress.getBonus());

        ranking.setPlayTime(playTime);
        ranking.setPointsForTime(pointsForTime);
        mainLoader.getRankingManager().add(ranking);
        progress.setRanking(new Ranking());

        final LevelFinishController levelFinishController = mainLoader.getController(LevelFinishController.class);
        levelFinishController.getPlayTime().setText(TimeUtil.getDateInString(playTime * 1_000L, ", ", true));
        levelFinishController.getGainedPoints().setText(String.valueOf(pointsForTime));
        mainLoader.getViewLoader().setWindow(LevelFinishController.class);

        final FileManager fileManager = mainLoader.getFileManager();
        try {
            fileManager.getFileSaver().saveFile(fileManager.getRankingFile(), mainLoader.getRankingManager());
        } catch (final SaveUserFileException exception) {
            exception.printStackTrace();
        }
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
