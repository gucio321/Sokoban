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
        mainLoader.getStage(DoYouWantToController.class).show(); //i tu dalej cuda
    }

    @FXML
    void changeMapName(final ActionEvent event) {
        mainLoader.getStage(ChangeNameController.class).show();
    }

    @FXML
    void resetMap(final ActionEvent event) {
        mainLoader.getStage(DoYouWantToController.class).show(); //i tu dalej cuda

    }

    @FXML
    void saveMap(final ActionEvent event) {
        //jesli mapa nie ma nadanej nazwy, odpalany zostaje ChangeName
        mainLoader.getStage(ChangeNameController.class).show();
    }

    @FXML
    void showMapList(final ActionEvent event) {
        mainLoader.getStage(LoadController.class).show();
    }

}
