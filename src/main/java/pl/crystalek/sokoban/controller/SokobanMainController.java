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
    }

    @FXML
    void settings(final ActionEvent event) {

    }

    @FXML
    void showRanking(final ActionEvent event) {

    }

    @FXML
    void start(final ActionEvent event) {

    }

}
