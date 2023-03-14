package io.github.thatrobin.docky;

import io.github.thatrobin.docky.utils.DataTypeLoader;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Docky implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger(Docky.class);

    @Override
    public void onInitialize() {
        DataTypeLoader.load();
    }
}
