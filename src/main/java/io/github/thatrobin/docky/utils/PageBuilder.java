package io.github.thatrobin.docky.utils;

import java.util.LinkedList;
import java.util.List;

public class PageBuilder {

    private List<String> contents = new LinkedList<>();

    public PageBuilder setTitle(String title) {
        this.contents.add("# " + title + "\n\n");
        return this;
    }

    public PageBuilder setDescription(String description) {
        this.contents.add(description + "\n");
        return this;
    }
}
