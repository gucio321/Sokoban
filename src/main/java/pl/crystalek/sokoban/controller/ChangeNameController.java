package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.crystalek.sokoban.io.MainLoader;

public final class ChangeNameController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private TextField nameTextField;

    @FXML
    private Label setName;

    @FXML
    void cancel(final ActionEvent event) {

    }

    @FXML
    void save(final ActionEvent event) {

    }
}
