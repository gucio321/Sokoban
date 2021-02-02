package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameSettingsController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {

    }

    @FXML
    void brightnessMinus(final ActionEvent event) {

    }

    @FXML
    void brightnessPlus(final ActionEvent event) {

    }

    @FXML
    void control(final ActionEvent event) {

    }

    @FXML
    void soundButton(final ActionEvent event) {

    }

    @FXML
    void texture(final ActionEvent event) {

    }

}
