package io.github.thatrobin.docky.utils;

import com.mojang.datafixers.util.Pair;
import kotlin.TypeCastException;

import java.util.List;

public class DocEntry {

    public Object data;
    private String title;

    public DocEntry(String title, String path) {
        this.title = title;
        this.data = path;
    }

    public DocEntry(String title, List<DocEntry> path) {
        this.title = title;
        this.data = path;
    }

    public String getTitle() {
        return title;
    }

    public String getString() {
        if(this.data instanceof String) {
            return (String)this.data;
        }
        throw new RuntimeException("Could not cast data to String.");
    }

    public List<DocEntry> getEntries() {
        if(this.data instanceof List) {
            return (List<DocEntry>)this.data;
        }
        throw new RuntimeException("Could not cast data to List<DocEntry>.");
    }

    public DataType getDataType() {
        if(this.data instanceof String) {
            return DataType.STRING;
        }
        return DataType.GROUP;
    }

    public enum DataType {
        STRING,
        GROUP
    }

}
