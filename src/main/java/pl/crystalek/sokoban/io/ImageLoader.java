package pl.crystalek.sokoban.io;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

final class ImageLoader {

    Map<String, Image> getImageList(final FileLoader fileLoader) {
        final Map<String, Image> resultMap = new HashMap<>();

        for (final Map.Entry<String, InputStream> entry : fileLoader.getImageFileList().entrySet()) {
            try (
                    final InputStream inputStream = entry.getValue();
            ) {
                final Image picture = new Image(inputStream);

                resultMap.put(entry.getKey(), picture);
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        }

        return resultMap;
    }
}
