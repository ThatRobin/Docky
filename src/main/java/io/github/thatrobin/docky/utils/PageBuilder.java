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

    public PageBuilder addTable(TableBuilder builder) {
        this.contents.append(builder.build());
        return this;
    }

    public PageBuilder addTable(TableBuilder builder, boolean newLine) {
        this.contents.append(builder.build());
        if(newLine) newLine();
        return this;
    }

    public void newLine() {
        this.contents.append("\n");
    }



    public static class TableBuilder {

        private final StringBuilder content = new StringBuilder();

        public TableBuilder init() {
            return new TableBuilder();
        }

        public TableBuilder addRow(String... values) {
            for (int i = 0; i < values.length; i++) {
                content.append(values[i]);
                if(i != values.length - 1) {
                    content.append(" | ");
                } else {
                    content.append("\n");
                }
            }
            return this;
        }

        public String build() {
            return this.content.toString();
        }

    }

}
