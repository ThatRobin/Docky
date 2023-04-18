package io.github.thatrobin.docky.providers;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.thatrobin.docky.DockyEntry;
import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.mixin.SerializableDataTypeAccessor;
import io.github.thatrobin.docky.utils.DataTypeRedirector;
import io.github.thatrobin.docky.utils.PageBuilder;
import io.github.thatrobin.docky.utils.SerializableDataExt;
import io.github.thatrobin.docky.utils.SerializableDataTypesRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused", "deprecation"})
public class DockyEntryProvider extends DockyDataProvider {

    public FabricDataOutput dataOutput;
    public DockyEntry dockyEntry;

    public DockyEntryProvider(FabricDataOutput dataOutput, DockyEntry dockyEntry) {
        this(dataOutput, null, dockyEntry);
    }

    public DockyEntryProvider(FabricDataOutput dataOutput, Path baseOutputPath, DockyEntry dockyEntry) {
        super(dataOutput, baseOutputPath);
        this.dataOutput = dataOutput;
        this.dockyEntry = dockyEntry;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateEntryPage(this.dockyEntry), getFilePath());
    }

    static String generateEntryPage(DockyEntry entry) {
        io.github.apace100.apoli.power.factory.Factory factory = entry.getFactory();
        String prefix = entry.getType();
        String description = entry.getDescription();
        String path = entry.getExamplePath();

        Identifier id = factory.getSerializerId();

        PageBuilder pageBuilder = new PageBuilder();
        pageBuilder.addTitle(WordUtils.capitalize(id.getPath().replaceAll("_", " ")))
            .addLink(WordUtils.capitalize(prefix.replaceAll("_", " ")), "../" + prefix + ".md");

        if (description != null) {
            pageBuilder.addText(description);
        }
        pageBuilder.addText("Type ID: `" + id)
            .addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = new PageBuilder.TableBuilder();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak();

        SerializableData data = factory.getSerializableData();
        for (String fieldName : data.getFieldNames()) {
            String[] row = new String[4];
            if(!fieldName.equals("condition") && !fieldName.equals("inverted")) {
                row[0] = "`" + fieldName + "`";
                SerializableData.Field<?> field = data.getField(fieldName);
                SerializableDataType<?> type = field.getDataType();
                for (Class<?> clazz : SerializableDataTypesRegistry.entries()) {
                    for (Field field1 : clazz.getFields()) {
                        try {
                            Object obj = field1.get(null);
                            SerializableDataType<?> type2 = (SerializableDataType<?>) obj;
                            if (type2.equals(type)) {
                                StringBuilder typeBuilder = new StringBuilder();
                                String temp = field1.getName().toLowerCase(Locale.ROOT);
                                typeBuilder.append("[");
                                if (!((SerializableDataTypeAccessor<?>) type2).getDataClass().isAssignableFrom(List.class)) {
                                    typeBuilder.append("Array](../data_types/array.md) of [");
                                    temp = temp.replaceAll("(s)(?!\\S)", "");
                                }
                                if (DataTypeRedirector.get().containsKey(field1.getName().toLowerCase())) {
                                    typeBuilder.append(WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)))
                                        .append("](")
                                        .append(DataTypeRedirector.get().get(field1.getName().toLowerCase()))
                                        .append(")");
                                } else {
                                    typeBuilder.append(WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)))
                                        .append("](../data_types/")
                                        .append(field1.getName().toLowerCase(Locale.ROOT))
                                        .append(".md)");
                                }
                                row[1] = typeBuilder.toString();
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                }
                try {
                    Object defaultVal = field.getDefault(null);
                    if (defaultVal != null) {
                        row[2] = (String)defaultVal;
                    } else {
                        row[2] = "_optional_";
                    }
                } catch (Exception e) {
                    row[2] = "_optional_";
                }
                if (data instanceof SerializableDataExt) {
                    SerializableDataExt ext = (SerializableDataExt) data;
                    row[3] = ext.getDescription(fieldName);
                } else {
                    row[3] = "";
                }
                tableBuilder.addRow(row);
            }
        }
        pageBuilder.addTable(tableBuilder);

        if(path != null) {
            try {
                pageBuilder.addTitle3("Example")
                    .addJson(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pageBuilder.toString();
    }

    private Path getFilePath() {
        Identifier id = this.dockyEntry.getFactory().getSerializerId();
        return this.getBaseOutput()
            .resolve("wiki")
            .resolve("docs")
            .resolve(this.dockyEntry.getType())
            .resolve(id.getPath() + ".md");
    }

    @Override
    public String getName() {
        return this.dockyEntry.getType() + ": " + this.dockyEntry.getFactory().getSerializerId().toString();
    }
}
