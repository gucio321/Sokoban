package pl.crystalek.sokoban.settings;

import java.io.Serializable;

public final class Settings implements Serializable {
    private static final long serialVersionUID = 5179680418474481551L;
    private Sound sound = Sound.ENABLE;
    private Control controlType = Control.WASD;
    private int brightness = 100;
    private Texture textureType = Texture.SET1;

    public Settings() {
    }

    public Settings(final Sound sound, final Control controlType, final int brightness, final Texture textureType) {
        this.sound = sound;
        this.controlType = controlType;
        this.brightness = brightness;
        this.textureType = textureType;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(final Sound sound) {
        this.sound = sound;
    }

    public Control getControlType() {
        return controlType;
    }

    public void setControlType(final Control controlType) {
        this.controlType = controlType;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(final int brightness) {
        this.brightness = brightness;
    }

    public Texture getTextureType() {
        return textureType;
    }

    public void setTextureType(final Texture textureType) {
        this.textureType = textureType;
    }
}
