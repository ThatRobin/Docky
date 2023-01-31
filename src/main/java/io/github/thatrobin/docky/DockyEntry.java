package io.github.thatrobin.docky;

import io.github.apace100.apoli.power.factory.Factory;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;

public class DockyEntry {

    private final Factory factory;
    private final String prefix;
    private final String description;
    private final String examplePath;

    public DockyEntry(Factory factory, String prefix, String description, String examplePath) {
        this.factory = factory;
        this.prefix = prefix;
        this.description = description;
        this.examplePath = examplePath;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getDescription() {
        return this.description;
    }

    public String getExamplePath() {
        return this.examplePath;
    }

    @Override
    public String toString() {
        return description;
    }
}
