package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import pl.crystalek.sokoban.io.MainLoader;

public final class FirstModuleGameWindowController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private GridPane mapBox;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {

    }

}
