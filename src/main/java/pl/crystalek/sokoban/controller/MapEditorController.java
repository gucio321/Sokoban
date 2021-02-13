package pl.crystalek.sokoban.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import pl.crystalek.sokoban.controller.type.ChangeNameType;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.controller.type.ExecutionSource;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;
import pl.crystalek.sokoban.map.MapEditor;
import pl.crystalek.sokoban.map.UserMapManager;
import pl.crystalek.sokoban.map.model.UserMap;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class MapEditorController implements Controller {
    private MainLoader mainLoader;
    private MapEditor mapEditor;
    private UserMap choosenMap;
    private Button choosenMapButton;
    private File fileToSave;
    @FXML
    private GridPane mapBox;
    @FXML
    private VBox blockBox;
    @FXML
    private AnchorPane editedAreaPane;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void leave(final ActionEvent event) {
        if (!mapEditor.getEditedMap().isChangesToSave()) {
            clearMapWhenLeave();
            return;
        }
        final ConfirmationPane controller = mainLoader.getController(ConfirmationPane.class);
        controller.setConfirmationType(ConfirmationType.LEAVE);
        controller.getTextLabel().setText("Czy na pewno chcesz opuscic edytor? Zapisz mape, w przeciwnym wypadku utracisz niezapisane zmiany.");
        mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();

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
        final ConfirmationPane controller = mainLoader.getController(ConfirmationPane.class);
        controller.setConfirmationType(ConfirmationType.RESETMAP);
        controller.getTextLabel().setText("Czy na pewno chcesz zresetować mapę?");
        mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();

    }

    @FXML
    private void saveMap(final ActionEvent event) {
        final UserMap editedMap = mapEditor.getEditedMap();
        if (!editedMap.isChangesToSave()) {
            mainLoader.getController(DialogController.class).showDialogWindow("warning", "Ostrzezenie!", "Brak zmian do zapisu");
            return;
        }

        if (editedMap.getMapName() == null) {
            final ChangeNameController controller = mainLoader.getController(ChangeNameController.class);
            controller.setChangeNameType(ChangeNameType.MAP);
            controller.getTextLabel().setText("Podaj nazwe mapy");
            mainLoader.getViewLoader().setWindow(ChangeNameController.class);
            return;
        }
        saveMap();

    }

    void saveMap() {
        final UserMap editedMap = mapEditor.getEditedMap();
        final String oldMapName = editedMap.getOldMapName();
        if (oldMapName != null) {
            final File fileMapToDelete = mainLoader.getFileManager().getUserMapFileList().remove(oldMapName);
            fileMapToDelete.delete();
            editedMap.setOldMapName(null);
        }

        editedMap.setMapLines(mapEditor.getGridPaneToString().convertGridPaneToString());

        try {
            mainLoader.getFileManager().getFileSaver().saveUserMap(editedMap);
            editedMap.setChangesToSave(false);
            mainLoader.getUserMapManager().addMap(editedMap);
            mainLoader.getController(DialogController.class).showDialogWindow("info", "Informacja", "Mapa zostala pomyslnie zapisana!");
        } catch (final SaveUserFileException exception) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", exception.getMessage());
        }
    }

    @FXML
    private void showMapList(final ActionEvent event) {
        final LoadController controller = mainLoader.getController(LoadController.class);
        controller.setExecutionSource(ExecutionSource.MAPEDITOR);
        final VBox boxList = controller.getBoxList();
        final ObservableList<Node> children = boxList.getChildren();
        children.clear();
        final UserMapManager mapManager = mainLoader.getUserMapManager();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final Label label = controller.getInfoLabel();

        for (final UserMap userMap : mapManager.getUserMapList()) {
            final Button button = new Button();
            button.setOnMouseEntered(mouseEvent -> {
                label.setText(
                        "Data stworzenia: " + userMap.getCreationDate().format(dateTimeFormatter) +
                                "\nData modyfikacji: " + userMap.getModificationDate().format(dateTimeFormatter)
                );
                label.setStyle("-fx-background-color: #a0a0a0");
            });
            button.setOnMouseExited(mouseEvent -> {
                label.setText("");
                label.setStyle(null);
            });
            button.setOnAction(event1 -> {
                this.choosenMapButton = button;
                this.choosenMap = userMap;
                controller.getLoadButton().setDisable(false);
                controller.getDeleteButton().setDisable(false);
            });
            button.setMaxWidth(149);
            button.setMaxHeight(27);
            button.setAlignment(Pos.CENTER);
            button.setTextFill(Paint.valueOf("#a10000"));
            button.setStyle("-fx-background-color: #a0a0a0; -fx-border-color: #aaaaaa");
            button.setFont(new Font("System Bold Italic", 12));
            button.setText(userMap.getMapName());
            children.add(button);
        }
        mainLoader.getViewLoader().setWindow(LoadController.class);
    }

    @FXML
    private void exportMap() {
        final UserMap editedMap = mapEditor.getEditedMap();
        if (editedMap.isChangesToSave()) {
            final ConfirmationPane controller = mainLoader.getController(ConfirmationPane.class);
            controller.setConfirmationType(ConfirmationType.EXPORTMAP);
            controller.getTextLabel().setText("Czy na pewno chcesz wyeksportować mapę bez aktualnych poprawek?");
            mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();
            return;
        }
        showFileChooser();


    }

    void showFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.setInitialFileName(mapEditor.getEditedMap().getMapName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setTitle("Wybierz miejsce zapisu mapy");
        final File fileToSave = fileChooser.showSaveDialog(mainLoader.getViewLoader().getMainStage());
        this.fileToSave = fileToSave;
        if (fileToSave == null) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", "Nie wybrano lokalizacji!");
            return;
        }

        if (!fileToSave.exists()) {
            try {
                fileToSave.createNewFile();
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        } else {
            final ConfirmationPane controller = mainLoader.getController(ConfirmationPane.class);
            controller.setConfirmationType(ConfirmationType.OVERRIDEFILEMAP);
            controller.getTextLabel().setText("Czy na pewno chcesz nadpisać ten plik?");
            mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();
            return;
        }

        writeFile();
    }

    void writeFile() {
        final List<String> mapLines = mapEditor.getGridPaneToString().convertGridPaneToString();
        try (
                final FileWriter fileWriter = new FileWriter(fileToSave);
                final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)

        ) {
            for (final String mapLine : mapLines) {
                bufferedWriter.write(mapLine);
                bufferedWriter.newLine();
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void importMap() {
        final UserMap editedMap = mapEditor.getEditedMap();
        if (editedMap.isChangesToSave()) {
            final ConfirmationPane controller = mainLoader.getController(ConfirmationPane.class);
            controller.setConfirmationType(ConfirmationType.EXPORTMAP);
            controller.getTextLabel().setText("Masz niezapisane zmiany na aktualnie edytowanej mapie, czy na pewno chcesz załadować nową mapę?");
            mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();
            return;
        }
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setTitle("Wybierz lokaliacje pliku mapy");
        final File chosenFile = fileChooser.showOpenDialog(mainLoader.getViewLoader().getMainStage());
        if (chosenFile == null || chosenFile.isDirectory()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", "Nie wybrano lokalizacji!");
            return;
        }

        final List<String> mapLines;
        try {
            mapLines = Files.readAllLines(chosenFile.toPath());
        } catch (final IOException exception) {
            exception.printStackTrace();
            return;
        }

        if (mapLines.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error",
                    "Wczytanie nie powiodło się",
                    "Plik mapy nie może być pusty!");
            return;
        }

        if (mapLines.size() > 20) {
            mainLoader.getController(DialogController.class).showDialogWindow("error",
                    "Wczytanie nie powiodło się",
                    "Mapa nie może mieć więcej niż 20 rzędów!");
            return;
        }

        for (final String mapLine : mapLines) {
            final char[] lineInChars = mapLine.toCharArray();
            if (lineInChars.length > 30) {
                mainLoader.getController(DialogController.class).showDialogWindow("error",
                        "Wczytanie nie powiodło się",
                        "Mapa nie może mieć więcej niż 30 kolumn!");
                return;
            }
            for (final char character : lineInChars) {
                if (character == '@' || character == '#' || character == '$' || character == '*' || character == ' ' || character == '.') {
                    continue;
                }
                mainLoader.getController(DialogController.class).showDialogWindow("error",
                        "Wczytanie nie powiodło się",
                        "W pliku mapy wykryto niedozwolony znak.");
                return;
            }
        }

        clearMap();
        mapEditor.getConvertStringToGridPane().stringToGridPane(mapLines);
        mapEditor.setEditedMap(new UserMap(FilenameUtils.removeExtension(chosenFile.getName())));


    }

    void clearMap() {
        for (final Node child : mapBox.getChildren()) {
            if (child instanceof Pane) {
                ((Pane) child).getChildren().clear();
            }
        }

    }

    void clearMapWhenLeave() {
        final ViewLoader viewLoader = mainLoader.getViewLoader();
        viewLoader.setWindow(GameModuleChoiceController.class);
        mapBox.setGridLinesVisible(false);
        mapBox.getChildren().clear();
        blockBox.getChildren().clear();
    }

    MapEditor getMapEditor() {
        return mapEditor;
    }

    void setMapEditor(final MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    public GridPane getMapBox() {
        return mapBox;
    }

    public VBox getBlockBox() {
        return blockBox;
    }

    UserMap getChoosenMap() {
        return choosenMap;
    }

    public AnchorPane getEditedAreaPane() {
        return editedAreaPane;
    }

    public Button getChoosenMapButton() {
        return choosenMapButton;
    }
}
