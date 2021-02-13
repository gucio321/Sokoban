package pl.crystalek.sokoban.map.model;

import javafx.scene.layout.Pane;

public final class BoxLocation {
    private final int x;
    private final int y;
    private final int column;
    private final int row;
    private final Pane boxPane;

    public BoxLocation(final int x, final int y, final int column, final int row, final Pane boxPane) {
        this.x = x;
        this.y = y;
        this.column = column;
        this.row = row;
        this.boxPane = boxPane;
    }

    public Pane getBoxPane() {
        return boxPane;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "BoxLocation{" +
                "column=" + column +
                ", row=" + row +
                ", boxPane=" + boxPane +
                '}' + "\n";
    }
}
