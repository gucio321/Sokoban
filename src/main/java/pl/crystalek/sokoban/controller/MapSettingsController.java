package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.UserMap;

import java.time.LocalDateTime;

public final class MapSettingsController implements Controller {
    private MainLoader mainLoader;

    @FXML
    private TextField playTimeTextField;

    @FXML
    private TextField bonusForTimeTextField;

    @FXML
    private TextField mapNameTextField;

    @FXML
    private CheckBox closeGameCheckBox;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        playTimeTextField.setText("");
        bonusForTimeTextField.setText("");
        mapNameTextField.setText("");
        closeGameCheckBox.setSelected(false);
        mainLoader.getViewLoader().setWindow(MapEditorController.class);
    }

    @FXML
    private void saveSettings(final ActionEvent event) {
        final String playTimeText = playTimeTextField.getText().trim();
        final String bonusForTimeText = bonusForTimeTextField.getText().trim();
        final String mapNameText = mapNameTextField.getText().trim();
        final UserMap editedMap = mainLoader.getController(MapEditorController.class).getMapEditor().getEditedMap();

        if (playTimeText.isEmpty() || bonusForTimeText.isEmpty() || mapNameText.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Pola nie mogą być puste!");
            return;
        }

        if (!mapNameText.equals(editedMap.getName())) {
            if (mainLoader.getMapManager().getUserMapList().stream().anyMatch(x -> x.getName().equalsIgnoreCase(mapNameText))) {
                mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Taka nazwa mapy już istnieje!");
                return;
            }
        }

        final int playTimeNumber;
        final int bonusForTimeNumber;
        try {
            playTimeNumber = Integer.parseInt(playTimeText);
            bonusForTimeNumber = Integer.parseInt(bonusForTimeText);
            if (playTimeNumber < 0 || bonusForTimeNumber < 0) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException exception) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", "Błąd!", "Pola muszą być liczbą całkowitą z zakresu 0 - " + Integer.MAX_VALUE);
            return;
        }

        editedMap.setTimeInSeconds(playTimeNumber);
        editedMap.setBonus(bonusForTimeNumber);
        if (editedMap.getOldName() == null) {
            editedMap.setOldName(editedMap.getName());
        }
        editedMap.setName(mapNameText);
        editedMap.setCloseGameWhenTimeEnd(closeGameCheckBox.isSelected());
        editedMap.setModificationDate(LocalDateTime.now());
        editedMap.setChangesToSave(true);
        mainLoader.getController(DialogController.class).showDialogWindow("info", "Informacja", "Zmiany zostały zapisane!");
    }

    public TextField getPlayTimeTextField() {
        return playTimeTextField;
    }

    public TextField getBonusForTimeTextField() {
        return bonusForTimeTextField;
    }

    public TextField getMapNameTextField() {
        return mapNameTextField;
    }

    public CheckBox getCloseGameCheckBox() {
        return closeGameCheckBox;
    }
}
