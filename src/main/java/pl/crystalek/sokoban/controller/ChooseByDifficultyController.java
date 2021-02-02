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

    }

    @FXML
    void easyLevel(final ActionEvent event) {

    }

    @FXML
    void hardLevel(final ActionEvent event) {

    }

    @FXML
    void normalLevel(final ActionEvent event) {

    }

    @FXML
    void start(final ActionEvent event) {

    }

}
