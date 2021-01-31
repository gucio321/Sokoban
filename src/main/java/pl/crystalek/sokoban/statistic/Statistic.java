package pl.crystalek.sokoban.statistic;

public final class Statistic {
    private long time = 0;
    private int steps = 0;
    private int winLevels = 0;
    private int crateMove = 0;
    private int deletedMaps = 0;
    private int createdMaps = 0;

    public Statistic() {
    }

    public Statistic(final long time, final int steps, final int winLevels, final int crateMove, final int deletedMaps, final int createdMaps) {
        this.time = time;
        this.steps = steps;
        this.winLevels = winLevels;
        this.crateMove = crateMove;
        this.deletedMaps = deletedMaps;
        this.createdMaps = createdMaps;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(final int steps) {
        this.steps = steps;
    }

    public int getWinLevels() {
        return winLevels;
    }

    public void setWinLevels(final int winLevels) {
        this.winLevels = winLevels;
    }

    public int getCrateMove() {
        return crateMove;
    }

    public void setCrateMove(final int crateMove) {
        this.crateMove = crateMove;
    }

    public int getDeletedMaps() {
        return deletedMaps;
    }

    public void setDeletedMaps(final int deletedMaps) {
        this.deletedMaps = deletedMaps;
    }

    public int getCreatedMaps() {
        return createdMaps;
    }

    public void setCreatedMaps(final int createdMaps) {
        this.createdMaps = createdMaps;
    }

}
