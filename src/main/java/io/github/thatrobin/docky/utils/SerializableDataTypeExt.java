package io.github.thatrobin.docky.utils;

import com.google.gson.JsonElement;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.thatrobin.docky.mixin.SerializableDataTypeAccessor;
import net.minecraft.network.PacketByteBuf;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SerializableDataTypeExt<T> extends SerializableDataType<T> {

    public SerializableDataTypeExt(Class<T> dataClass, BiConsumer<PacketByteBuf, T> send, Function<PacketByteBuf, T> receive, Function<JsonElement, T> read) {
        super(dataClass, send, receive, read);
    }

    public static <T> SerializableDataType<T> compound(String title, Class<T> dataClass, SerializableDataExt data, Function<SerializableData.Instance, T> toInstance, BiFunction<SerializableData, T, SerializableData.Instance> toData, String examplePath) {
        DataTypeRegistry.register(title, generatePage(title, data, examplePath));
        return new SerializableDataType<>(dataClass,
            (buf, t) -> data.write(buf, toData.apply(data, t)),
            (buf) -> toInstance.apply(data.read(buf)),
            (json) -> toInstance.apply(data.read(json.getAsJsonObject())));
    }

    public static PageBuilder generatePage(String title, SerializableDataExt dataExt, String examplePath) {
        PageBuilder builder = new PageBuilder();
        builder.addTitle(WordUtils.capitalize(title.replaceAll("_", " ")));
        builder.addText("[Data Type](../data_types.md)");

        builder.addSubTitle("Fields");
        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak();
        for (String fieldName : dataExt.getFieldNames()) {
            String[] row = new String[4];
            if(!fieldName.equals("condition") && !fieldName.equals("inverted")) {
                row[0] = fieldName;
                SerializableData.Field<?> field = dataExt.getField(fieldName);
                SerializableDataType<?> type = field.getDataType();
                for (Class<?> clazz : SerializableDataTypesRegistry.entries()) {
                    for (Field field1 : clazz.getFields()) {
                        try {
                            Object obj = field1.get(null);
                            SerializableDataType<?> type2 = (SerializableDataType<?>) obj;
                            if(type2 != null) {
                                if (!((SerializableDataTypeAccessor) type2).getDataClass().isAssignableFrom(List.class)) {
                                    if (type2.equals(type)) {
                                        String typeBuilder = "[" +
                                            WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)) +
                                            "](../data_types/" +
                                            field1.getName().toLowerCase(Locale.ROOT) +
                                            ".md)";
                                        row[1] = typeBuilder;
                                    }
                                } else {
                                    if (type2.equals(type)) {
                                        String typeBuilder = "[Array](../data_types/array.md) of [" +
                                            WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)) +
                                            "](../data_types/" +
                                            field1.getName().toLowerCase(Locale.ROOT).replaceAll("(s)(?!\\S)", "") +
                                            ".md)";
                                        row[1] = typeBuilder;
                                    }
                                }
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
                row[3] = dataExt.getDescription(fieldName);
            }
            tableBuilder.addRow(row);
        }
        builder.addTable(tableBuilder);

        if(!examplePath.equals("")) {
            builder.addSubTitle("Example");
            builder.addJson(examplePath);
        }
        return builder;
    }


}
