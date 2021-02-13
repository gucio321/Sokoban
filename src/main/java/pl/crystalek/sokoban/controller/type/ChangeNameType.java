package pl.crystalek.sokoban.controller.type;

public enum ChangeNameType {
    MAP("mapy"),
    SAVE("zapisu");

    private final String text;

    ChangeNameType(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
