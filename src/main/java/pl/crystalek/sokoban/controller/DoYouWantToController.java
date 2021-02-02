package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.io.MainLoader;

public final class DoYouWantToController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private Label textLabel;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void no(final ActionEvent event) {

    }

    @FXML
    void yes(final ActionEvent event) {

    }

}
