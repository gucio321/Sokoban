package pl.crystalek.sokoban.map.listener;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

final class BoxMousePressedListener implements EventHandler<MouseEvent> {
    private final BoxListenerManager boxListenerManager;

    BoxMousePressedListener(final BoxListenerManager boxListenerManager) {
        this.boxListenerManager = boxListenerManager;
    }

    @Override
    public void handle(final MouseEvent event) {
        final EventTarget target = event.getTarget();
        final ImageView boxImageView = (ImageView) target;
        final Image boxImage = boxImageView.getImage();

        if (boxListenerManager.getUseImage() == null || !boxListenerManager.getUseImage().equals(boxImage)) {
            boxListenerManager.setMovedBoxImageView(boxImageView);
            boxListenerManager.setUseImage(boxImage);
            boxListenerManager.setOrgSceneX(event.getSceneX());
            boxListenerManager.setOrgSceneY(event.getSceneY());
        }
    }
}
