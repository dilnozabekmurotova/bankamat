package uz.pdp.loader;

import uz.pdp.enums.http.Http;
import uz.pdp.exceptions.APIException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    public static void init() throws APIException {
        load();
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    private static void load() throws APIException {

    }

}
