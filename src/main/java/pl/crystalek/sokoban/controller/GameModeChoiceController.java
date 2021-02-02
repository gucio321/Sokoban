package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameModeChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {

    }

    @FXML
    void ownMap(final ActionEvent event) {

    }

    @FXML
    void randomLevel(final ActionEvent event) {

    }

    @FXML
    void risingLevel(final ActionEvent event) {

    }

}
