package pl.crystalek.sokoban.io.file;

import pl.crystalek.sokoban.exception.SaveUserFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

class FileSaver {
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

    private void saveUserMaps() {


    }
}
