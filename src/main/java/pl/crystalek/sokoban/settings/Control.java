package pl.crystalek.sokoban.settings;

public enum Control {
    WASD("WASD"),
    ARROWS("Strzałki");

    private final String name;

    Control(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
