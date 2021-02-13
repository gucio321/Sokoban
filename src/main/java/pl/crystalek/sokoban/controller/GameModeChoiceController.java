package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.controller.type.ExecutionSource;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameModeChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModuleChoiceController.class);
    }

    @FXML
    void ownMap(final ActionEvent event) {
        mainLoader.getController(LoadController.class).setExecutionSource(ExecutionSource.GAMEMODECHOICE);
        mainLoader.getViewLoader().setWindow(LoadController.class);
    }

    @FXML
    void randomLevel(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(ChooseByDifficultyController.class);
    }

    @FXML
    void risingLevel(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(SelectLevelController.class);
    }

}
