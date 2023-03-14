package io.github.thatrobin.docky;

import com.google.common.hash.Hashing;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface DockyGenerator extends DataGeneratorEntrypoint {

    static CompletableFuture<?> writeToPath(DataWriter writer, String json, Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                writer.write(path, json.getBytes(StandardCharsets.UTF_8), Hashing.sha256().hashString(json, StandardCharsets.UTF_8));
            }
            catch (IOException iOException) {
                Docky.LOGGER.error("Failed to save file to {}", path, iOException);
            }
        }, Util.getMainWorkerExecutor());
    }
}
