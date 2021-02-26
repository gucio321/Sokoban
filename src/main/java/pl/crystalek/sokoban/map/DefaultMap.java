package pl.crystalek.sokoban.map;

import java.io.Serializable;
import java.util.List;

public class DefaultMap implements Comparable<DefaultMap>, Serializable {
    private static final long serialVersionUID = 8602912027125034534L;
    private String name;
    private transient int priority;
    private List<String> mapLines;
    private int defaultPointNumber;
    private int bonus;
    private int timeInSeconds;

    public DefaultMap() {
    }

    public DefaultMap(final String name) {
        this.name = name;
    }

    public DefaultMap(final String name, final int defaultPointNumber, final int bonus, final int timeInSeconds) {
        this.name = name;
        this.defaultPointNumber = defaultPointNumber;
        this.bonus = bonus;
        this.timeInSeconds = timeInSeconds;
    }

    public DefaultMap(final String name, final int priority, final List<String> mapLines, final int defaultPointNumber, final int bonus, final int timeInSeconds) {
        this.name = name;
        this.priority = priority;
        this.mapLines = mapLines;
        this.defaultPointNumber = defaultPointNumber;
        this.bonus = bonus;
        this.timeInSeconds = timeInSeconds;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public List<String> getMapLines() {
        return mapLines;
    }

    public void setMapLines(final List<String> mapLines) {
        this.mapLines = mapLines;
    }

    public int getDefaultPointNumber() {
        return defaultPointNumber;
    }

    public void setDefaultPointNumber(final int defaultPointNumber) {
        this.defaultPointNumber = defaultPointNumber;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(final int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(final int bonus) {
        this.bonus = bonus;
    }

    @Override
    public int compareTo(final DefaultMap defaultMap) {
        return -Integer.compare(priority, defaultMap.getPriority());
    }
}
