package pl.crystalek.sokoban.io.file;

import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.map.model.UserMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public final class FileSaver {
    private final FileManager fileManager;

    FileSaver(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    void saveFiles() throws SaveUserFileException {
        final File programDirectory = fileManager.getProgramDirectory();
        if (!programDirectory.exists()) {
            programDirectory.mkdir();
        }

        saveFile(fileManager.getSettingsFile(), fileManager.getSettings());
        saveFile(fileManager.getStatisticFile(), fileManager.getStatistic());
        saveUserMaps();
    }

    private void saveFile(final File fileToSave, final Object objectToSave) throws SaveUserFileException {
        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(objectToSave);
        } catch (final IOException exception) {
            throw new SaveUserFileException("Wystapił błąd podczas zapisywania pliku: " + fileToSave.getName(), exception);
        }
    }

    private void saveUserMaps() throws SaveUserFileException {
        final List<UserMap> userMapList = fileManager.getMainLoader().getUserMapManager().getUserMapList();

        for (final UserMap userMap : userMapList) {
            saveUserMap(userMap);
        }
    }

    public void saveUserMap(final UserMap userMap) throws SaveUserFileException {
        final Map<String, File> userMapFileList = fileManager.getUserMapFileList();
        final String mapName = userMap.getMapName();

        userMapFileList.putIfAbsent(mapName, new File(fileManager.getUserMapDirectory(), mapName + ".sokoban"));

        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(userMapFileList.get(mapName));
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(userMap);
        } catch (final IOException exception) {
            throw new SaveUserFileException("Wystapił błąd podczas zapisywania mapy: " + mapName, exception);
        }
    }
}
