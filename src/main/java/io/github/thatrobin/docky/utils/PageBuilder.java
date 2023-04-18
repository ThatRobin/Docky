package io.github.thatrobin.docky.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.thatrobin.docky.Docky;
import net.minecraft.util.JsonHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PageBuilder {

    private final StringBuilder contents = new StringBuilder();

    public static PageBuilder init() {
        return new PageBuilder();
    }

    public String build() {
        return contents.toString();
    }

    public PageBuilder addTitle(String name) {
        return addTitle(name, true);
    }

    public PageBuilder addTitle(String name, boolean newLine) {
        this.contents.append("# ").append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addTitle2(String name) {
        return addTitle2(name, true);
    }

    public PageBuilder addTitle2(String name, boolean newLine) {
        this.contents.append("## ").append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addTitle3(String name) {
        return addTitle3(name, true);
    }

    public PageBuilder addTitle3(String name, boolean newLine) {
        this.contents.append("### ").append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addText(String name) {
        return addText(name, true);
    }

    public PageBuilder addText(String name, boolean newLine) {
        this.contents.append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addLink(String text, String link) {
        return addLink(text, link, true);
    }

    public PageBuilder addLink(String text, String link, boolean newLine) {
        this.contents.append("[").append(text).append("](").append(link).append(")");
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addTable(TableBuilder builder) {
        return addTable(builder, true);
    }

    public PageBuilder addTable(TableBuilder builder, boolean newLine) {
        this.contents.append(builder.build());
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addJson(String path) {
        return addJson(path, true);
    }

    public PageBuilder addJson(String path, boolean newLine) {
        this.contents.append("```json\n");
        String exampleDescription = "";
        try {
            JsonObject jsonObject = (JsonObject)JsonParser.parseReader(new FileReader(path));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if(jsonObject.has("example_description")) {
                exampleDescription = JsonHelper.getString(jsonObject, "example_description");
                jsonObject.remove("example_description");
            }
            this.contents.append(gson.toJson(jsonObject).replaceAll("\t", "   "));
        } catch (FileNotFoundException e) {
            Docky.LOGGER.warn("Example not found at: " + path);
        }
        this.contents.append("\n```\n")
            .append(exampleDescription);
        if(newLine) newLine();
        return this;
    }


    public PageBuilder addNote(String text) {
        return addNote(text, true);
    }

    public PageBuilder addNote(String text, boolean newLine) {
        this.contents.append("!!! note\n    \n  ")
            .append(text);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addListElement(String name) {
        return addListElement(name, true);
    }

    public PageBuilder addListElement(String name, boolean newLine) {
        this.contents.append("* ").append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder newLine() {
        this.contents.append("\n");
        return this;
    }

    public static class TableBuilder {

        private final StringBuilder content = new StringBuilder();

        public static TableBuilder init() {
            TableBuilder tableBuilder = new TableBuilder();
            tableBuilder.content.append("\n");
            return tableBuilder;
        }

        public TableBuilder addRow(String... values) {
            content.append(" | ");
            for (int i = 0; i < values.length; i++) {
                content.append(values[i]);
                content.append(" | ");
                if(i == values.length - 1) {
                    content.append("\n");
                }
            }
            return this;
        }

        public TableBuilder addBreak() {
            content.append("|---|---|---|---|\n");
            return this;
        }

        public String build() {
            return this.content.toString();
        }

    }

}
