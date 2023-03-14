package io.github.thatrobin.docky;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.apace100.apoli.power.factory.Factory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.thatrobin.docky.mixin.DataGeneratorAccessor;
import io.github.thatrobin.docky.utils.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface DockyGenerator extends DataGeneratorEntrypoint {

    static Path getOutputPath(DataGenerator dataGenerator) {
        return ((DataGeneratorAccessor)dataGenerator).getOutputPath();
    }

    static CompletableFuture<?> writeToPath(DataWriter writer, String json, Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                writer.write(path, json.getBytes(StandardCharsets.UTF_8), Hashing.sha1().hashString(json, StandardCharsets.UTF_8));
            }
            catch (IOException iOException) {
                Docky.LOGGER.error("Failed to save file to {}", path, iOException);
            }
        }, Util.getMainWorkerExecutor());
    }
}
