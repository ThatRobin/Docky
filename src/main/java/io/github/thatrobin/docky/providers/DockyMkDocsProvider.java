package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.utils.MkdocsBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyMkDocsProvider implements DataProvider {

    public final FabricDataOutput dataOutput;
    public final MkdocsBuilder mkdocsBuilder;

    public DockyMkDocsProvider(FabricDataOutput dataOutput, MkdocsBuilder mkdocsBuilder) {
        this.dataOutput = dataOutput;
        this.mkdocsBuilder = mkdocsBuilder;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, this.mkdocsBuilder.build(), getFilePath());
    }

    private Path getFilePath() {
        return dataOutput
            .getPath().resolve("wiki")
            .resolve("mkdocs.yml");
    }

    @Override
    public String getName() {
        return "mkdocs yml generator";
    }
}
