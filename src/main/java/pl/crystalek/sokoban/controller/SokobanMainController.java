package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class SokobanMainController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void exit(final ActionEvent event) {
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void settings(final ActionEvent event) {
        mainLoader.getStage(GameSettingsController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void showStatistic(final ActionEvent event) {
        mainLoader.getStage(ShowStatisticController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void start(final ActionEvent event) {
        mainLoader.getStage(GameModuleChoiceController.class).show();
        mainLoader.getStage(getClass()).close();
    }

}
