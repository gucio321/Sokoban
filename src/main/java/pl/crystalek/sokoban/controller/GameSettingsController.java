package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.settings.Control;
import pl.crystalek.sokoban.settings.Settings;
import pl.crystalek.sokoban.settings.Sound;
import pl.crystalek.sokoban.settings.Texture;

public final class GameSettingsController implements Controller {
    private MainLoader mainLoader;

    @FXML
    private Button soundButton;

    @FXML
    private Button controlButton;

    @FXML
    private Button textureButton;

    //TODO ZARZADZANIE JASNOSCIA EKRANU

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(SokobanMainController.class);
    }

    @FXML
    private void brightnessMinus(final ActionEvent event) {

    }

    @FXML
    private void brightnessPlus(final ActionEvent event) {

    }

    @FXML
    private void control(final ActionEvent event) {
        final Settings settings = mainLoader.getSettings();
        settings.setControlType(settings.getControlType() == Control.WASD ? Control.ARROWS : Control.WASD);
        controlButton.setText(settings.getControlType().getName());
    }

    @FXML
    private void soundButton(final ActionEvent event) {
        final Settings settings = mainLoader.getSettings();
        settings.setSound(settings.getSound() == Sound.ENABLE ? Sound.DISABLE : Sound.ENABLE);
        soundButton.setText(settings.getSound().getName());
    }

    @FXML
    private void texture(final ActionEvent event) {
        final Settings settings = mainLoader.getSettings();
        settings.setTextureType(settings.getTextureType() == Texture.SET1 ? Texture.SET2 : Texture.SET1);
        textureButton.setText(settings.getTextureType().getName());
    }

    void updateSettings() {
        final Settings settings = mainLoader.getSettings();
        controlButton.setText(settings.getControlType().getName());
        soundButton.setText(settings.getSound().getName());
        textureButton.setText(settings.getTextureType().getName());
    }
}
