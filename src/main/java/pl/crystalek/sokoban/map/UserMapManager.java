package pl.crystalek.sokoban.map;

import pl.crystalek.sokoban.map.model.UserMap;

import java.util.ArrayList;
import java.util.List;

public final class UserMapManager {
    private final List<UserMap> userMapList = new ArrayList<>();

    public void addMap(final UserMap userMap) {
        userMapList.add(userMap);
    }

    public void deleteMap(final UserMap userMap) {
        userMapList.remove(userMap);
    }

    public List<UserMap> getUserMapList() {
        return userMapList;
    }
}
