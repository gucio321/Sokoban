package pl.crystalek.sokoban.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.ranking.Ranking;
import pl.crystalek.sokoban.util.TimeUtil;

import java.util.List;

public final class RankingController implements Controller {
    private MainLoader mainLoader;
    private Ranking chosenRanking;
    @FXML
    private VBox boxList;
    @FXML
    private Button detailsButton;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(SokobanMainController.class);
    }

    @FXML
    private void showDetails(final ActionEvent event) {
        final RankingDetailsController rankingDetailsController = mainLoader.getController(RankingDetailsController.class);
        rankingDetailsController.getPlayTimeLabel().setText(TimeUtil.getDateInString(chosenRanking.getPlayTime() * 1_000L, ", ", true));
        rankingDetailsController.getPointsForTimeLabel().setText(String.valueOf(chosenRanking.getPointsForTime()));
        rankingDetailsController.getStepsNumberLabel().setText(String.valueOf(chosenRanking.getStepsNumber()));
        rankingDetailsController.getTotalPoints().setText(String.valueOf(chosenRanking.getTotalPoints()));
        mainLoader.getViewLoader().setWindow(RankingDetailsController.class);
    }

    void showRanking() {
        final ObservableList<Node> children = boxList.getChildren();
        children.clear();
        final List<Ranking> rankingList = mainLoader.getRankingManager().getRankingList();
        rankingList.sort(Ranking::compareTo);

        for (final Ranking ranking : rankingList) {
            final Button button = new Button();
            button.setOnAction(event -> {
                this.chosenRanking = ranking;
                detailsButton.setDisable(false);
            });
            button.setMaxWidth(149);
            button.setMaxHeight(27);
            button.setAlignment(Pos.CENTER);
            button.setTextFill(Paint.valueOf("#a10000"));
            button.setStyle("-fx-background-color: #a0a0a0; -fx-border-color: #aaaaaa");
            button.setFont(new Font("System Bold Italic", 12));
            button.setText(ranking.getMapName() + " - " + ranking.getTotalPoints());
            children.add(button);
        }
    }

    public Ranking getChosenRanking() {
        return chosenRanking;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }
}
