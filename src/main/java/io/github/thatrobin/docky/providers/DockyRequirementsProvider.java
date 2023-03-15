package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyRequirementsProvider extends DockyDataProvider {

    public final FabricDataOutput dataOutput;

    public DockyRequirementsProvider(FabricDataOutput dataOutput, Path baseOutputPath) {
        super(dataOutput, baseOutputPath);
        this.dataOutput = dataOutput;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateRequirements(), getFilePath());
    }

    static String generateRequirements() {
        return "mkdocs-mermaid2-plugin\nmkdocs-material\nsphinx-markdown-tables";
    }

    private Path getFilePath() {
        return this.getBaseOutput()
            .resolve("wiki")
            .resolve("requirements.txt");
    }

    @Override
    public String getName() {
        return "Requirements generator";
    }
}
