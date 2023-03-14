package io.github.thatrobin.docky.providers;

import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.utils.PageBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyPageProvider implements DataProvider {

    public final FabricDataOutput dataOutput;
    private final String name;
    private final String path;
    private final PageBuilder pageBuilder;

    public DockyPageProvider(FabricDataOutput dataOutput, String name, PageBuilder pageBuilder) {
        this(dataOutput, name, null, pageBuilder);
    }
    public DockyPageProvider(FabricDataOutput dataOutput, String name, String path, PageBuilder pageBuilder) {
        this.dataOutput = dataOutput;
        this.name = name;
        this.path = path;
        this.pageBuilder = pageBuilder;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, this.pageBuilder.build(), getFilePath());
    }

    private Path getFilePath() {
        Path path = dataOutput.getPath().resolve("wiki");
        if(this.path != null) {
            path = path.resolve(this.path);
        }
        return path.resolve(name + ".md");
    }

    @Override
    public String getName() {
        return name;
    }
}
