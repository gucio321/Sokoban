package pl.crystalek.sokoban.map.convert;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.MapEditor;
import pl.crystalek.sokoban.map.model.BoxLocation;

import java.util.List;
import java.util.Map;

public final class ConvertStringToGridPane {
    private final MainLoader mainLoader;
    private final MapEditor mapEditor;

    public ConvertStringToGridPane(final MainLoader mainLoader, final MapEditor mapEditor) {
        this.mainLoader = mainLoader;
        this.mapEditor = mapEditor;
    }

    public void stringToGridPane(final List<String> mapLines) {
        final List<BoxLocation> boxLocationList = mapEditor.getBoxLocationList();
        final Map<String, Image> imageList = mainLoader.getImageList();
        imageList.remove("error");
        imageList.remove("info");
        imageList.remove("warning");

        for (int i = 0; i < mapLines.size(); i++) {
            final String line = mapLines.get(i);
            for (int j = 0; j < 30; j++) {
                final BoxLocation boxLocation = boxLocationList.get(i * 30 + j);
                final ObservableList<Node> boxPaneChildren = boxLocation.getBoxPane().getChildren();
                final ImageView imageView = new ImageView();
                boxPaneChildren.add(imageView);
                switch (line.charAt(j)) {
                    case '#':
                        imageView.setImage(imageList.get("bricks"));
                        break;
                    case '.':
                        imageView.setImage(imageList.get("bomb"));
                        break;
                    case '@':
                        imageView.setImage(imageList.get("player"));
                        break;
                    case '$':
                        imageView.setImage(imageList.get("crate"));
                        break;
                    case ' ':
                        imageView.setImage(imageList.get("grass"));
                        break;
                }
            }
        }
    }

}
