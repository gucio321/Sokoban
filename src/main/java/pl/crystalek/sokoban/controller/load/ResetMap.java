package pl.crystalek.sokoban.controller.load;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.crystalek.sokoban.controller.GameController;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.TimeCounter;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.ranking.Ranking;

public final class ResetMap implements EventHandler<KeyEvent> {
    private final MainLoader mainLoader;

    public ResetMap(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final KeyEvent event) {
        if (event.getCode() != KeyCode.SPACE) {
            return;
        }

        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final Game game = loadGameController.getGame();
        final Progress loadGameProgress = loadGameController.getProgress();
        final Progress progress = game.getProgress();
        final TimeCounter timeCounter = game.getTimeCounter();
        final Ranking ranking = progress.getRanking();

        ranking.setPlayTime(ranking.getPlayTime() + timeCounter.getPlayTime());
        timeCounter.getTimer().cancel();
        progress.setSetCrates(loadGameProgress.getSetCrates());

        game.loadGame(mainLoader.getController(GameController.class).getMapBox(), loadGameProgress.getMapLines(), progress);
    }
}
