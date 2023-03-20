package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.utils.DataTypeRegistry;
import io.github.thatrobin.docky.utils.PageBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;
import org.apache.commons.lang3.text.WordUtils;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyDataTypeContentsPageProvider extends DockyDataProvider {

    public final FabricDataOutput dataOutput;

    public DockyDataTypeContentsPageProvider(FabricDataOutput dataOutput) {
        this(dataOutput, null);
    }

    public DockyDataTypeContentsPageProvider(FabricDataOutput dataOutput, Path baseOutputPath) {
        super(dataOutput, baseOutputPath);
        this.dataOutput = dataOutput;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateContentsPages(), getFilePath());
    }

    String generateContentsPages() {
        StringBuilder builder = new StringBuilder();
        builder.append("# Data Types")
            .append("\n\n\n")
            .append("### List\n");

        for (Map.Entry<String, PageBuilder> builderEntry : DataTypeRegistry.entries()) {
            String name = builderEntry.getKey();
            PageBuilder subfolder = builderEntry.getValue();

            builder.append("\n * [")
                .append(WordUtils.capitalize(name.replaceAll("_", " ")))
                .append("](")
                .append("data_types")
                .append("/")
                .append(name)
                .append(".md)");
        }
        return builder.toString();
    }

    private Path getFilePath() {
        return this.getBaseOutput().resolve("wiki")
            .resolve("docs")
            .resolve("data_types.md");
    }

    @Override
    public String getName() {
        return "data_types";
    }
}
