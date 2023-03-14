package io.github.thatrobin.docky.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.github.thatrobin.docky.Docky;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DataTypeLoader {

    public static void load() {
        try {
            List<String> files = IOUtils.readLines(Objects.requireNonNull(DataTypeLoader.class.getClassLoader().getResourceAsStream("wiki/data_types/")), Charsets.UTF_8);
            for (String file : files) {
                JsonElement reader = JsonParser.parseReader(new FileReader(DataTypeLoader.class.getClassLoader().getResource("wiki/data_types/" + file).getFile()));
                JsonObject jsonObject = (JsonObject)reader;
                DataTypeRegistry.register(file.replace(".json", ""), jsonObject);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
