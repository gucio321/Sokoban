package pl.crystalek.sokoban;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.crystalek.sokoban.controller.SokobanMainController;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.MainLoader;

import java.io.IOException;

public final class Sokoban extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final MainLoader mainLoader = new MainLoader();
        try {
            mainLoader.load();
        } catch (final LoadUserFileException | LoadResourcesException | CreateFileException | IOException exception) {
            exception.printStackTrace();
            return;
        }
        final SokobanMainController controller = mainLoader.getController(SokobanMainController.class);

        final Stage sokobanMainStage = mainLoader.getStage(SokobanMainController.class);
        sokobanMainStage.setTitle("siema");
        sokobanMainStage.show();
    }

    private void setManagers(final MainLoader mainLoader) {

    }
}
