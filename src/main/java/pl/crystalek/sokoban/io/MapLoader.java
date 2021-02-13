package pl.crystalek.sokoban.io;

import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.map.UserMapManager;
import pl.crystalek.sokoban.map.model.UserMap;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class MapLoader {

    Map<String, List<String>> getMapsInString(final Map<String, InputStream> mapFileList) throws IOException {
        final Map<String, List<String>> resultMap = new HashMap<>();

        for (final Map.Entry<String, InputStream> entry : mapFileList.entrySet()) {
            try (
                    final InputStream inputStream = entry.getValue();
                    final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                final List<String> mapLines = bufferedReader.lines().collect(Collectors.toList());
                resultMap.put(entry.getKey(), mapLines);
            }
        }
        return resultMap;
    }

    UserMapManager getUserMaps(final Map<String, File> mapFileList) throws LoadUserFileException {
        final UserMapManager userMapManager = new UserMapManager();

        for (final Map.Entry<String, File> entry : mapFileList.entrySet()) {
            try (
                    final FileInputStream fileInputStream = new FileInputStream(entry.getValue());
                    final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
            ) {
                final UserMap userMap = (UserMap) objectInputStream.readObject();
                userMapManager.addMap(userMap);
            } catch (final IOException | ClassNotFoundException exception) {
                throw new LoadUserFileException("Wystapił błąd podczas ładowania mapy: " + entry.getKey(), exception);
            }
        }
        return userMapManager;

    }
}
