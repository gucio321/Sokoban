package pl.crystalek.sokoban.controller.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.controller.ChangeNameController;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.GameModuleChoiceController;
import pl.crystalek.sokoban.controller.load.LoadMapToEditController;
import pl.crystalek.sokoban.controller.load.LoadUtil;
import pl.crystalek.sokoban.controller.type.ChangeNameType;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;

public final class MapEditorController implements Controller {
    private MainLoader mainLoader;
    private MapEditor mapEditor;
    private ImportMap importMap;
    private ExportMap exportMap;
    private SaveMap saveMap;
    @FXML
    private GridPane mapBox;
    @FXML
    private VBox blockBox;
    @FXML
    private AnchorPane editedAreaPane;
    @FXML
    private Button importMapButton;
    @FXML
    private Button exportMapButton;
    @FXML
    private Button saveMapButton;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
        this.importMap = new ImportMap(mainLoader);
        importMapButton.setOnAction(importMap);
        this.exportMap = new ExportMap(mainLoader);
        exportMapButton.setOnAction(exportMap);
        this.saveMap = new SaveMap(mainLoader);
        saveMapButton.setOnAction(saveMap);
    }

    @FXML
    private void leave(final ActionEvent event) {
        if (!mapEditor.getEditedMap().isChangesToSave()) {
            clearMapWhenLeave();
            return;
        }

        final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
        controller.setConfirmationType(ConfirmationType.LEAVE);
        controller.getTextLabel().setText("Czy na pewno chcesz opuscic edytor? Zapisz mape, w przeciwnym wypadku utracisz niezapisane zmiany.");
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    @FXML
    private void changeMapName(final ActionEvent event) {
        final ChangeNameController controller = mainLoader.getController(ChangeNameController.class);
        controller.setChangeNameType(ChangeNameType.MAP);
        controller.getTextLabel().setText("Podaj nazwe mapy");
        mainLoader.getViewLoader().setWindow(ChangeNameController.class);
    }

    @FXML
    private void resetMap(final ActionEvent event) {
        final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
        controller.setConfirmationType(ConfirmationType.RESETMAP);
        controller.getTextLabel().setText("Czy na pewno chcesz zresetować mapę?");
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    @FXML
    private void showMapList(final ActionEvent event) {
        final LoadMapToEditController controller = mainLoader.getController(LoadMapToEditController.class);
        controller.setLoadUtil(new LoadUtil(mainLoader));
        mainLoader.getViewLoader().setWindow(LoadMapToEditController.class);
    }

    public void clearMap() {
        for (final Node child : mapBox.getChildren()) {
            if (child instanceof Pane) {
                ((Pane) child).getChildren().clear();
            }
        }

    }

    public void clearMapWhenLeave() {
        final ViewLoader viewLoader = mainLoader.getViewLoader();
        viewLoader.setWindow(GameModuleChoiceController.class);
        mapBox.setGridLinesVisible(false);
        mapBox.getChildren().clear();
        blockBox.getChildren().clear();
    }

    public MapEditor getMapEditor() {
        return mapEditor;
    }

    public void setMapEditor(final MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    public GridPane getMapBox() {
        return mapBox;
    }

    public VBox getBlockBox() {
        return blockBox;
    }

    public AnchorPane getEditedAreaPane() {
        return editedAreaPane;
    }

    public ExportMap getExportMap() {
        return exportMap;
    }

    public SaveMap getSaveMap() {
        return saveMap;
    }

    public ImportMap getImportMap() {
        return importMap;
    }
}
