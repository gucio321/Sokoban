package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import pl.crystalek.sokoban.io.MainLoader;

public final class MapEditorController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private GridPane mapBox;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void leave(final ActionEvent event) {

    }

    @FXML
    void changeMapName(final ActionEvent event) {

    }

    @FXML
    void resetMap(final ActionEvent event) {

    }

    @FXML
    void saveMap(final ActionEvent event) {

    }

    @FXML
    void showMapList(final ActionEvent event) {

    }

}
