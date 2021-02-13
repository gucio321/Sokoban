package pl.crystalek.sokoban.io.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.settings.Settings;
import pl.crystalek.sokoban.statistic.Statistic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class FileLoader {
    private final FileManager fileManager;

    FileLoader(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    void loadFiles() throws LoadResourcesException, LoadUserFileException {
        loadResources();
        fileManager.setSettings(loadUserFile(fileManager.getSettingsFile()).map(object -> (Settings) object).orElseGet(Settings::new));
        fileManager.setStatistic(loadUserFile(fileManager.getStatisticFile()).map(object -> (Statistic) object).orElseGet(Statistic::new));
        loadUserMapFiles();
    }

    private void loadResources() throws LoadResourcesException {
        final Map<String, InputStream> mapFileList = fileManager.getMapFileList();
        final Map<String, InputStream> imageFileList = fileManager.getImageFileList();
        final List<InputStream> fxmlFileList = fileManager.getFXMLFileList();

        try (
                final InputStream fileNameStream = getClass().getResourceAsStream("/FileNameList.txt")
        ) {
            final String[] fileNameList = IOUtils.toString(fileNameStream, StandardCharsets.UTF_8).split("\r\n");
            for (final String fileName : fileNameList) {
                final String[] fileNameSplit = FilenameUtils.removeExtension(fileName).split("/");
                final String fileNameWithoutExtension = fileNameSplit[fileNameSplit.length - 1];

                switch (FilenameUtils.getExtension(fileName).toLowerCase()) {
                    case "txt":
                        mapFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/map/" + fileName));
                        break;
                    case "png":
                        imageFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/img/" + fileName));
                        break;
                    case "fxml":
                        fxmlFileList.add(getClass().getResourceAsStream("/fxml/" + fileName));
                        break;
                }
            }

        } catch (final IOException exception) {
            throw new LoadResourcesException("Wystapił błąd podczas ładowania plików gry!", exception);
        }
    }

    private Optional<Object> loadUserFile(final File fileToLoad) throws LoadUserFileException {
        try (
                final FileInputStream fileInputStream = new FileInputStream(fileToLoad);
                final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            final Object readObject = objectInputStream.readObject();
            return readObject instanceof Statistic || readObject instanceof Settings ? Optional.of(readObject) : Optional.empty();

        } catch (final EOFException exception) {
            return Optional.empty();
        } catch (final IOException | ClassNotFoundException exception) {
            throw new LoadUserFileException("Wystapił błąd podczas ładowania pliku: " + fileToLoad.getName(), exception);
        }
    }

    private void loadUserMapFiles() {
        final File userMapDirectory = fileManager.getUserMapDirectory();
        final Map<String, File> userMapFileList = fileManager.getUserMapFileList();

        for (final File fileMap : userMapDirectory.listFiles()) {
            userMapFileList.put(FilenameUtils.removeExtension(fileMap.getName()), fileMap);
        }
    }
}
