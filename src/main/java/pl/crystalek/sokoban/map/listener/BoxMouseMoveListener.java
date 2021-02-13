package pl.crystalek.sokoban.map.listener;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.crystalek.sokoban.map.model.BoxLocation;

import java.util.Optional;

final class BoxMouseMoveListener implements EventHandler<MouseEvent> {
    private final BoxListenerManager boxListenerManager;

    BoxMouseMoveListener(final BoxListenerManager boxListenerManager) {
        this.boxListenerManager = boxListenerManager;
    }

    @Override
    public void handle(final MouseEvent event) {
        final ImageView movedBoxImageView = boxListenerManager.getMovedBoxImageView();
        if (movedBoxImageView == null) {
            return;
        }

        if (event.getSceneX() < 160 || event.getSceneX() > 1440 || event.getSceneY() > 800 || event.getSceneY() < 20) {
            boxListenerManager.getEventUtil().goOutMapBorder();
            return;
        }

        movedBoxImageView.setTranslateX(event.getSceneX() - boxListenerManager.getOrgSceneX());
        movedBoxImageView.setTranslateY(event.getSceneY() - boxListenerManager.getOrgSceneY());

        final Optional<BoxLocation> closestPaneOptional = boxListenerManager.getEventUtil().getSmallestDistance(event.getSceneX(), event.getSceneY());
        if (!closestPaneOptional.isPresent()) {
            boxListenerManager.getEventUtil().removeLastImage();
            return;
        }

        final Pane closestPane = closestPaneOptional.get().getBoxPane();
        final ObservableList<Node> closestPaneChildren = closestPane.getChildren();
        final Image draggedImage = boxListenerManager.getCopyDraggedBlockMap().get(boxListenerManager.getUseImage());

        final Image previousImage = boxListenerManager.getPreviousImage();
        final Pane previousImagePane = boxListenerManager.getPreviousImagePane();
        if (previousImage != null) {
            if (!closestPane.equals(previousImagePane)) {
                if (previousImagePane.getChildren().size() != 0) {
                    final ImageView child = (ImageView) previousImagePane.getChildren().get(0);
                    child.setImage(previousImage);
                    boxListenerManager.setPreviousImage(null);
                    boxListenerManager.setPreviousImagePane(null);

                }
            }
        }

        final ImageView imageView;
        if (closestPaneChildren.size() != 0) {
            imageView = (ImageView) closestPaneChildren.get(0);
        } else {
            imageView = new ImageView();
            closestPaneChildren.add(imageView);
        }

        final Image image = imageView.getImage();
        if (image != null) {
            if (!image.equals(draggedImage)) {
                boxListenerManager.setPreviousImage(image);
                boxListenerManager.setPreviousImagePane(closestPane);
            }
        }

        imageView.setImage(draggedImage);

        final Pane previousPane = boxListenerManager.getPreviousPane();
        if (previousPane != null) {
            final ObservableList<Node> previousPaneChildren = previousPane.getChildren();
            if (previousPaneChildren.size() != 0) {
                final ImageView child = (ImageView) previousPaneChildren.get(0);
                if (draggedImage.equals(child.getImage())) {
                    if (!previousPane.equals(closestPane)) {
                        previousPaneChildren.clear();
                    }
                }
            }
        }

        boxListenerManager.setPreviousPane(closestPane);
    }
}


