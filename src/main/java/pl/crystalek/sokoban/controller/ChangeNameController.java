package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.controller.load.LoadGameController;
import pl.crystalek.sokoban.controller.type.ChangeNameType;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.UserMap;

import java.time.LocalDateTime;

public final class ChangeNameController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label textLabel;
    private ChangeNameType changeNameType;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void cancel(final ActionEvent event) {
        switch (changeNameType) {
            case MAP:
                mainLoader.getViewLoader().setWindow(MapEditorController.class);
                break;
            case SAVE:
                mainLoader.getViewLoader().setWindow(GameController.class);
                break;
        }
        mainLoader.getController(LoadGameController.class).getGame().getTimeCounter().setPause(false);
    }

    @FXML
    private void save(final ActionEvent event) {
        final String text = nameTextField.getText().trim();
        if (text.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Podaj nazwę " + changeNameType.getText());
            return;
        }

        switch (changeNameType) {
            case MAP:
                if (mainLoader.getMapManager().getUserMapList().stream().anyMatch(x -> x.getName().equalsIgnoreCase(text))) {
                    mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Taka nazwa mapy już istnieje!");
                    return;
                }

                final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
                final MapEditor mapEditor = mapEditorController.getMapEditor();
                final UserMap editedMap = mapEditor.getEditedMap();
                editedMap.setOldName(editedMap.getName());
                editedMap.setName(text);
                editedMap.setModificationDate(LocalDateTime.now());
                mapEditorController.getSaveMap().saveMap(mapEditor);
                mainLoader.getViewLoader().setWindow(MapEditorController.class);
                break;
            case SAVE:
                if (mainLoader.getProgressManager().getSaveList().stream().anyMatch(x -> x.getName().equalsIgnoreCase(text))) {
                    mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Taka nazwa zapisu już istnieje!");
                    return;
                }

                final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
                final Game game = loadGameController.getGame();
                final Progress progress = game.getProgress();
                progress.setOldName(progress.getName());
                progress.setName(text);
                progress.setModificationDate(LocalDateTime.now());
                mainLoader.getController(GameController.class).save();
                mainLoader.getViewLoader().setWindow(GameController.class);
                game.getTimeCounter().setPause(false);
                mainLoader.getViewLoader().getMainStage().addEventFilter(KeyEvent.KEY_RELEASED, loadGameController.getResetMapListener());
                break;
        }
        nameTextField.setText("");
    }

    public Label getTextLabel() {
        return textLabel;
    }

    public void setChangeNameType(final ChangeNameType changeNameType) {
        this.changeNameType = changeNameType;
    }
}
