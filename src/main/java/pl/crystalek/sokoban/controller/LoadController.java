package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.io.MainLoader;

public final class LoadController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private VBox boxList;

    public VBox getBoxList() {
        return boxList;
    }

    @FXML
    void back(final ActionEvent event) {

    }

    @FXML
    void delete(final ActionEvent event) {

    }

    @FXML
    void load(final ActionEvent event) {

    }

}
