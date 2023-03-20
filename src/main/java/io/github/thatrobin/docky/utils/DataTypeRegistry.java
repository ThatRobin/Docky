package io.github.thatrobin.docky.utils;

import java.util.*;

@SuppressWarnings("unused")
public class DataTypeRegistry {
    private static final Map<String, PageBuilder> DATA_TYPES = new TreeMap<>();

    public static void register(String key, PageBuilder value) {
        if(DATA_TYPES.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + key + "'");
        }
        DATA_TYPES.put(key, value);
    }

    public static Map<String, PageBuilder> get() {
        return DATA_TYPES;
    }

    public static Set<Map.Entry<String, PageBuilder>> entries() {
        return DATA_TYPES.entrySet();
    }

    public static int size() {
        return DATA_TYPES.size();
    }


    public static void clear() {
        DATA_TYPES.clear();
    }

    public static void reset() {
        clear();
    }
}
