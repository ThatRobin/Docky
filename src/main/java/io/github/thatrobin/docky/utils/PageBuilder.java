package io.github.thatrobin.docky.utils;

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

    public void newLine() {
        this.contents.append("\n");
    }
}
