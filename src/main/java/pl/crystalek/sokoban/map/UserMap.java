package pl.crystalek.sokoban.map;

import pl.crystalek.sokoban.controller.ObjectName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class UserMap implements ObjectName, Serializable {
    private static final long serialVersionUID = 6981674798801078963L;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime modificationDate = LocalDateTime.now();
    private List<String> mapLines;
    private String name;
    private transient boolean changesToSave = false;
    private transient String oldName;

    public UserMap() {
    }

    public UserMap(final String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(final String oldName) {
        this.oldName = oldName;
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

    @Override
    public List<String> getMapLines() {
        return mapLines;
    }

    public void setMapLines(final List<String> mapLines) {
        this.mapLines = mapLines;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
