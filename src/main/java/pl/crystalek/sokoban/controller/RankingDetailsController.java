package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.io.MainLoader;

public final class RankingDetailsController implements Controller {
    private MainLoader mainLoader;

    @FXML
    private Label stepsNumberLabel;

    @FXML
    private Label pointsForTimeLabel;

    @FXML
    private Label totalPoints;

    @FXML
    private Label playTimeLabel;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        final RankingController controller = mainLoader.getController(RankingController.class);
        controller.getDetailsButton().setDisable(true);
        mainLoader.getViewLoader().setWindow(RankingController.class);
    }

    public Label getStepsNumberLabel() {
        return stepsNumberLabel;
    }

    public Label getPointsForTimeLabel() {
        return pointsForTimeLabel;
    }

    public Label getTotalPoints() {
        return totalPoints;
    }

    public Label getPlayTimeLabel() {
        return playTimeLabel;
    }
}
