package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.utils.DataTypeRegistry;
import io.github.thatrobin.docky.utils.PageBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;
import org.apache.commons.lang3.text.WordUtils;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "deprecation"})
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
        PageBuilder builder = new PageBuilder();
        builder.addTitle("Data Types")
            .newLine().newLine().newLine()
            .addTitle3("List");
        Set<Map.Entry<String, PageBuilder>> entries = DataTypeRegistry.entries();
        List<Map.Entry<String, PageBuilder>> listEntries = entries.stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
        for (Map.Entry<String, PageBuilder> builderEntry : listEntries) {
            String name = builderEntry.getKey();
            String listElement = "[" + WordUtils.capitalize(name.replaceAll("_", " ")) + "](data_types/" + name + ".md)";
            builder.addListElement(listElement);
        }
        return builder.build();
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
