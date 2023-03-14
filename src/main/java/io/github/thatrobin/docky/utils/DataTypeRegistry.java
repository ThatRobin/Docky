package io.github.thatrobin.docky.utils;

import com.google.gson.JsonObject;
import io.github.apace100.apoli.power.factory.Factory;

import java.util.*;

public class DataTypeRegistry {
    private static final Map<String, JsonObject> DATA_TYPES = new HashMap<>();

    public static <T extends Factory> void register(String key, JsonObject value) {
        if(DATA_TYPES.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + key + "'");
        }
        DATA_TYPES.put(key, value);
    }

    public static Map<String, JsonObject> get() {
        return DATA_TYPES;
    }

    public static Set<Map.Entry<String, JsonObject>> entries() {
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
