package io.github.thatrobin.docky;

import io.github.apace100.apoli.power.factory.Factory;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.thatrobin.docky.utils.SectionTitleManager;
import net.fabricmc.api.ModInitializer;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class DockyRegistry {
    public static List<DockyEntry> entryList = new LinkedList<>();

    public static <T extends Factory> DockyEntry register(DockyEntry entry) {
        if(entryList.contains(entry)) {
            throw new IllegalArgumentException("Duplicate entry tried to register: '" + entry + "'");
        }
        entryList.add(entry);
        if(entry.getHeader() != null && entry.getHeader().isEmpty()) {
            SectionTitleManager.put(entry.getHeader(), entry.getPath());
        }
        return entry;
    }

    public static int size() {
        return entryList.size();
    }

    public static <T extends Factory> boolean contains(T factory) {
        return entryList.stream().anyMatch((dockyEntry -> dockyEntry.getFactory().equals(factory)));
    }

    public static void clear() {
        entryList.clear();
    }

    public static void reset() {
        clear();
    }
}
