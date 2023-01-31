package io.github.thatrobin.docky.utils;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;

import java.util.LinkedHashMap;
import java.util.function.Function;

public class SerializableDataExt extends SerializableData {

    private final LinkedHashMap<String, String> descFields = new LinkedHashMap<>();

    public SerializableDataExt add(String name, String description, SerializableDataType<?> type) {
        descFields.put(name, description);
        this.add(name, type);
        return this;
    }

    public <T> SerializableDataExt add(String name, String description, SerializableDataType<T> type, T defaultValue) {
        descFields.put(name, description);
        this.add(name, type, defaultValue);
        return this;
    }

    public <T> SerializableDataExt addFunctionedDefault(String name, String description, SerializableDataType<T> type, Function<Instance, T> defaultFunction) {
        descFields.put(name, description);
        this.addFunctionedDefault(name, type, defaultFunction);
        return this;
    }

    public String getDescription(String name) {
        return descFields.get(name);
    }
}
