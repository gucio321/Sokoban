package pl.crystalek.sokoban.map;

import java.util.ArrayList;
import java.util.List;

public final class MapManager {
    private final List<DefaultMap> defaultMapList = new ArrayList<>();
    private List<UserMap> userMapList = new ArrayList<>();

    public void addMap(final DefaultMap defaultMap) {
        defaultMapList.add(defaultMap);
    }

    public void addMap(final UserMap userMap) {
        userMapList.add(userMap);
    }

    public void deleteMap(final UserMap userMap) {
        userMapList.remove(userMap);
    }

    public List<UserMap> getUserMapList() {
        return userMapList;
    }

    public void setUserMapList(final List<UserMap> userMapList) {
        this.userMapList = userMapList;
    }

    public List<DefaultMap> getDefaultMapList() {
        return defaultMapList;
    }
}
