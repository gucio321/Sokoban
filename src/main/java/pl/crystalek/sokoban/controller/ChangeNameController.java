package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;

import java.time.LocalDateTime;

public final class ChangeNameController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label textLabel;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void cancel(final ActionEvent event) {
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        final Game game = mainLoader.getController(GameController.class).getGame();
        mainStage.addEventFilter(KeyEvent.KEY_RELEASED, game.getResetMapListener());
        mainStage.addEventFilter(KeyEvent.KEY_PRESSED, game.getPlayerMoveListener());
        game.getTimeCounter().setPause(false);
        mainLoader.getViewLoader().setWindow(GameController.class);
    }

    @FXML
    private void save(final ActionEvent event) {
        final String text = nameTextField.getText().trim();
        if (text.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Podaj nazwę zapisu");
            return;
        }

        if (mainLoader.getProgressManager().getSaveList().stream().anyMatch(x -> x.getName().equalsIgnoreCase(text))) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Taka nazwa zapisu już istnieje!");
            return;
        }

        final Game game = mainLoader.getController(GameController.class).getGame();
        final Progress progress = game.getProgress();
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        progress.setOldName(progress.getOldName());
        progress.setProgressName(text);
        progress.setModificationDate(LocalDateTime.now());
        mainLoader.getController(GameController.class).save();
        mainLoader.getViewLoader().setWindow(GameController.class);
        game.getTimeCounter().setPause(false);
        mainStage.addEventFilter(KeyEvent.KEY_RELEASED, game.getResetMapListener());
        mainStage.addEventFilter(KeyEvent.KEY_PRESSED, game.getPlayerMoveListener());

    }

    public Label getTextLabel() {
        return textLabel;
    }
}
