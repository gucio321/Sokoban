package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameModuleChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        mainLoader.getStage(SokobanMainController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void createLevel(final ActionEvent event) {
        mainLoader.getStage(MapEditorController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void chooseGameMode(final ActionEvent event) {
        mainLoader.getStage(GameModeChoiceController.class).show();
        mainLoader.getStage(getClass()).close();
    }

}
