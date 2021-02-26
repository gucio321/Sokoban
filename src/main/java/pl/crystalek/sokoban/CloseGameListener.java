package pl.crystalek.sokoban;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.type.ConfirmationType;
import pl.crystalek.sokoban.io.MainLoader;

final class CloseGameListener implements EventHandler<KeyEvent> {
    private final MainLoader mainLoader;

    CloseGameListener(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final KeyEvent event) {
        if (event.getCode() != KeyCode.ESCAPE) {
            return;
        }

        final ConfirmationController confirmationController = mainLoader.getController(ConfirmationController.class);
        confirmationController.setConfirmationType(ConfirmationType.EXIT);
        confirmationController.getTextLabel().setText("Czy na pewno chcesz zamknąć grę? Wszystkie niezapisane zmiany zostaną utracone");
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }
}
