package pl.crystalek.sokoban.io;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.io.view.FXMLFileLoader;
import pl.crystalek.sokoban.io.view.ImageLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;
import pl.crystalek.sokoban.map.UserMapManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MainLoader {
    private final Map<Class<?>, Controller> controllerMap = new HashMap<>();
    private final FileManager fileManager = new FileManager(this);
    private final ViewLoader viewLoader;
    private Map<String, List<String>> stringMapList;
    private UserMapManager userMapManager;
    private Map<String, Image> imageList;

    public MainLoader(final ViewLoader viewLoader) {
        this.viewLoader = viewLoader;
    }

    public void load() throws LoadUserFileException, LoadResourcesException, CreateFileException, IOException {
        final MapLoader mapLoader = new MapLoader();
        fileManager.load();
        this.stringMapList = mapLoader.getMapsInString(fileManager.getMapFileList());
        this.userMapManager = mapLoader.getUserMaps(fileManager.getUserMapFileList());
        this.imageList = new ImageLoader().getImageList(fileManager);
        final List<FXMLLoader> fxmlList = new FXMLFileLoader().getFXMLList(fileManager);
        setMapController(fxmlList);
        viewLoader.viewLoad(fxmlList);
    }

    private void setMapController(final List<FXMLLoader> fxmlList) {
        for (final FXMLLoader fxmlLoader : fxmlList) {
            final Controller controller = fxmlLoader.getController();
            controller.setManagers(this);
            controllerMap.put(controller.getClass(), controller);
        }
    }

    public <T extends Controller> T getController(final Class<T> controllerClass) {
        return (T) controllerMap.get(controllerClass);
    }

    public UserMapManager getUserMapManager() {
        return userMapManager;
    }

    public Map<String, List<String>> getStringMapList() {
        return stringMapList;
    }

    public Map<String, Image> getImageList() {
        return imageList;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ViewLoader getViewLoader() {
        return viewLoader;
    }
}
