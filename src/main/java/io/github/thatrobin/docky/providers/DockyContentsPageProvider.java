package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyEntry;
import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.DockyRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyContentsPageProvider implements DataProvider {

    public final FabricDataOutput dataOutput;
    private final String label;

    public DockyContentsPageProvider(FabricDataOutput dataOutput, String label) {
        this.dataOutput = dataOutput;
        this.label = label;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateContentsPages(), getFilePath());
    }

    String generateContentsPages() {
        StringBuilder builder = new StringBuilder();
        builder.append("# ")
            .append(StringUtils.capitalize(this.label.replaceAll("_", " ")))
            .append("\n\n\n")
            .append("### List\n");

        for (DockyEntry dockyEntry : DockyRegistry.entries()) {
            String name = dockyEntry.getFactory().getSerializerId().getPath();
            String subfolder = dockyEntry.getType();

            if(this.label.equals(subfolder)) {
                builder.append("\n * [")
                    .append(StringUtils.capitalize(name.replace(".md", "").replaceAll("_", " ")))
                    .append("](")
                    .append(subfolder)
                    .append("/")
                    .append(name)
                    .append(".md)");
            }
        }
        return builder.toString();
    }

    private Path getFilePath() {
        return dataOutput
            .getPath().resolve("wiki")
            .resolve("docs")
            .resolve(this.label + ".md");
    }

    @Override
    public String getName() {
        return this.label;
    }
}
