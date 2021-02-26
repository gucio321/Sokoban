package pl.crystalek.sokoban.controller.load;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.SerializationUtils;
import pl.crystalek.sokoban.controller.*;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.TimeCounter;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.map.UserMap;
import pl.crystalek.sokoban.ranking.Ranking;

import java.util.Comparator;
import java.util.List;

public final class LoadGameController implements Controller, Load {
    private MainLoader mainLoader;
    private LoadUtil loadUtil;
    private Game game;
    private EventHandler<KeyEvent> resetMapListener;
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
        final EventHandler<KeyEvent> eventHandler = event1 -> {
            if (event1.getCode() == KeyCode.SPACE) {
                final Progress progress = game.getProgress();
                final TimeCounter timeCounter = game.getTimeCounter();
                final Ranking ranking = progress.getRanking();
                ranking.setPlayTime(ranking.getPlayTime() + timeCounter.getPlayTime());
                timeCounter.getTimer().cancel();
                progress.setSetCrates(this.progress.getSetCrates());

                game.loadGame(mainLoader.getController(GameController.class).getMapBox(), this.progress.getMapLines(), progress);
            }
        };

        mainLoader.getViewLoader().getMainStage().addEventFilter(KeyEvent.KEY_RELEASED, eventHandler);
        final Object choosenObject = loadUtil.getChosenObject();
        final Game game = new Game(mainLoader);
        this.game = game;
        final GridPane mapBox = mainLoader.getController(GameController.class).getMapBox();

        if (choosenObject instanceof Progress) {
            final Progress progress = (Progress) choosenObject;
            game.loadGame(mapBox, progress.getMapLines(), SerializationUtils.clone(progress));
            this.progress = progress;
        } else if (choosenObject instanceof DefaultMap) {
            final DefaultMap defaultMap = (DefaultMap) choosenObject;
            final List<String> mapLines = defaultMap.getMapLines();
            final Progress progress = new Progress(new String[mapLines.stream().max(Comparator.comparingInt(String::length)).get().length()][mapLines.size()]);
            progress.setMapName(defaultMap.getName());
            progress.setUserMap(false);
            progress.getRanking().setMapName(defaultMap.getName());
            this.progress = progress;

            game.loadGame(mapBox, defaultMap.getMapLines(), SerializationUtils.clone(progress));
            this.progress.setMapLines(mapLines);
        } else {
            final UserMap choosenMap = (UserMap) choosenObject;
            final List<String> mapLines = choosenMap.getMapLines();
            final Progress progress = new Progress(new String[mapLines.stream().max(Comparator.comparingInt(String::length)).get().length()][mapLines.size()]);
            progress.setMapName(choosenMap.getName());
            progress.setUserMap(true);
            progress.getRanking().setMapName(choosenMap.getName());
            final String start = game.loadGame(mapBox, mapLines, SerializationUtils.clone(progress));
            if (!start.isEmpty()) {
                mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", start);
                return;
            }
            this.progress = progress;
            this.progress.setMapLines(mapLines);
        }

        loadButton.setDisable(true);
        deleteButton.setDisable(true);
        mainLoader.getViewLoader().setWindow(GameController.class);
        this.resetMapListener = eventHandler;
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

    public EventHandler<KeyEvent> getResetMapListener() {
        return resetMapListener;
    }

    public Progress getProgress() {
        return progress;
    }
}
