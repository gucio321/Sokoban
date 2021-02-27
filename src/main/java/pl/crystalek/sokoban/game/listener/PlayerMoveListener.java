package pl.crystalek.sokoban.game.listener;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.Player;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.ranking.Ranking;

import java.time.LocalDateTime;
import java.util.Map;

public final class PlayerMoveListener implements EventHandler<KeyEvent> {
    private final Game game;

    public PlayerMoveListener(final Game game) {
        this.game = game;
    }

    @Override
    public void handle(final KeyEvent event) {
        final int column = game.getPlayer().getColumn();
        final int row = game.getPlayer().getRow();

        switch (event.getCode()) {
            case UP:
                move(column, row - 1, column, row - 2);
                break;
            case DOWN:
                move(column, row + 1, column, row + 2);
                break;
            case RIGHT:
                move(column + 1, row, column + 2, row);
                break;
            case LEFT:
                move(column - 1, row, column - 2, row);
                break;
        }
    }

    public void move(final int columnToGetBox, final int rowToGetBox, final int secondColumnToGetBox, final int secondRowToGetBox) {
        if (columnToGetBox < 0 || rowToGetBox < 0 || columnToGetBox > 29 || rowToGetBox > 19) {
            return;
        }
        final Player player = game.getPlayer();
        final ImageView imageViewOverPlayer = game.getBoxLocationList()[columnToGetBox][rowToGetBox];
        final Image imageOverPlayer = imageViewOverPlayer.getImage();
        final int column = player.getColumn();
        final int row = player.getRow();

        if (imageOverPlayer == null) {
            return;
        }

        final Map<String, Image> imageList = game.getImageList();
        if (imageOverPlayer.equals(imageList.get("bricks"))) {
            return;
        }

        final Image[][] deleteImageList = game.getDeleteImageList();
        final ImageView[][] boxLocationList = game.getBoxLocationList();
        final Progress progress = game.getProgress();
        if (imageOverPlayer.equals(imageList.get("crate"))) {
            if (secondColumnToGetBox < 0 || secondRowToGetBox < 0 || secondColumnToGetBox > 29 || secondRowToGetBox > 19) {
                return;
            }

            final ImageView imageViewOverCrate = boxLocationList[secondColumnToGetBox][secondRowToGetBox];
            final Image imageOverCrate = imageViewOverCrate.getImage();

            if (imageOverCrate == null) {
                return;
            }

            if (!(imageOverCrate.equals(imageList.get("grass")) || imageOverCrate.equals(imageList.get("bomb")) || imageOverCrate.equals(imageList.get("tree")))) {
                return;
            }
            deleteImageList[secondColumnToGetBox][secondRowToGetBox] = imageOverCrate;

            if (imageOverCrate.equals(imageList.get("bomb"))) {
                progress.setSetCrates(progress.getSetCrates() + 1);
            }

            final Image deletImageUnderCrate = deleteImageList[columnToGetBox][rowToGetBox];
            if (deletImageUnderCrate != null) {
                if (deleteImageList[columnToGetBox][rowToGetBox].equals(imageList.get("bomb"))) {
                    progress.setSetCrates(progress.getSetCrates() - 1);
                }
            }

            imageViewOverCrate.setImage(imageList.get("crate"));

            if (progress.getSetCrates() == game.getCrateCount()) {
                game.getTimeCounter().stop();
            }
        } else {
            deleteImageList[columnToGetBox][rowToGetBox] = imageOverPlayer;
        }

        imageViewOverPlayer.setImage(imageList.get("player"));

        final ImageView boxImageView = boxLocationList[column][row];

        if (deleteImageList[column][row] != null) {
            boxImageView.setImage(deleteImageList[column][row]);
            deleteImageList[column][row] = null;
        } else {
            boxImageView.setImage(imageList.get("grass"));
        }

        player.setColumn(columnToGetBox);
        player.setRow(rowToGetBox);
        final Ranking ranking = progress.getRanking();
        ranking.setStepsNumber(ranking.getStepsNumber() + 1);
        progress.setChangesToSave(true);
        progress.setModificationDate(LocalDateTime.now());
    }
}
