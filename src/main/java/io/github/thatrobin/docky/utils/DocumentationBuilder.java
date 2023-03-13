package io.github.thatrobin.docky.utils;

import io.github.thatrobin.docky.DockyGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.nio.file.Path;
import java.util.Optional;

public class DocumentationBuilder {

    private MkdocsBuilder builder;

    public DocumentationBuilder() {
    }

    public DocumentationBuilder mkdocs(MkdocsBuilder builder) {
        this.builder = builder;
        return this;
    }

    public Optional<MkdocsBuilder> getMkDocsBuilder() {
        return Optional.of(builder);
    }

    public void build(Path path) {
        DockyGenerator.generate(this, path);
    }

}
