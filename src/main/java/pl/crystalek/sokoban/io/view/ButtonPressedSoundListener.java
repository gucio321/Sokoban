package pl.crystalek.sokoban.io.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pl.crystalek.sokoban.io.MainLoader;

import javax.sound.sampled.Clip;

final class ButtonPressedSoundListener implements EventHandler<MouseEvent> {
    private final MainLoader mainLoader;

    ButtonPressedSoundListener(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final MouseEvent event) {
        final Clip click = mainLoader.getSoundList().get("buttonClick");
        click.setFramePosition(0);
        click.start();
    }
}
