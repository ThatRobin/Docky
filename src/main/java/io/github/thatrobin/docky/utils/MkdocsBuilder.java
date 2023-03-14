package io.github.thatrobin.docky.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class MkdocsBuilder {

    private final List<String> contents = new LinkedList<>();
    private int indentationLevel = 0;

    public static MkdocsBuilder init() {
        return new MkdocsBuilder();
    }

    public MkdocsBuilder setName(String name) {
        this.contents.add("site_name: " + name + "\n");
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        for (String content : this.contents) {
            builder.append(content);
        }
        return builder.toString();
    }

    public MkdocsBuilder navigation(DocEntry... entries) {
        this.contents.add("\nnav:");
        this.indentationLevel++;
        for (DocEntry entry : entries) {
            if (entry.getDataType().equals(DocEntry.DataType.GROUP)) {
                addGroup(entry);
            }
            if (entry.getDataType().equals(DocEntry.DataType.STRING)) {
                addString(entry);
            }
        }
        this.contents.add("\n");
        return this;
    }

    public void indent() {
        for (int i = 0; i < indentationLevel; i++) {
            this.contents.add("  ");
        }
    }

    @SuppressWarnings("unchecked")
    public void addGroup(DocEntry entry) {
        this.contents.add("\n");
        indent();
        this.contents.add("- " + entry.getTitle() + ":");
        this.indentationLevel++;
        List<DocEntry> entries = (List<DocEntry>) entry.data;
        for (DocEntry docEntry : entries) {
            if (docEntry.getDataType().equals(DocEntry.DataType.GROUP)) {
                addGroup(docEntry);
            }
            if (docEntry.getDataType().equals(DocEntry.DataType.STRING)) {
                addString(docEntry);
            }
        }
        this.indentationLevel--;
    }

    public void addString(DocEntry entry) {
        this.contents.add("\n");
        indent();
        this.contents.add("- " + entry.getTitle() + ": " + entry.getString());
    }

    public static DocEntry createSection(String title, DocEntry... entries) {
        return new DocEntry(title, Arrays.asList(entries));
    }

    public static DocEntry createPage(String title, String path){
        return new DocEntry(title, path);
    }

    public MkdocsBuilder theme() {
        String themeBuilder = "\ntheme:\n    name: material\n    palette:\n        - media: \"(prefers-color-scheme: dark)\"\n          scheme: slate\n          primary: deep purple\n          toggle:\n            icon: material/toggle-switch-off-outline\n            name: Switch to light mode\n        - media: \"(prefers-color-scheme: light)\"\n          scheme: default\n          primary: deep purple\n          toggle:\n            icon: material/toggle-switch\n            name: Switch to dark mode\n\n" +
            "separator: '_'\nplugins:\n    - search\n    - mermaid2:\n        version: 8.6.4\n        arguments:\n            theme: 'neutral'\n\n" +
            "markdown_extensions:\n    - admonition\n" +
            "extra_javascript:\n    - https://unpkg.com/mermaid@8.7.0/dist/mermaid.min.js";

        this.contents.add(themeBuilder);
        return this;
    }
}
