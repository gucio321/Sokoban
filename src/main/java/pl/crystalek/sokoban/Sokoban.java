package pl.crystalek.sokoban;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.crystalek.sokoban.controller.SokobanMainController;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileManager;

import java.io.IOException;

public final class Sokoban extends Application {
    private MainLoader mainLoader;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final FileManager fileManager = new FileManager();
        final MainLoader mainLoader = new MainLoader(fileManager);
        try {
            mainLoader.load();
        } catch (final LoadUserFileException | LoadResourcesException | CreateFileException | IOException exception) {
            exception.printStackTrace();
            return;
        }
        this.mainLoader = mainLoader;

        final Stage sokobanMainStage = mainLoader.getStage(SokobanMainController.class);
        sokobanMainStage.setTitle("siema");
        sokobanMainStage.show();
    }

    @Override
    public void stop() {
        try {
            mainLoader.save();
        } catch (final SaveUserFileException exception) {
            exception.printStackTrace();
        }
    }
}
