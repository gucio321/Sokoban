package pl.crystalek.sokoban.settings;

public enum Texture {
    SET1("Zestaw 1"),
    SET2("Zestaw 2");

    private final String name;

    Texture(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
