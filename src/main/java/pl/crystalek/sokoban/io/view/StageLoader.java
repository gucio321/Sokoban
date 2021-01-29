package pl.crystalek.sokoban.io.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.Map;

public final class StageLoader {

    public Map<String, Stage> getStageList(final MainLoader mainLoader) {
        final Map<String, Stage> resultMap = new HashMap<>();

        for (final Map.Entry<String, FXMLLoader> entry : mainLoader.getFxmlList().entrySet()) {
            final Stage stage = new Stage();
            final AnchorPane pane = entry.getValue().getRoot();
            final Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Sokoban");
            resultMap.put(entry.getKey().replace("Pane", "Stage"), stage);
        }

        return resultMap;
    }
}
