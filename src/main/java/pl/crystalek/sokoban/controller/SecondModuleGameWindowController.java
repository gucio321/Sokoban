package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;

public final class SecondModuleGameWindowController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private GridPane mapBox;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    void back(final ActionEvent event) {
        final ViewLoader viewLoader = mainLoader.getViewLoader();
//        viewLoader.getStage(ConfirmationPane.class).show(); //i tam wlaczanie okna selectLevel lub chooseByDifficulty
        viewLoader.getMainStage().setWidth(1280);
        viewLoader.getMainStage().setHeight(720);
    }

    @FXML
    void saveGame(final ActionEvent event) {
        //no i tu jakieś cuda xD
    }

}
