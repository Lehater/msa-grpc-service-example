package orderservice.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private final Properties properties = new Properties();

    public AppConfig(String configFileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.out.println("Configuration file " + configFileName + " not found. Using environment variables " +
                        "only.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration file: " + configFileName, e);
        }
    }

    public String getString(String key, boolean required) {
        // Проверяем переменную окружения
        String envValue = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envValue != null) {
            return envValue;
        }
        // Проверяем файл
        String fileValue = properties.getProperty(key);
        if (fileValue != null) {
            return fileValue;
        }
        if (required) {
            throw new IllegalArgumentException("Missing required configuration key: " + key);
        }
        return null;
    }

    public int getInt(String key, boolean required) {
        String value = getString(key, required);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    public String getString(String key) {
        return getString(key, false);
    }

    public int getInt(String key) {
        return getInt(key, false);
    }
}
