package io.github.thatrobin.docky;

import io.github.apace100.apoli.power.factory.Factory;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.fabricmc.api.ModInitializer;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class DockyRegistry {
    public static List<DockyEntry> entryList = new LinkedList<>();

    public static DockyEntry register(Factory factory, String prefix, @Nullable String description, @Nullable String examplePath) {
        DockyEntry newEntry = new DockyEntry(factory, prefix, description, examplePath);
        if(entryList.contains(newEntry)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + newEntry + "'");
        }
        entryList.add(newEntry);
        return newEntry;
    }

    public static int size() {
        return entryList.size();
    }

    public static boolean contains(PowerFactory<?> factory) {
        return entryList.stream().anyMatch((dockyEntry -> dockyEntry.getFactory().equals(factory)));
    }

    public static void clear() {
        entryList.clear();
    }

    public static void reset() {
        clear();
    }
}
