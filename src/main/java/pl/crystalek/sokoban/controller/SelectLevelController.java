package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class SelectLevelController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        mainLoader.getStage(GameModeChoiceController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void showGameSaveList(final ActionEvent event) {
        mainLoader.getStage(LoadController.class).show();
        mainLoader.getStage(getClass()).close();
    }
}
