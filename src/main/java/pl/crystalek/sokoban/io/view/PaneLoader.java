package pl.crystalek.sokoban.io.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PaneLoader {

    public Map<Class<?>, Pane> getPaneList(final List<FXMLLoader> fxmlList) {
        final Map<Class<?>, Pane> resultMap = new HashMap<>();

        for (final FXMLLoader fxmlLoader : fxmlList) {
            resultMap.put(fxmlLoader.getController().getClass(), fxmlLoader.getRoot());
        }

        return resultMap;
    }
}
