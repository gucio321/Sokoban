package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;

public final class ChooseByDifficultyController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModeChoiceController.class);

    }

    @FXML
    void start(final ActionEvent event) {
        final ViewLoader viewLoader = mainLoader.getViewLoader();
        viewLoader.setWindow(SecondModuleGameWindowController.class);
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
