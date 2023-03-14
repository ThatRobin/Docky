package io.github.thatrobin.docky.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.thatrobin.docky.Docky;
import io.github.thatrobin.docky.DockyEntry;
import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.DockyRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class DockyEntryProvider implements DataProvider {

    public final FabricDataOutput dataOutput;
    public final DockyEntry dockyEntry;

    public DockyEntryProvider(FabricDataOutput dataOutput, DockyEntry dockyEntry) {
        this.dataOutput = dataOutput;
        this.dockyEntry = dockyEntry;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(getFilePath().toString(), generateEntryPage(this.dockyEntry));
    }

    static String generateEntryPage(DockyEntry entry) {
        io.github.apace100.apoli.power.factory.Factory factory = entry.getFactory();
        String prefix = entry.getSerializableData().getLabel();
        String description = entry.getDescription();
        String path = entry.getExamplePath();


        Identifier id = factory.getSerializerId();

        StringBuilder builder = new StringBuilder();
        builder.append("# ")
            .append(WordUtils.capitalize(id.getPath().replaceAll("_", " ")))
            .append("\n[")
            .append(WordUtils.capitalize(prefix.replaceAll("_", " ")))
            .append(" Type](../")
            .append(prefix)
            .append("_types.md)\n");

        if (description != null) {
            builder.append("\n")
                .append(description)
                .append("\n");
        }
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
                                    .append(WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)))
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
        if(path != null) {
            try {
                builder.append("\n### Example\n```json\n");

                String exampleDescription = "";
                JsonParser parser = new JsonParser();
                try {
                    Object obj = parser.parse(new FileReader(path));
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonObject jsonObject = (JsonObject)obj;
                    if(jsonObject.has("example_description")) {
                        exampleDescription = JsonHelper.getString(jsonObject, "example_description");
                        jsonObject.remove("example_description");
                    }
                    builder.append(gson.toJson(jsonObject).replaceAll("\t", "   "));
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
        return dataOutput
            .getResolver(DataOutput.OutputType.RESOURCE_PACK, "wiki")
            .resolve(new Identifier(dataOutput.getModId()), "md");
    }

    @Override
    public String getName() {
        return "Test run i guess";
    }
}
