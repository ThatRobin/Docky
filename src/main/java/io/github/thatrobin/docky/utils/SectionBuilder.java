package io.github.thatrobin.docky.utils;

import java.util.LinkedList;
import java.util.List;

public class SectionBuilder {

    private StringBuilder name;
    private List<String> contents = new LinkedList<>();
    private int indentLevel = 1;

    public SectionBuilder(String name) {
        this.name = indent("- " + name + ":").append("\n");
    }

    public String build() {
        StringBuilder output = new StringBuilder();
        output.append(this.name);
        for (String content : contents) {
            output.append(content);
        }
        return output.toString();
    }

    public SectionBuilder path(String title, String path) {
        StringBuilder builder = indent(title).append(": ").append(path).append("\n");
        contents.add(builder.toString());
        return this;
    }

    public StringBuilder indent(String title) {
        StringBuilder empty = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            empty.append("  ");
        }
        return empty.append(title);
    }

    public SectionBuilder incrementIndents() {
        this.indentLevel++;
        return this;
    }

    public SectionBuilder decrementIndents() {
        this.indentLevel--;
        return this;
    }

}
