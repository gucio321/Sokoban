package pl.crystalek.sokoban.controller.load;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.SerializationUtils;
import pl.crystalek.sokoban.controller.*;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.map.UserMap;

import java.util.Comparator;
import java.util.List;

public final class LoadGameController implements Controller, Load {
    private MainLoader mainLoader;
    private LoadUtil loadUtil;
    private Game game;
    private ResetMap resetMap;
    private Progress progress;
    @FXML
    private VBox mapBox;
    @FXML
    private Label mapInfoLabel;
    @FXML
    private Label saveInfoLabel;
    @FXML
    private VBox saveBox;
    @FXML
    private Button loadButton;
    @FXML
    private Button deleteButton;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        loadButton.setDisable(true);
        deleteButton.setDisable(true);
        mainLoader.getViewLoader().setWindow(GameModeChoiceController.class);
    }

    @FXML
    private void delete(final ActionEvent event) {
        final ConfirmationController confirmationController = mainLoader.getController(ConfirmationController.class);
        confirmationController.setConfirmationType(ConfirmationType.DELETE);
        confirmationController.setLoad(this);
        confirmationController.getTextLabel().setText("Czy na pewno chcesz usunąć " + (loadUtil.getChosenObject() instanceof Progress ? "ten zapis?" : "tą mapę?"));
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    @FXML
    private void loadButton(final ActionEvent event) {
        this.resetMap = new ResetMap(mainLoader);
        this.game = new Game(mainLoader);
        final DefaultMap choosenObject = loadUtil.getChosenObject();
        final List<String> mapLines = choosenObject.getMapLines();

        if (choosenObject instanceof Progress) {
            progress = (Progress) choosenObject;
        } else {
            final String name = choosenObject.getName();
            progress = new Progress(new String[mapLines.stream().max(Comparator.comparingInt(String::length)).get().length()][mapLines.size()], name, choosenObject);
            progress.setUserMap(choosenObject instanceof UserMap);
            progress.getRanking().setMapName(name);
        }

        final String start = game.loadGame(mainLoader.getController(GameController.class).getMapBox(), mapLines, SerializationUtils.clone(progress));
        if (!start.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", start);
            return;
        }

        loadButton.setDisable(true);
        deleteButton.setDisable(true);
        mainLoader.getViewLoader().getMainStage().addEventFilter(KeyEvent.KEY_RELEASED, resetMap);
        mainLoader.getViewLoader().setWindow(GameController.class);
        progress.setMapLines(mapLines);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public LoadUtil getLoadUtil() {
        return loadUtil;
    }

    public void setLoadUtil(final LoadUtil loadUtil, final boolean userMapSave) {
        loadUtil.setUserMapSave(userMapSave);
        loadUtil.showProgress();
        this.loadUtil = loadUtil;
    }

    public VBox getMapBox() {
        return mapBox;
    }

    @Override
    public Label getMapInfoLabel() {
        return mapInfoLabel;
    }

    public Label getSaveInfoLabel() {
        return saveInfoLabel;
    }

    public VBox getSaveBox() {
        return saveBox;
    }

    public Game getGame() {
        return game;
    }

    public ResetMap getResetMapListener() {
        return resetMap;
    }

    public Progress getProgress() {
        return progress;
    }
}
