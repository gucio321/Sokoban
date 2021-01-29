package pl.crystalek.sokoban.io;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class FileLoader {
    private final Map<String, InputStream> imageFileList = new HashMap<>();
    private final Map<String, InputStream> mapFileList = new HashMap<>();
    private final Map<String, InputStream> fxmlFileList = new HashMap<>();

    public void loadFiles() {
        try (
                final InputStream fileNameStream = getClass().getResourceAsStream("/FileNameList.txt")
        ) {
            final String[] fileNameList = IOUtils.toString(fileNameStream, StandardCharsets.UTF_8).split("\r\n");
            for (final String fileName : fileNameList) {
                final String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);

                switch (FilenameUtils.getExtension(fileName).toLowerCase()) {
                    case "txt":
                        mapFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/map/" + fileName));
                        break;
                    case "png":
                        imageFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/img/" + fileName));
                        break;
                    case "fxml":
                        fxmlFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/fxml/" + fileName));
                        break;
                }
            }

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    public Map<String, InputStream> getFXMLFileList() {
        return fxmlFileList;
    }

    public Map<String, InputStream> getImageFileList() {
        return imageFileList;
    }

    public Map<String, InputStream> getMapFileList() {
        return mapFileList;
    }
}
