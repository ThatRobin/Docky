package io.github.thatrobin.docky;

import io.github.thatrobin.docky.utils.DataTypeLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Docky {

    public static final Logger LOGGER = LogManager.getLogger(Docky.class);


    public static void main(String[] args) {
        DataTypeLoader.load();
    }
}
