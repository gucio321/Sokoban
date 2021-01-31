package pl.crystalek.sokoban.settings;

public enum Sound {
    ENABLE("Włączony"),
    DISABLE("Wyłączony");

    private final String text;

    Sound(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
