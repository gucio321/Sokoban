package pl.crystalek.sokoban.map.convert;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.MapEditor;
import pl.crystalek.sokoban.map.model.BoxLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ConvertGridPaneToString {
    private final MainLoader mainLoader;
    private final MapEditor mapEditor;

    public ConvertGridPaneToString(final MainLoader mainLoader, final MapEditor mapEditor) {
        this.mainLoader = mainLoader;
        this.mapEditor = mapEditor;
    }

    public List<String> convertGridPaneToString() {
        final List<BoxLocation> boxLocationList = mapEditor.getBoxLocationList();
        final List<String> resultStringList = new LinkedList<>();
        final Map<String, Image> imageList = new HashMap<>(mainLoader.getImageList());
        imageList.remove("error");
        imageList.remove("info");
        imageList.remove("warning");

        for (int i = 0; i < 20; i++) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < 30; j++) {
                final BoxLocation boxLocation = boxLocationList.get(i * 30 + j);
                final ObservableList<Node> boxPaneChildren = boxLocation.getBoxPane().getChildren();
                if (boxPaneChildren.size() == 0) {
                    stringBuilder.append("*");
                    continue;
                }
                final ImageView imageView = (ImageView) boxPaneChildren.get(0);
                final Image image = imageView.getImage();

                if (image.equals(imageList.get("grass"))) {
                    stringBuilder.append(" ");
                } else if (image.equals(imageList.get("bricks"))) {
                    stringBuilder.append("#");
                } else if (image.equals(imageList.get("crate"))) {
                    stringBuilder.append("$");
                } else if (image.equals(imageList.get("bomb"))) {
                    stringBuilder.append(".");
                } else if (image.equals(imageList.get("player"))) {
                    stringBuilder.append("@");
                }
            }
            resultStringList.add(stringBuilder.toString());
        }
        return resultStringList;
    }
}
