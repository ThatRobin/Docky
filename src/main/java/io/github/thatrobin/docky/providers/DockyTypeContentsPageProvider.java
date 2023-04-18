package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyEntry;
import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.DockyRegistry;
import io.github.thatrobin.docky.utils.PageBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;
import org.apache.commons.lang3.text.WordUtils;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused", "deprecation"})
public class DockyTypeContentsPageProvider extends DockyDataProvider {

    public final FabricDataOutput dataOutput;
    private final String label;

    public DockyTypeContentsPageProvider(FabricDataOutput dataOutput, String label) {
        this(dataOutput, null, label);
    }

    public DockyTypeContentsPageProvider(FabricDataOutput dataOutput, Path baseOutputPath, String label) {
        super(dataOutput, baseOutputPath);
        this.dataOutput = dataOutput;
        this.label = label;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateContentsPages(), getFilePath());
    }

    String generateContentsPages() {
        PageBuilder builder = new PageBuilder();
        builder.addTitle(WordUtils.capitalize(this.label.replaceAll("_", " ")))
            .newLine().newLine().newLine()
            .addTitle3("List");

        for (DockyEntry dockyEntry : DockyRegistry.entries()) {
            String name = dockyEntry.getFactory().getSerializerId().getPath();
            String subfolder = dockyEntry.getType();

            if(this.label.equals(subfolder)) {
                builder.addListElement("[" + WordUtils.capitalize(name.replace(".md", "").replaceAll("_", " ")) + "](" + subfolder + "/" + name + ".md)");
            }
        }
        return builder.toString();
    }

    private Path getFilePath() {
        return this.getBaseOutput().resolve("wiki")
            .resolve("docs")
            .resolve(this.label + ".md");
    }

    @Override
    public String getName() {
        return this.label;
    }
}
