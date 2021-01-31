package pl.crystalek.sokoban.io.file;

import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserException;
import pl.crystalek.sokoban.settings.Settings;
import pl.crystalek.sokoban.statistic.Statistic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private final Map<String, InputStream> imageFileList = new HashMap<>();
    private final Map<String, InputStream> mapFileList = new HashMap<>();
    private final Map<String, File> userMapFileList = new HashMap<>();
    private final Map<String, InputStream> fxmlFileList = new HashMap<>();
    private final FileLoader fileLoader = new FileLoader(this);
    private File programDirectory;
    private File userMapDirectory;
    private File statisticFile;
    private File settingsFile;
    private Statistic statistic;
    private Settings settings;

    public void load() throws LoadResourcesException, LoadUserException, CreateFileException {
        checkFilesExist();
        fileLoader.loadFiles();
    }

    public void save() {

    }

    private void checkFilesExist() throws CreateFileException {
        try {
            final URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
            final File decode = new File(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.toString()));
            final File programDirectory = new File(decode.getParentFile(), "sokoban");
            final File userMapDirectory = new File(programDirectory, "your maps");

            if (!programDirectory.exists()) {
                programDirectory.mkdir();
            }

            if (!userMapDirectory.exists()) {
                userMapDirectory.mkdir();
            }

            final File statisticFile = new File(programDirectory, "statistics.txt");
            final File settingsFile = new File(programDirectory, "settings.txt");


            if (!statisticFile.exists()) {
                statisticFile.createNewFile();
                System.out.println("Plik statystyk został utworzony!");
            }
            if (!settingsFile.exists()) {
                settingsFile.createNewFile();
                System.out.println("Plik konfiguracyjny został utworzony!");
            }


            this.programDirectory = programDirectory;
            this.userMapDirectory = userMapDirectory;
            this.statisticFile = statisticFile;
            this.settingsFile = settingsFile;
        } catch (final IOException exception) {
            throw new CreateFileException("Wystapił błąd podczas próby tworzenia plików konfiguracyjnych", exception);
        }
    }

    public Map<String, File> getUserMapFileList() {
        return userMapFileList;
    }

    public File getUserMapDirectory() {
        return userMapDirectory;
    }

    public File getProgramDirectory() {
        return programDirectory;
    }

    public File getStatisticFile() {
        return statisticFile;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public Map<String, InputStream> getImageFileList() {
        return imageFileList;
    }

    public Map<String, InputStream> getMapFileList() {
        return mapFileList;
    }

    public Map<String, InputStream> getFXMLFileList() {
        return fxmlFileList;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(final Statistic statistic) {
        this.statistic = statistic;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(final Settings settings) {
        this.settings = settings;
    }
}
