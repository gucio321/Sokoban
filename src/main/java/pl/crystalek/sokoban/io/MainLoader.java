package pl.crystalek.sokoban.io;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.io.view.FXMLFileLoader;
import pl.crystalek.sokoban.io.view.ImageLoader;
import pl.crystalek.sokoban.io.view.StageLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MainLoader {
    private final Map<Class<?>, Controller> controllerMap = new HashMap<>();
    private Map<Class<?>, Stage> stageMap;
    private List<FXMLLoader> fxmlList;
    private Map<String, List<String>> stringMapList;
    private Map<String, List<String>> userStringMapList;
    private Map<String, Image> imageList;

    public void load() throws LoadUserFileException, LoadResourcesException, CreateFileException, IOException {
        final MapLoader mapLoader = new MapLoader();
        final FileManager fileManager = new FileManager();
        fileManager.load();
        this.stringMapList = mapLoader.getMapsInString(fileManager.getMapFileList());
        this.userStringMapList = mapLoader.getMapsInString(fileManager.getUserMapFileList());
        this.imageList = new ImageLoader().getImageList(fileManager);
        this.fxmlList = new FXMLFileLoader().getFXMLList(fileManager);
        this.stageMap = new StageLoader().getStageList(this);
        setControllerMap();
    }

    private void setControllerMap() {
        for (final FXMLLoader fxmlLoader : fxmlList) {
            final Controller controller = fxmlLoader.getController();
            controllerMap.put(controller.getClass(), controller);
        }
    }

    public Stage getStage(final Class<?> controllerClass) {
        return stageMap.get(controllerClass);
    }

    public <T extends Controller> T getController(final Class<T> controllerClass) {
        return (T) controllerMap.get(controllerClass);
    }

    public List<FXMLLoader> getFXMLList() {
        return fxmlList;
    }

    public Map<String, List<String>> getUserStringMapList() {
        return userStringMapList;
    }

    public Map<String, List<String>> getStringMapList() {
        return stringMapList;
    }

    public Map<String, Image> getImageList() {
        return imageList;
    }
}
