package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.crystalek.sokoban.controller.type.ChangeNameType;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.model.UserMap;

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
                break;
        }
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
                if (mainLoader.getUserMapManager().getUserMapList().stream().anyMatch(x -> x.getMapName().equalsIgnoreCase(text))) {
                    mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Taka nazwa mapy już istnieje!");
                    return;
                }

                final MapEditorController controller = mainLoader.getController(MapEditorController.class);
                final UserMap editedMap = controller.getMapEditor().getEditedMap();
                editedMap.setOldMapName(editedMap.getMapName());
                editedMap.setMapName(text);
                controller.saveMap();
                editedMap.setModificationDate(LocalDateTime.now());
                nameTextField.setText("");
                mainLoader.getViewLoader().setWindow(MapEditorController.class);
                break;
            case SAVE:
                break;
        }
    }

    Label getTextLabel() {
        return textLabel;
    }

    void setChangeNameType(final ChangeNameType changeNameType) {
        this.changeNameType = changeNameType;
    }
}
