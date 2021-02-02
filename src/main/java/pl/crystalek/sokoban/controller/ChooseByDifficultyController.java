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
    void back(final ActionEvent event) {
        mainLoader.getStage(GameModeChoiceController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void start(final ActionEvent event) {
        mainLoader.getStage(SecondModuleGameWindowController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void easyLevel(final ActionEvent event) {
        //ustawianie poziomu trudności
    }

    @FXML
    void hardLevel(final ActionEvent event) {
        //ustawianie poziomu trudności
    }

    @FXML
    void normalLevel(final ActionEvent event) {
        //ustawianie poziomu trudności
    }

}
