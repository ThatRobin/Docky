package io.github.thatrobin.docky.utils;

import com.google.gson.JsonObject;
import io.github.apace100.apoli.power.factory.Factory;
import io.github.thatrobin.docky.DockyEntry;
import org.gradle.internal.impldep.com.google.api.client.json.Json;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataTypeRegistry {
    private static final Map<String, JsonObject> DATA_TYPES = new HashMap<>();

    public static <T extends Factory> JsonObject register(String key, JsonObject value) {
        if(DATA_TYPES.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + key + "'");
        }
        DATA_TYPES.put(key, value);
        return value;
    }

    public static Map<String, JsonObject>entries() {
        return DATA_TYPES;
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
