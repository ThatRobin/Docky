package io.github.thatrobin.docky.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;

import java.nio.file.Path;

public abstract class DockyDataProvider implements DataProvider {

    private final Path baseOutputPath;
    private final FabricDataOutput dataOutput;

    public DockyDataProvider(FabricDataOutput dataOutput, Path baseOutputPath) {
        this.dataOutput = dataOutput;
        this.baseOutputPath = baseOutputPath;
    }

    public Path getBaseOutput() {
        if(baseOutputPath == null) {
            return dataOutput.getPath();
        } else {
            return this.baseOutputPath;
        }
    }

}
