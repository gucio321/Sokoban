package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.statistic.Statistic;
import pl.crystalek.sokoban.util.TimeUtil;

public final class ShowStatisticController implements Controller {
    private MainLoader mainLoader;
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

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(SokobanMainController.class);
    }

    @FXML
    private void resetStatistic(final ActionEvent event) {
        final ConfirmationPane controller = mainLoader.getController(ConfirmationPane.class);
        controller.setConfirmationType(ConfirmationType.STATISTIC);
        controller.getTextLabel().setText("Czy na pewno chcesz zresetować statystyki?");
        mainLoader.getViewLoader().getStage(ConfirmationPane.class).show();

    }

    void showStatistic() {
        final Statistic statistic = mainLoader.getFileManager().getStatistic();
        stepsLabel.setText(String.valueOf(statistic.getSteps()));
        winLevelLabel.setText(String.valueOf(statistic.getWinLevels()));
        crateMoveLabel.setText(String.valueOf(statistic.getCrateMove()));
        deletedMapsLabel.setText(String.valueOf(statistic.getDeletedMaps()));
        createdMapsLabel.setText(String.valueOf(statistic.getCreatedMaps()));
    }

    @FXML
    private void showPlayTime(final ActionEvent event) {
        final Statistic statistic = mainLoader.getFileManager().getStatistic();
        final DialogController controller = mainLoader.getController(DialogController.class);
        controller.getHeaderTextLabel().setText("Czas gry:");
        controller.getContentTextLabel().setText(TimeUtil.getDateInString(statistic.getTime(), ", "));
        controller.getInfoImageView().setImage(mainLoader.getImageList().get("info"));
        mainLoader.getViewLoader().getStage(DialogController.class).show();

    }
}
