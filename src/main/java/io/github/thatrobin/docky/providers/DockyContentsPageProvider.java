package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.utils.SectionTitleManager;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DockyContentsPageProvider implements DataProvider {

    public final FabricDataOutput dataOutput;

    public DockyContentsPageProvider(FabricDataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateContentsPages(), getFilePath());
    }

    static String generateContentsPages() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : SectionTitleManager.entries()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }

    private Path getFilePath() {
        return dataOutput
            .getPath().resolve("wiki")
            .resolve("requirements.txt");
    }

    @Override
    public String getName() {
        return "Requirements generator";
    }
}
