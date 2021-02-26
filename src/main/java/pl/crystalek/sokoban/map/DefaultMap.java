package pl.crystalek.sokoban.map;

import pl.crystalek.sokoban.controller.ObjectName;

import java.util.List;

public final class DefaultMap implements ObjectName, Comparable<DefaultMap> {
    private final String name;
    private final int priority;
    private final List<String> mapLines;
    private final int defaultPointNumber;
    private final int bonus;
    private final int timeInSeconds;

    public DefaultMap(final String name, final int priority, final List<String> mapLines, final int defaultPointNumber, final int bonus, final int timeInSeconds) {
        this.name = name;
        this.priority = priority;
        this.mapLines = mapLines;
        this.defaultPointNumber = defaultPointNumber;
        this.bonus = bonus;
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public List<String> getMapLines() {
        return mapLines;
    }

    public int getDefaultPointNumber() {
        return defaultPointNumber;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public int compareTo(final DefaultMap defaultMap) {
        return -Integer.compare(priority, defaultMap.getPriority());
    }
}
