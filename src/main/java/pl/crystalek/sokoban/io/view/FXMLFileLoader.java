package pl.crystalek.sokoban.io.view;

import javafx.fxml.FXMLLoader;
import pl.crystalek.sokoban.io.file.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class FXMLFileLoader {

    public Map<String, FXMLLoader> getFXMLList(final FileManager fileManager) {
        final Map<String, FXMLLoader> resultMap = new HashMap<>();

        for (final Map.Entry<String, InputStream> entry : fileManager.getFXMLFileList().entrySet()) {
            try (
                    final InputStream inputStream = entry.getValue()
            ) {
                final FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.load(inputStream);

                resultMap.put(entry.getKey(), fxmlLoader);
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        }

        return resultMap;
    }
}
