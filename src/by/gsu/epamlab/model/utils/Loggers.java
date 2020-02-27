package by.gsu.epamlab.model.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

public class Loggers {
    private static FileHandler loggerHandler;

    static {
        try {
            loggerHandler = new FileHandler(LOG_PATH + LOG_FILE, true);
        } catch (IOException e) {
            System.err.println(loggerHandler);
            e.printStackTrace();
        }
    }

    public static Logger init(String className) /*throws IOException*/ {
        Logger logger = Logger.getLogger(className);
        loggerHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(loggerHandler);
        logger.setLevel(Level.ALL);
        return logger;
    }
}
