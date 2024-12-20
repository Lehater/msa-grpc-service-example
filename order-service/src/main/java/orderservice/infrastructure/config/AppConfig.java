package orderservice.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private final Properties properties = new Properties();

    public AppConfig(String configFileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
                logger.info("Configuration file {} loaded.", configFileName);
            } else {
                logger.info("Configuration file {} not found. Using environment variables only.", configFileName);
            }
        } catch (IOException e) {
            logger.error("Error loading configuration file: {}", configFileName);
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
            logger.error("Missing required configuration key: {}", key);
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
