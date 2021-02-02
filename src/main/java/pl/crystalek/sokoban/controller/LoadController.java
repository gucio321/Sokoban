package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.io.MainLoader;

public final class LoadController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private VBox boxList;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    public VBox getBoxList() {
        return boxList;
    }

    @FXML
    void back(final ActionEvent event) {
        //odpalony zostaje selectLevel.fxml lub gameModeChoice.fxml, w zależności od wywołania
    }

    @FXML
    void delete(final ActionEvent event) {
        //usuwa zapis/mape
    }

    @FXML
    void load(final ActionEvent event) {
        //laduje zapis/mape do edycji
    }

}
