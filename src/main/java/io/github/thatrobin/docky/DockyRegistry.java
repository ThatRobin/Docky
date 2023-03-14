package io.github.thatrobin.docky;

import io.github.apace100.apoli.power.factory.Factory;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class DockyRegistry {

    private static final List<DockyEntry> ENTRY_LIST = new LinkedList<>();

    public static <T extends Factory> DockyEntry register(DockyEntry entry) {
        if(ENTRY_LIST.contains(entry)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + entry + "'");
        }
        ENTRY_LIST.add(entry);
        return entry;
    }

    public static List<DockyEntry> entries() {
        return ENTRY_LIST;
    }

    public static int size() {
        return ENTRY_LIST.size();
    }

    public static <T extends Factory> boolean contains(T factory) {
        return ENTRY_LIST.stream().anyMatch((dockyEntry -> dockyEntry.getFactory().equals(factory)));
    }

    public static void clear() {
        ENTRY_LIST.clear();
    }

    public static void reset() {
        clear();
    }
}
