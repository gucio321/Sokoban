package pl.crystalek.sokoban.controller.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.ConfirmationType;
import pl.crystalek.sokoban.controller.DialogController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.UserMap;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public final class ImportMap implements EventHandler<ActionEvent> {
    private final MainLoader mainLoader;

    public ImportMap(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final ActionEvent event) {
        if (mainLoader.getController(MapEditorController.class).getMapEditor().getEditedMap().isChangesToSave()) {
            final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
            controller.setConfirmationType(ConfirmationType.IMPORTMAP);
            controller.getTextLabel().setText("Masz niezapisane zmiany na aktualnie edytowanej mapie, czy na pewno chcesz załadować nową mapę?");
            mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
            return;
        }

        showFileChooser();
    }

    public void showFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setTitle("Wybierz lokaliacje pliku mapy");

        checkFile(fileChooser.showOpenDialog(mainLoader.getViewLoader().getMainStage()));

    }

    public boolean checkFile(final File chosenFile) {
        if (chosenFile == null || chosenFile.isDirectory()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd", "Nie wybrano lokalizacji!");
            return false;
        }

        final List<String> mapLines;
        try {
            mapLines = Files.readAllLines(chosenFile.toPath());
        } catch (final IOException exception) {
            exception.printStackTrace();
            return false;
        }

        if (mapLines.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error",
                    "Wczytanie nie powiodło się",
                    "Plik mapy nie może być pusty!");
            return false;
        }

        if (mapLines.size() > 20) {
            mainLoader.getController(DialogController.class).showDialogWindow("error",
                    "Wczytanie nie powiodło się",
                    "Mapa nie może mieć więcej niż 20 rzędów!");
            return false;
        }

        for (final String mapLine : mapLines) {
            final char[] lineInChars = mapLine.toCharArray();
            if (lineInChars.length > 30) {
                mainLoader.getController(DialogController.class).showDialogWindow("error",
                        "Wczytanie nie powiodło się",
                        "Mapa nie może mieć więcej niż 30 kolumn!");
                return false;
            }
            for (final char character : lineInChars) {
                if (character == '@' || character == '#' || character == '$' || character == '*' || character == ' ' || character == '.' || character == '&') {
                    continue;
                }
                mainLoader.getController(DialogController.class).showDialogWindow("error",
                        "Wczytanie nie powiodło się",
                        "W pliku mapy wykryto niedozwolony znak.");
                return false;
            }
        }

        final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
        final MapEditor mapEditor = mapEditorController.getMapEditor();

        mapEditorController.clearMap();
        mapEditor.getConvertStringToGridPane().stringToGridPane(mapLines);
        mapEditor.setEditedMap(new UserMap(FilenameUtils.removeExtension(chosenFile.getName())));
        return true;
    }
}
