package io.github.thatrobin.docky.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.JsonHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;

@SuppressWarnings("unused")
public class PageBuilder {

    private final StringBuilder contents = new StringBuilder();

    public static PageBuilder init() {
        return new PageBuilder();
    }

    public String build() {
        return contents.toString();
    }

    public PageBuilder addTitle(String name) {
        this.contents.append("# ").append(name);
        newLine();
        return this;
    }

    public PageBuilder addTitle(String name, boolean newLine) {
        this.contents.append("# ").append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addSubTitle(String name) {
        this.contents.append("## ").append(name);
        newLine();
        return this;
    }

    public PageBuilder addSubTitle(String name, boolean newLine) {
        this.contents.append("## ").append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addText(String name) {
        this.contents.append(name);
        newLine();
        return this;
    }

    public PageBuilder addText(String name, boolean newLine) {
        this.contents.append(name);
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addLink(String text, String link) {
        this.contents.append("[").append(text).append("](").append(link).append(")");
        newLine();
        return this;
    }

    public PageBuilder addLink(String text, String link, boolean newLine) {
        this.contents.append("[").append(text).append("](").append(link).append(")");
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addTable(TableBuilder builder) {
        this.contents.append(builder.build());
        newLine();
        return this;
    }

    public PageBuilder addTable(TableBuilder builder, boolean newLine) {
        this.contents.append(builder.build());
        if(newLine) newLine();
        return this;
    }

    public PageBuilder addJson(String path) {
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
            throw new RuntimeException(e);
        }
        this.contents.append("```")
            .append(exampleDescription);
        newLine();
        return this;
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
            throw new RuntimeException(e);
        }
        this.contents.append("\n\n```\n\n")
            .append(exampleDescription);
        if(newLine) newLine();
        return this;
    }

    public void newLine() {
        this.contents.append("\n");
    }



    public static class TableBuilder {

        private final StringBuilder content = new StringBuilder();

        public static TableBuilder init() {
            return new TableBuilder();
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
