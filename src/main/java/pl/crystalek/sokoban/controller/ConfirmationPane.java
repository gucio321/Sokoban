package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.MapEditor;
import pl.crystalek.sokoban.map.model.UserMap;

import java.time.LocalDateTime;

public final class ConfirmationPane implements Controller {
    private MainLoader mainLoader;
    @FXML
    private Label textLabel;
    private ConfirmationType confirmationType;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void no(final ActionEvent event) {
        mainLoader.getViewLoader().getStage(getClass()).close();
    }

    @FXML
    private void yes(final ActionEvent event) {
        switch (confirmationType) {
            case STATISTIC:
                mainLoader.getFileManager().getStatistic().resetStatistic();
                mainLoader.getController(ShowStatisticController.class).showStatistic();
                break;
            case RESETMAP:
                final MapEditorController mapEditorController1 = mainLoader.getController(MapEditorController.class);
                final MapEditor mapEditor1 = mapEditorController1.getMapEditor();
                mapEditorController1.clearMap();
                mapEditor1.getEditedMap().setChangesToSave(true);
                mapEditor1.getEditedMap().setModificationDate(LocalDateTime.now());
                mainLoader.getController(DialogController.class).showDialogWindow("info", "Informacja", "Mapa zostala wyczyszczona");
                break;
            case DELETESAVE:
                break;
            case DELETEMAP:
                final MapEditorController mapEditorController2 = mainLoader.getController(MapEditorController.class);
                final UserMap choosenMap1 = mapEditorController2.getChoosenMap();
                mainLoader.getUserMapManager().deleteMap(choosenMap1);
                mainLoader.getFileManager().getUserMapFileList().get(choosenMap1.getMapName()).delete();
                final LoadController loadController = mainLoader.getController(LoadController.class);
                loadController.getBoxList().getChildren().remove(mapEditorController2.getChoosenMapButton());
                loadController.getLoadButton().setDisable(true);
                loadController.getDeleteButton().setDisable(true);
                break;
            case LEAVE:
                mainLoader.getController(MapEditorController.class).clearMapWhenLeave();
                break;
            case LOADMAP:
                final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
                final MapEditor mapEditor = mapEditorController.getMapEditor();
                final UserMap choosenMap = mapEditorController.getChoosenMap();
                mapEditorController.clearMap();
                mapEditor.setEditedMap(choosenMap);
                mapEditor.getConvertStringToGridPane().stringToGridPane(choosenMap.getMapLines());
                mainLoader.getViewLoader().setWindow(LoadController.class);
                final LoadController controller = mainLoader.getController(LoadController.class);
                controller.getLoadButton().setDisable(true);
                controller.getDeleteButton().setDisable(true);
                break;
            case EXPORTMAP:
                mainLoader.getController(MapEditorController.class).showFileChooser();
                break;
            case OVERRIDEFILEMAP:
                mainLoader.getController(MapEditorController.class).writeFile();
                break;
        }
        mainLoader.getViewLoader().getStage(getClass()).close();
        textLabel.setText("");
    }

    Label getTextLabel() {
        return textLabel;
    }

    void setConfirmationType(final ConfirmationType confirmationType) {
        this.confirmationType = confirmationType;
    }
}
