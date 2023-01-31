package io.github.thatrobin.docky.utils;

import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;

import java.util.*;

public class SerializableDataTypeRegistry {

    private static final Map<SerializableDataType<?>, String> typeMap = new HashMap<>();

    static {
        typeMap.put(SerializableDataTypes.INTS, "");
    }

}
