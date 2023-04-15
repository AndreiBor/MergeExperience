package by.structure.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    private static final Properties PROPERTIES = new Properties();

    static {
        initProperties();
    }

    private static void initProperties() {
        try {
            PROPERTIES.load(PropertyUtils.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

}
