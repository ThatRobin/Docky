package io.github.thatrobin.docky.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.thatrobin.docky.DockyEntry;
import io.github.thatrobin.docky.DockyGenerator;
import io.github.thatrobin.docky.utils.SerializableDataExt;
import io.github.thatrobin.docky.utils.SerializableDataTypesRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.text.WordUtils;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class DockyReadTheDocsProvider implements DataProvider {

    public final FabricDataOutput dataOutput;

    public DockyReadTheDocsProvider(FabricDataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DockyGenerator.writeToPath(writer, generateReadthedocsyaml(), getFilePath());
    }

    static String generateReadthedocsyaml() {
        return "version: 2\n\n" +
            "mkdocs:\n  " +
            "configuration: mkdocs.yml\n" +
            "python:\n  " +
            "install:\n    " +
            "- requirements: requirements.txt";
    }

    private Path getFilePath() {
        return dataOutput
            .getPath().resolve("wiki")
            .resolve(".readthedocs.yaml");
    }

    @Override
    public String getName() {
        return "ReadTheDocs YML generator";
    }
}
