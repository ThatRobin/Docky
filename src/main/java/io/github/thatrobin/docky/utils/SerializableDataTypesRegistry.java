package io.github.thatrobin.docky.utils;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.calio.data.SerializableDataTypes;

import java.util.LinkedList;
import java.util.List;

public class SerializableDataTypesRegistry {

    private static final List<Class<?>> serializableDataTypeList = new LinkedList<>();

    static {
        register(SerializableDataTypes.class);
        register(ApoliDataTypes.class);
    }

    public static void register(Class<?> clazz) {
        serializableDataTypeList.add(clazz);
    }

    public static List<Class<?>> entries() {
        return serializableDataTypeList;
    }
}
