package pl.crystalek.sokoban.io;

import pl.crystalek.sokoban.io.file.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class MapLoader {

    Map<String, List<String>> getMapsInString(final FileManager fileManager) {
        final Map<String, List<String>> resultMap = new HashMap<>();

        for (final Map.Entry<String, InputStream> entry : fileManager.getMapFileList().entrySet()) {
            try (
                    final InputStream inputStream = entry.getValue();
                    final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                final List<String> mapLines = bufferedReader.lines().collect(Collectors.toList());
                resultMap.put(entry.getKey(), mapLines);
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        }
        return resultMap;
    }
}