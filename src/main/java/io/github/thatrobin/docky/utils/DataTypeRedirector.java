package io.github.thatrobin.docky.utils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@SuppressWarnings({"unused"})
public class DataTypeRedirector {

    private static final Map<String, String> DATA_TYPES = new TreeMap<>();

    public static void register(String key, String value) {
        if(DATA_TYPES.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + key + "'");
        }
        DATA_TYPES.put(key, value);
    }

    public static Map<String, String> get() {
        return DATA_TYPES;
    }

    public static Set<Map.Entry<String, String>> entries() {
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
