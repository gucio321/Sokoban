package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public final class ShowStatisticController implements Controller {

    @FXML
    private Label timeLabel;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label winLevelLabel;

    @FXML
    private Label crateMoveLabel;

    @FXML
    private Label deletedMapsLabel;

    @FXML
    private Label createdMapsLabel;

    @FXML
    void back(final ActionEvent event) {

    }

    @FXML
    void resetRanking(final ActionEvent event) {

    }

}
