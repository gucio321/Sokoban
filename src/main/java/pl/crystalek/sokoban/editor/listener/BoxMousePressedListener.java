package pl.crystalek.sokoban.editor.listener;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

final class BoxMousePressedListener implements EventHandler<MouseEvent> {
    private final BoxListenerManager boxListenerManager;

    BoxMousePressedListener(final BoxListenerManager boxListenerManager) {
        this.boxListenerManager = boxListenerManager;
    }

    @Override
    public void handle(final MouseEvent event) {
        final ImageView boxImageView = (ImageView) event.getTarget();

        if (!boxImageView.equals(boxListenerManager.getMovedImageView())) {
            boxListenerManager.setMovedImageView(boxImageView);
            boxListenerManager.setOrgSceneX(event.getSceneX());
            boxListenerManager.setOrgSceneY(event.getSceneY());
        }
    }
}
