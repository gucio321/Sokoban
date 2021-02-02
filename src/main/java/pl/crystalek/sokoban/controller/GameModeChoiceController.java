package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameModeChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        mainLoader.getStage(GameModuleChoiceController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void ownMap(final ActionEvent event) {
        mainLoader.getStage(LoadController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void randomLevel(final ActionEvent event) {
        mainLoader.getStage(ChooseByDifficultyController.class).show();
        mainLoader.getStage(getClass()).close();
    }

    @FXML
    void risingLevel(final ActionEvent event) {
        mainLoader.getStage(SelectLevelController.class).show();
        mainLoader.getStage(getClass()).close();
    }

}
