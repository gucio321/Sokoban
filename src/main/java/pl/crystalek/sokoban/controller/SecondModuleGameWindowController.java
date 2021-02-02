package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import pl.crystalek.sokoban.io.MainLoader;

public final class SecondModuleGameWindowController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private GridPane mapBox;

    @FXML
    void back(final ActionEvent event) {

    }

    @FXML
    void saveGame(final ActionEvent event) {

    }

}
