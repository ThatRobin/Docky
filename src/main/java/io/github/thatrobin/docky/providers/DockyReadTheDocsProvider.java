package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyReadTheDocsProvider extends DockyDataProvider {

    public final FabricDataOutput dataOutput;

    public DockyReadTheDocsProvider(FabricDataOutput dataOutput) {
        this(dataOutput, null);
    }

    public DockyReadTheDocsProvider(FabricDataOutput dataOutput, Path baseOutputPath) {
        super(dataOutput, baseOutputPath);
        this.dataOutput = dataOutput;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateReadthedocsyaml(), getFilePath());
    }

    static String generateReadthedocsyaml() {
        return "version: 2\n\n" +
            "mkdocs:\n  " +
            "configuration: mkdocs.yml\n" +
            "python:\n  " +
            "install:\n    " +
            "- requirements: requirements.txt";
    }

    private Path getFilePath() {
        return this.getBaseOutput()
            .resolve("wiki")
            .resolve(".readthedocs.yaml");
    }

    @Override
    public String getName() {
        return "ReadTheDocs YML generator";
    }
}
