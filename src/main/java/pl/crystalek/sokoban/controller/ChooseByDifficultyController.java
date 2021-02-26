package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class ChooseByDifficultyController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModeChoiceController.class);
        //wlaczanie przycisku od zapisywania
    }

    @FXML
    private void start(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameController.class);
        //wylaczanie przycisku od zapisywania
    }

    @FXML
    private void easyLevel(final ActionEvent event) {
        //ustawianie poziomu trudności
    }

    @FXML
    private void hardLevel(final ActionEvent event) {
        //ustawianie poziomu trudności
    }

    @FXML
    private void normalLevel(final ActionEvent event) {
        //ustawianie poziomu trudności
    }

}
