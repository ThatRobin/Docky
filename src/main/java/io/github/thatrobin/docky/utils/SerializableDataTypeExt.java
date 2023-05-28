package io.github.thatrobin.docky.utils;

import com.google.common.collect.BiMap;
import com.google.gson.JsonElement;
import com.mojang.brigadier.arguments.ArgumentType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.ArgumentWrapper;
import io.github.thatrobin.docky.mixin.SerializableDataTypeAccessor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"unused", "deprecation"})
public class SerializableDataTypeExt<T> extends SerializableDataType<T> {

    public SerializableDataTypeExt(Class<T> dataClass, BiConsumer<PacketByteBuf, T> send, Function<PacketByteBuf, T> receive, Function<JsonElement, T> read) {
        super(dataClass, send, receive, read);
    }

    public static <T> SerializableDataType<T> compound(String title, String description, Class<T> dataClass, SerializableDataExt data, Function<SerializableData.Instance, T> toInstance, BiFunction<SerializableData, T, SerializableData.Instance> toData, String examplePath) {
        DataTypeRegistry.register(title, generateCompoundPage(title, description, data, examplePath));
        return SerializableDataType.compound(dataClass, data, toInstance, toData);
    }

    public static <T> SerializableDataType<T> mapped(String title, String description, Class<T> dataClass, BiMap<String, T> map) {
        DataTypeRegistry.register(title, generateMappedPage(title, description, map));
        return SerializableDataType.mapped(dataClass, map);
    }

    public static <T> SerializableDataType<T> registry(String title, String description, Class<T> dataClass, Registry<T> registry) {
        DataTypeRegistry.register(title, generateRegistryPage(title, description));
        return SerializableDataType.registry(dataClass, registry);
    }

    public static <T extends Enum<T>> SerializableDataType<T> enumValue(String title, String description, Class<T> dataClass) {
        return enumValue(title, description, dataClass, null);
    }

    public static <T extends Enum<T>> SerializableDataType<T> enumValue(String title, String description, Class<T> dataClass, HashMap<String, T> additionalMap) {
        DataTypeRegistry.register(title, generateEnumPage(title, description, dataClass));
        return SerializableDataType.enumValue(dataClass, additionalMap);
    }

    public static <T, U> SerializableDataType<T> wrap(String title, Class<T> dataClass, SerializableDataType<U> base, Function<T, U> toFunction, Function<U, T> fromFunction) {
        DataTypeRegistry.register(title, DataTypeRegistry.get().get(((ParameterizedType)base.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName().toLowerCase()));
        return SerializableDataType.wrap(dataClass, base, toFunction, fromFunction);
    }

    public static <T> SerializableDataType<TagKey<T>> tag(String title, RegistryKey<? extends Registry<T>> registryKey) {
        DataTypeRegistry.register(title, DataTypeRegistry.get().get("identifier"));
        return SerializableDataType.tag(registryKey);
    }

    public static <T> SerializableDataType<RegistryKey<T>> registryKey(String title, RegistryKey<Registry<T>> registryKeyRegistry) {
        DataTypeRegistry.register(title, DataTypeRegistry.get().get("identifier"));
        return SerializableDataType.registryKey(registryKeyRegistry);
    }

    public static <T extends Enum<T>> SerializableDataType<EnumSet<T>> enumSet(String title, Class<T> enumClass, SerializableDataType<T> enumDataType) {
        DataTypeRegistry.register(title, DataTypeRegistry.get().get("string"));
        return SerializableDataType.enumSet(enumClass, enumDataType);
    }

    public static <T, U extends ArgumentType<T>> SerializableDataType<ArgumentWrapper<T>> argumentType(String title, U argumentType) {
        DataTypeRegistry.register(title, DataTypeRegistry.get().get("string"));
        return SerializableDataType.argumentType(argumentType);
    }

    public static PageBuilder generateCompoundPage(String title, String description, SerializableDataExt dataExt, String examplePath) {
        PageBuilder pageBuilder = new PageBuilder();
        pageBuilder.addTitle(WordUtils.capitalize(title.replaceAll("_", " ")));
        pageBuilder.addLink("Data Type", "../data_types.md").newLine();

        pageBuilder.addText(description);

        pageBuilder.addTitle3("Fields");
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
                            if (type2 != null) {
                                if (type2.equals(type)) {
                                    StringBuilder typeBuilder = new StringBuilder();
                                    String temp = field1.getName().toLowerCase(Locale.ROOT);
                                    typeBuilder.append("[");
                                    if (((SerializableDataTypeAccessor<?>) type2).getDataClass().isAssignableFrom(List.class)) {
                                        typeBuilder.append("Array](../data_types/array.md) of [");
                                        temp = temp.replaceAll("(s)(?!\\S)", "");
                                    }
                                    if(DataTypeRedirector.get().containsKey(field1.getName().toLowerCase())) {
                                        typeBuilder.append(WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)))
                                            .append("](")
                                            .append(DataTypeRedirector.get().get(field1.getName().toLowerCase()));
                                    } else {
                                        typeBuilder.append(WordUtils.capitalize(field1.getName().replaceAll("_", " ").toLowerCase(Locale.ROOT)))
                                            .append("](../data_types/")
                                            .append(temp)
                                            .append(".md)");
                                    }
                                    row[1] = typeBuilder.toString();
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
                tableBuilder.addRow(row);
            }
        }
        pageBuilder.addTable(tableBuilder);

        if(examplePath != null) {
            try {
                pageBuilder.addTitle3("Example");
                pageBuilder.addJson(examplePath);
            } catch(Exception ignored) {}
        }
        return pageBuilder;
    }

    public static <T> PageBuilder generateMappedPage(String title, String description, BiMap<String, T> map) {
        PageBuilder pageBuilder = new PageBuilder();
        pageBuilder.addTitle(WordUtils.capitalize(title.replaceAll("_", " ")));
        pageBuilder.addLink("Data Type", "../data_types.md").newLine();

        pageBuilder.addText(description);

        pageBuilder.addTitle3("Fields");
        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Name", "Description")
            .addBreak(2);

        for (Map.Entry<String, T> stringTEntry : map.entrySet()) {
            String[] row = new String[2];
            row[0] = stringTEntry.getKey();
            tableBuilder.addRow(row);

        }
        pageBuilder.addTable(tableBuilder);

        return pageBuilder;
    }

    public static PageBuilder generateRegistryPage(String title, String description) {
        PageBuilder pageBuilder = new PageBuilder();
        pageBuilder.addTitle(WordUtils.capitalize(title.replaceAll("_", " ")));
        pageBuilder.addLink("Data Type", "../data_types.md").newLine();

        pageBuilder.addText(description);

        return pageBuilder;
    }

    public static <T extends Enum<T>> PageBuilder generateEnumPage(String title, String description, Class<T> dataClass) {
        PageBuilder pageBuilder = new PageBuilder();
        pageBuilder.addTitle(WordUtils.capitalize(title.replaceAll("_", " ")));
        pageBuilder.addLink("Data Type", "../data_types.md").newLine();

        pageBuilder.addText(description);

        T[] enumValues = dataClass.getEnumConstants();

        pageBuilder.addTitle3("Fields");
        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Name", "Description")
            .addBreak(2);

        for (T enumValue : enumValues) {
            String[] row = new String[2];
            row[0] = enumValue.name();
            tableBuilder.addRow(row);
        }
        pageBuilder.addTable(tableBuilder);



        return pageBuilder;
    }

}
