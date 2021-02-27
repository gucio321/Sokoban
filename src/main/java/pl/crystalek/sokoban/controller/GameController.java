package pl.crystalek.sokoban.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.SerializationUtils;
import pl.crystalek.sokoban.controller.load.LoadGameController;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.TimeCounter;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileSaveType;
import pl.crystalek.sokoban.ranking.Ranking;

import java.util.List;

public final class GameController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private GridPane mapBox;
    @FXML
    private Label timeLabel;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final MouseEvent event) {
        if (!mainLoader.getController(LoadGameController.class).getGame().getProgress().isChangesToSave()) {
            leaveGame();
            return;
        }

        final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
        controller.setConfirmationType(ConfirmationType.LEAVEGAME);
        controller.getTextLabel().setText("Masz niezapisane zmiany, czy na pewno chcesz wyjsc z gry?");
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    void leaveGame() {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final Game game = loadGameController.getGame();
        loadGameController.getLoadUtil().showProgress();
        mainLoader.getViewLoader().setWindow(LoadGameController.class);
        mainLoader.getViewLoader().getMainStage().removeEventFilter(KeyEvent.KEY_PRESSED, game.getPlayerMoveListener());
        mainLoader.getViewLoader().getMainStage().removeEventFilter(KeyEvent.KEY_RELEASED, loadGameController.getResetMapListener());
        mapBox.getChildren().clear();
        final TimeCounter timeCounter = game.getTimeCounter();
        final Ranking ranking = game.getProgress().getRanking();
        ranking.setPlayTime(ranking.getPlayTime() + timeCounter.getPlayTime());
        timeCounter.getTimer().cancel();
    }

    @FXML
    private void saveGame(final MouseEvent event) {
        final Progress progress = mainLoader.getController(LoadGameController.class).getGame().getProgress();
        if (!progress.isChangesToSave()) {
            mainLoader.getController(DialogController.class).showDialogWindow("warning", "Ostrzezenie!", "Brak zmian do zapisu");
            return;
        }

        if (progress.getProgressName() == null) {
            changeName();
            return;
        }
        save();
    }

    void save() {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final Game game = loadGameController.getGame();
        final Progress progress = game.getProgress();
        final String oldSaveName = progress.getOldName();
        if (oldSaveName != null) {
            mainLoader.getFileManager().getUserGameSaveList().remove(oldSaveName).delete();
            progress.setOldName(null);
        }

        progress.setMapLines(game.getConvertGridPaneToString().convertGridPaneToString(game.getBoxLocationList()));
        progress.setStringEditedBlocks(game.getConvertImageToStringImage().convertImageToStringImage(game.getDeleteImageList()));
        progress.getRanking().setPlayTime(game.getTimeCounter().getPlayTime() + progress.getRanking().getPlayTime());
        progress.setChangesToSave(false);
        final List<Progress> progressList = mainLoader.getProgressManager().getSaveList();
        final Progress progressCopy = SerializationUtils.clone(progress);
        final int progressIndex = progressList.indexOf(loadGameController.getProgress());

        if (progressIndex != -1) {
            progressList.set(progressIndex, progressCopy);
        } else {
            progressList.add(progressCopy);
        }

        try {
            mainLoader.getFileManager().getFileSaver().saveUserFile(progress, FileSaveType.PROGRESS);
            mainLoader.getController(DialogController.class).showDialogWindow("info", "Informacja", "Zapis zostal pomyslnie zapisany!");
        } catch (final SaveUserFileException exception) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", exception.getMessage());
        }
    }

    @FXML
    private void changeSaveName(final MouseEvent event) {
        changeName();
    }

    private void changeName() {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final ChangeNameController changeNameController = mainLoader.getController(ChangeNameController.class);
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        mainStage.removeEventFilter(KeyEvent.KEY_RELEASED, loadGameController.getResetMapListener());
        mainStage.removeEventFilter(KeyEvent.KEY_PRESSED, loadGameController.getGame().getPlayerMoveListener());
        loadGameController.getGame().getTimeCounter().setPause(true);
        changeNameController.getTextLabel().setText("Podaj nazwe zapisu");
        mainLoader.getViewLoader().setWindow(ChangeNameController.class);
    }

    public GridPane getMapBox() {
        return mapBox;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }
}
