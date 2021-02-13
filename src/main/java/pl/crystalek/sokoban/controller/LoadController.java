package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.controller.type.ExecutionSource;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.MapEditor;
import pl.crystalek.sokoban.map.model.UserMap;

import java.net.URL;
import java.util.ResourceBundle;

public final class LoadController implements Controller, Initializable {
    private MainLoader mainLoader;
    private ExecutionSource executionSource;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox boxList;
    @FXML
    private Button loadButton;
    @FXML
    private Label infoLabel;


    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(executionSource.getControllerClass());
        loadButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    @FXML
    private void delete(final ActionEvent event) {
        switch (executionSource) {
            case MAPEDITOR:
                final ConfirmationPane confirmationPane = mainLoader.getController(ConfirmationPane.class);
                confirmationPane.setConfirmationType(ConfirmationType.DELETEMAP);
                confirmationPane.getTextLabel().setText("Czy na pewno chcesz usunąć tą mapę?");
                mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();
                break;
            case GAMEMODECHOICE:
                break;
            case SELECTLEVEL:
                break;
        }
        //usuwa zapis/mape
    }

    @FXML
    private void load(final ActionEvent event) {
        switch (executionSource) {
            case MAPEDITOR:
                final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
                final UserMap editedMap = mapEditorController.getMapEditor().getEditedMap();
                if (editedMap.isChangesToSave()) {
                    final ConfirmationPane confirmationPane = mainLoader.getController(ConfirmationPane.class);
                    confirmationPane.setConfirmationType(ConfirmationType.LOADMAP);
                    confirmationPane.getTextLabel().setText("Czy na pewno chcesz wczytać nową mape? Posiadasz niezapisane zmiany na aktualnie edytowanej mapie! Jeśli klikniesz tak, zmiany zostaną utracone.");
                    mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();
                    return;
                }
                mapEditorController.clearMap();
                final MapEditor mapEditor = mapEditorController.getMapEditor();
                final UserMap choosenMap = mapEditorController.getChoosenMap();
                mapEditor.setEditedMap(choosenMap);
                mapEditor.getConvertStringToGridPane().stringToGridPane(choosenMap.getMapLines());
                mainLoader.getViewLoader().setWindow(getClass());
                loadButton.setDisable(true);
                deleteButton.setDisable(true);
                break;
            case GAMEMODECHOICE:
                break;
            case SELECTLEVEL:
                break;
        }
        //laduje zapis/mape do edycji
    }

    void setExecutionSource(final ExecutionSource executionSource) {
        this.executionSource = executionSource;
    }

    VBox getBoxList() {
        return boxList;
    }

    Label getInfoLabel() {
        return infoLabel;
    }

    Button getDeleteButton() {
        return deleteButton;
    }

    Button getLoadButton() {
        return loadButton;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        loadButton.setDisable(true);
        deleteButton.setDisable(true);
    }
}
