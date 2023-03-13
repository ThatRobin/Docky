package io.github.thatrobin.docky;

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
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface DockyGenerator extends DataGeneratorEntrypoint {

    static Path getOutputPath(DataGenerator dataGenerator) {
        return ((DataGeneratorAccessor)dataGenerator).getOutputPath();
    }

    static void generate(DocumentationBuilder builder) {
        String outputPath = builder.getOutputPath();
        Optional<MkdocsBuilder> mkdocsBuilder = builder.getMkDocsBuilder();

        generateReadthedocsyaml(outputPath);
        generateRequirements(outputPath);

        for (DockyEntry dockyEntry : DockyRegistry.entryList) {
            generateEntryPage(dockyEntry, outputPath + "\\docs\\types");
        }

        generateContentsPages(outputPath);

        if(mkdocsBuilder.isPresent()) {
            generateMkdocsyml(outputPath, mkdocsBuilder.get());
        } else {
            autoGenerateMkdocsyml(outputPath);
        }
    }

    static void generateMkdocsyml(String outputPath, MkdocsBuilder mkdocsBuilder) {
        try (PrintWriter out = new PrintWriter(outputPath + "\\" + "mkdocs.yml")) {
            out.println(mkdocsBuilder.build());
        } catch (FileNotFoundException e) {
            Docky.LOGGER.error("Unable to create file: " + Arrays.toString(e.getStackTrace()));
        }
    }

    static void autoGenerateMkdocsyml(String outputPath) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : SectionTitleManager.entries()) {
            SectionBuilder sectionBuilder = new SectionBuilder(entry.getKey());
            sectionBuilder.incrementIndents();
            for (String fileName : entry.getValue()) {
                String title = "- " + WordUtils.capitalize(fileName.replace(".md", "").replaceAll("_", " "));
                String pathStr = "types/" + fileName;
                sectionBuilder.path(title, pathStr);
            }
            sectionBuilder.decrementIndents();
            builder.append(sectionBuilder.build());
        }

        try (PrintWriter out = new PrintWriter(outputPath + "\\" + "mkdocs.yml")) {
            out.println(builder.toString());
        } catch (FileNotFoundException e) {
            Docky.LOGGER.error("Unable to create file: " + Arrays.toString(e.getStackTrace()));
        }
    }

    static void generateContentsPages(String outputPath) {
        try {
            Stream<Path> pathStream = Files.list(Paths.get(outputPath + "\\docs\\types"));
            for (Path path : pathStream.collect(Collectors.toList())) {
                if(path.toFile().isDirectory()) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("# ")
                        .append(WordUtils.capitalize(path.getFileName().toString().replaceAll("_", " ")))
                        .append("\n\n\n")
                        .append("### List\n");
                    Stream<Path> pathStream2 = Files.list(Paths.get(outputPath + "\\docs\\types\\" + path.getFileName()));
                    for (Path path2 : pathStream2.collect(Collectors.toList())) {
                        builder.append("\n * [")
                            .append(WordUtils.capitalize(path2.getFileName().toString().replace(".md", "").replaceAll("_", " ")))
                            .append("](")
                            .append(path.getFileName())
                            .append("/")
                            .append(path2.getFileName().toString())
                            .append(")");
                    }
                    try (PrintWriter out = new PrintWriter(outputPath + "\\docs\\types\\" + path.getFileName() + ".md")) {
                        out.println(builder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void generateRequirements(String outputPath) {
        StringBuilder builder = new StringBuilder();
        builder.append("mkdocs-mermaid2-plugin\nmkdocs-material\nsphinx-markdown-tables");

        try (PrintWriter out = new PrintWriter(outputPath + "\\" + "requirements.txt")) {
            out.println(builder);
        } catch (FileNotFoundException e) {
            Docky.LOGGER.error("Unable to create file: " + Arrays.toString(e.getStackTrace()));
        }
    }

    static void generateReadthedocsyaml(String outputPath) {
        StringBuilder builder = new StringBuilder();
        builder.append("version: 2\n\n")
            .append("mkdocs:\n  ")
            .append("configuration: mkdocs.yml\n")
            .append("python:\n  ")
            .append("install:\n    ")
            .append("- requirements: requirements.txt");

        try (PrintWriter out = new PrintWriter(outputPath + "\\" + ".readthedocs.yaml")) {
            out.println(builder);
        } catch (FileNotFoundException e) {
            Docky.LOGGER.error("Unable to create file: " + Arrays.toString(e.getStackTrace()));
        }
    }

    static void generateDataTypePages(String outputPath) {

    }

    static void generateEntryPage(DockyEntry entry, String outputPath) {
        Factory factory = entry.getFactory();
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
        if(Files.exists(Paths.get(outputPath + "\\" + id.getPath() + ".md"))) {
            try {
                Files.delete(Paths.get(outputPath + "\\" + id.getPath() + ".md"));
            } catch (IOException e) {
                Docky.LOGGER.error(e.getMessage());
            }
        }

        if(!Files.exists(Paths.get(outputPath + "\\" + prefix + "_types"))) {
            File f1 = new File(outputPath + "\\" + prefix + "_types");
            boolean result = f1.mkdirs();
        }
        try (PrintWriter out = new PrintWriter(outputPath + "\\" + prefix + "_types" +  "\\" + id.getPath() + ".md")) {
            out.println(builder);
        } catch (FileNotFoundException e) {
            Docky.LOGGER.error("Unable to create file: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
