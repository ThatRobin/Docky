package io.github.thatrobin.docky;

import io.github.apace100.apoli.power.factory.Factory;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.thatrobin.docky.utils.SerializableDataExt;

public class DockyEntry {

    private Factory factory;
    private String header;
    private String description;
    private String examplePath;

    public DockyEntry setFactory(Factory factory) {
        this.factory = factory;
        return this;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public DockyEntry setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getHeader() {
        return this.header;
    }

    public DockyEntry setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public DockyEntry setExamplePath(String examplePath) {
        this.examplePath = examplePath;
        return this;
    }

    public String getExamplePath() {
        return this.examplePath;
    }

    public SerializableDataExt getSerializableData() {
        return (SerializableDataExt) this.getFactory().getSerializableData();
    }

    @Override
    public String toString() {
        return description;
    }
}
