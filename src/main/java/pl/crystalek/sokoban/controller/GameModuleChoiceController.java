package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.MapEditor;

public final class GameModuleChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(SokobanMainController.class);
    }

    @FXML
    void createLevel(final ActionEvent event) {
        final MapEditor mapEditor = new MapEditor(mainLoader);
        mainLoader.getController(MapEditorController.class).setMapEditor(mapEditor);
        mapEditor.openCreator();
        mainLoader.getViewLoader().setWindow(MapEditorController.class);
    }

    @FXML
    void chooseGameMode(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModeChoiceController.class);
    }

}
