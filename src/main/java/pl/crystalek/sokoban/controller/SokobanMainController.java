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
        mainLoader.getViewLoader().getMainStage().close();
    }

    @FXML
    void settings(final ActionEvent event) {
        mainLoader.getController(GameSettingsController.class).updateSettings();
        mainLoader.getViewLoader().setWindow(GameSettingsController.class);
    }

    @FXML
    void showStatistic(final ActionEvent event) {
        mainLoader.getController(ShowStatisticController.class).showStatistic();
        mainLoader.getViewLoader().setWindow(ShowStatisticController.class);
    }

    @FXML
    void start(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModuleChoiceController.class);
    }

}
