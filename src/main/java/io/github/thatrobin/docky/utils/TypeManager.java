package io.github.thatrobin.docky.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class TypeManager {

    private static final Set<String> types = new HashSet<>();

    public static void addType(String type) {
        types.add(type);
    }

    public static List<String> getTypes() {
        return new ArrayList<>(types);
    }
}
