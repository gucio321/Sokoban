package pl.crystalek.sokoban.io.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StageLoader {

    public Map<Class<?>, Stage> getStageList(final List<FXMLLoader> fxmlList) {
        final Map<Class<?>, Stage> resultMap = new HashMap<>();

        for (final FXMLLoader fxmlLoader : fxmlList) {
            final Stage stage = new Stage();
            final AnchorPane pane = fxmlLoader.getRoot();
            final Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Sokoban");
            stage.initStyle(StageStyle.UNDECORATED);
            resultMap.put(fxmlLoader.getController().getClass(), stage);
        }

        return resultMap;
    }
}
