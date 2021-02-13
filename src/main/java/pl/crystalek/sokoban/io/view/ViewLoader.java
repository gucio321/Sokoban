package pl.crystalek.sokoban.io.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.crystalek.sokoban.controller.ConfirmationPane;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.DialogController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ViewLoader {
    private final Map<Class<?>, Stage> stageMap = new HashMap<>();
    private final Stage mainStage;
    private Map<Class<?>, Pane> paneMap;

    public ViewLoader(final Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void viewLoad(final List<FXMLLoader> fxmlList) {
        this.paneMap = new PaneLoader().getPaneList(fxmlList);
        setStageList();
    }

    private void setStageList() {

        for (final Class controllerClass : new Class[]{ConfirmationPane.class, DialogController.class}) {
            final Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(paneMap.get(controllerClass)));
            stageMap.put(controllerClass, stage);
        }
    }

    public Stage getStage(final Class<?> controllerClass) {
        return stageMap.get(controllerClass);
    }

    public void setWindow(final Class<?> controllerClass) {
        mainStage.getScene().setRoot(paneMap.get(controllerClass));
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public Pane getPane(final Class<? extends Controller> controllerClass) {
        return paneMap.get(controllerClass);
    }
}
