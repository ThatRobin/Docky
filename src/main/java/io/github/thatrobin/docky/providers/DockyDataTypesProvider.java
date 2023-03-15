package io.github.thatrobin.docky.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.thatrobin.docky.DockyGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;
import net.minecraft.util.JsonHelper;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class DockyDataTypesProvider extends DockyDataProvider {

    public final FabricDataOutput dataOutput;
    public final JsonObject data;
    public final String name;

    public DockyDataTypesProvider(FabricDataOutput dataOutput, String name, JsonObject data) {
        this(dataOutput, null, name, data);
    }
    public DockyDataTypesProvider(FabricDataOutput dataOutput, Path baseOutputPath, String name, JsonObject data) {
        super(dataOutput, baseOutputPath);
        this.dataOutput = dataOutput;
        this.data = data;
        this.name = name;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateEntryPage(this.data), getFilePath());
    }

    static String generateEntryPage(JsonObject data) {
        String name = null;
        String description = null;
        JsonObject example = null;
        if(data.has("name")) name = data.get("name").getAsString();
        if(data.has("description")) description = data.get("description").getAsString();
        if(data.has("example")) example = data.get("example").getAsJsonObject();

        StringBuilder builder = new StringBuilder();
        builder.append("# ")
            .append(name)
            .append("\n[Data Type](../data_types.md)\n");

        if (description != null) {
            builder.append("\n")
                .append(description)
                .append("\n");
        }/*
        builder.append("\nType ID: " + "`")
            .append(id)
            .append("`\n### Fields\nField | Type | Default | Description\n------|------|---------|-------------\n");

        SerializableData data = factory.getSerializableData();
        for (String fieldName : data.getFieldNames()) {
            if(!fieldName.equals("condition") && !fieldName.equals("inverted")) {
                builder.append("`")
                    .append(fieldName)
                    .append("` | ");
                SerializableData.Field<?> field = data.getField(fieldName);
                SerializableDataType<?> type = field.getDataType();
                for (Class<?> closs : SerializableDataTypesRegistry.entries()) {
                    for (Field field1 : closs.getFields()) {
                        try {
                            Object obj = field1.get(null);
                            SerializableDataType<?> type2 = (SerializableDataType<?>) obj;
                            if (type2.equals(type)) {
                                builder.append("[")
                                    .append(StringUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)))
                                    .append("](../data_types/")
                                    .append(field1.getName().toLowerCase(Locale.ROOT))
                                    .append(".md)");
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                }
                try {
                    Object defaultVal = field.getDefault(null);
                    if (defaultVal != null) {
                        builder.append(" | ")
                            .append(defaultVal)
                            .append(" | ");
                    } else {
                        builder.append(" | _optional_ | ");
                    }
                } catch (Exception e) {
                    builder.append(" | _optional_ | ");
                }
                if (data instanceof SerializableDataExt) {
                    SerializableDataExt ext = (SerializableDataExt) data;
                    builder.append(ext.getDescription(fieldName));
                }
                builder.append("\n");
            }
        }
        */
        if(example != null) {
            try {
                builder.append("\n### Example\n```json\n");

                String exampleDescription = "";
                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    if(example.has("example_description")) {
                        exampleDescription = JsonHelper.getString(example, "example_description");
                        example.remove("example_description");
                    }
                    builder.append(gson.toJson(example).replaceAll("\t", "   "));
                } catch(Exception e) {
                    e.printStackTrace();
                }
                builder.append("\n```\n")
                    .append(exampleDescription);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    private Path getFilePath() {
        return this.getBaseOutput()
            .resolve("wiki")
            .resolve("docs")
            .resolve("data_types")
            .resolve(this.name + ".md");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
