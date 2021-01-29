package pl.crystalek.sokoban.io;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.crystalek.sokoban.io.view.FXMLFileLoader;
import pl.crystalek.sokoban.io.view.StageLoader;

import java.util.List;
import java.util.Map;

public final class MainLoader {
    private final Map<String, List<String>> stringMapList;
    private final Map<String, Image> imageList;
    private final Map<String, Stage> stageList;
    private final Map<String, FXMLLoader> fxmlList;

    public MainLoader() {
        final FileLoader fileLoader = new FileLoader();
        fileLoader.loadFiles();
        this.stringMapList = new MapLoader().getMapsInString(fileLoader);
        this.imageList = new ImageLoader().getImageList(fileLoader);
        this.fxmlList = new FXMLFileLoader().getFXMLList(fileLoader);
        this.stageList = new StageLoader().getStageList(this);
    }

    public Map<String, FXMLLoader> getFxmlList() {
        return fxmlList;
    }

    public Map<String, Stage> getStageList() {
        return stageList;
    }

    public Map<String, List<String>> getStringMapList() {
        return stringMapList;
    }

    public Map<String, Image> getImageList() {
        return imageList;
    }
}
