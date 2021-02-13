package pl.crystalek.sokoban.map.listener;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.controller.MapEditorController;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.MapEditor;

import java.util.HashMap;
import java.util.Map;

public final class BoxListenerManager {
    private final MainLoader mainLoader;
    private final EventUtil eventUtil = new EventUtil(this);
    private final MapEditor mapEditor;
    private double orgSceneX;
    private double orgSceneY;
    private Image useImage;
    private Image previousImage;
    private Pane previousImagePane;
    private Pane previousPane;
    private ImageView movedBoxImageView;
    private Map<Image, Image> copyDraggedBlockMap;

    public BoxListenerManager(final MainLoader mainLoader, final MapEditor mapEditor) {
        this.mainLoader = mainLoader;
        this.mapEditor = mapEditor;
    }

    public void setListeners() {
        final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
        final AnchorPane editedArea = mapEditorController.getEditedAreaPane();
        final VBox draggedBlockBox = mapEditorController.getBlockBox();

        final Map<String, Image> draggedBlockMap = new HashMap<>(mainLoader.getImageList());
        draggedBlockMap.remove("error");
        draggedBlockMap.remove("info");
        draggedBlockMap.remove("warning");
        this.copyDraggedBlockMap = eventUtil.getCopyImageList(draggedBlockMap);

        for (final Image boxImage : draggedBlockMap.values()) {
            final ImageView boxImageView = new ImageView(boxImage);
            boxImageView.setOnMousePressed(new BoxMousePressedListener(this));
            draggedBlockBox.getChildren().add(boxImageView);
        }

        editedArea.setOnMousePressed(new BoxPlaceMousePressedListener(this));
        editedArea.setOnMouseMoved(new BoxMouseMoveListener(this));
    }

    public EventUtil getEventUtil() {
        return eventUtil;
    }

    public MainLoader getMainLoader() {
        return mainLoader;
    }

    public MapEditor getMapEditor() {
        return mapEditor;
    }

    public double getOrgSceneX() {
        return orgSceneX;
    }

    public void setOrgSceneX(final double orgSceneX) {
        this.orgSceneX = orgSceneX;
    }

    public double getOrgSceneY() {
        return orgSceneY;
    }

    public void setOrgSceneY(final double orgSceneY) {
        this.orgSceneY = orgSceneY;
    }

    public Image getUseImage() {
        return useImage;
    }

    public void setUseImage(final Image useImage) {
        this.useImage = useImage;
    }

    public Image getPreviousImage() {
        return previousImage;
    }

    public void setPreviousImage(final Image previousImage) {
        this.previousImage = previousImage;
    }

    public Pane getPreviousImagePane() {
        return previousImagePane;
    }

    public void setPreviousImagePane(final Pane previousImagePane) {
        this.previousImagePane = previousImagePane;
    }

    public Pane getPreviousPane() {
        return previousPane;
    }

    public void setPreviousPane(final Pane previousPane) {
        this.previousPane = previousPane;
    }

    public ImageView getMovedBoxImageView() {
        return movedBoxImageView;
    }

    public void setMovedBoxImageView(final ImageView movedBoxImageView) {
        this.movedBoxImageView = movedBoxImageView;
    }

    public Map<Image, Image> getCopyDraggedBlockMap() {
        return copyDraggedBlockMap;
    }
}
