package io.github.thatrobin.docky.utils;

import java.util.*;

public class SectionTitleManager {

    private static final Map<String, List<String>> typeMap = new HashMap<>();

    public static void put(String title, String path) {
        path = path + "_types.md";
        if(typeMap.containsKey(title)) {
            List<String> paths = typeMap.get(title);
            paths.add(path);
            typeMap.put(title, paths);
        } else {
            List<String> paths = new LinkedList<>();
            paths.add(path);
            typeMap.put(title, paths);
        }
    }

    public static Set<Map.Entry<String, List<String>>> entries() {
        return typeMap.entrySet();
    }
}
