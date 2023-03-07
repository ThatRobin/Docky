package io.github.thatrobin.docky.utils;

import io.github.thatrobin.docky.DockyGenerator;

import java.util.Optional;

public class DocumentationBuilder {


    private String outputPath;
    private MkdocsBuilder builder;

    public DocumentationBuilder() {

    }

    public DocumentationBuilder outputPath(String outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    public DocumentationBuilder mkdocs(MkdocsBuilder builder) {
        this.builder = builder;
        return this;
    }

    public String getOutputPath() {
        return outputPath;
    }
    public Optional<MkdocsBuilder> getMkDocsBuilder() {
        return Optional.of(builder);
    }

    public void build() {
        DockyGenerator.generate(this);
    }

}
