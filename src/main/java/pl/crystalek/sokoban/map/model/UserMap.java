package pl.crystalek.sokoban.map.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public final class UserMap implements Serializable {
    private static final long serialVersionUID = 6981674798801078963L;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime modificationDate = LocalDateTime.now();
    private List<String> mapLines;
    private String mapName;
    private transient boolean changesToSave = false;
    private transient String oldMapName;

    public UserMap() {
    }

    public UserMap(final String mapName) {
        this.mapName = mapName;
    }

    public String getOldMapName() {
        return oldMapName;
    }

    public void setOldMapName(final String oldMapName) {
        this.oldMapName = oldMapName;
    }

    public boolean isChangesToSave() {
        return changesToSave;
    }

    public void setChangesToSave(final boolean changesToSave) {
        this.changesToSave = changesToSave;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(final LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public List<String> getMapLines() {
        return mapLines;
    }

    public void setMapLines(final List<String> mapLines) {
        this.mapLines = mapLines;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(final String mapName) {
        this.mapName = mapName;
    }
}
