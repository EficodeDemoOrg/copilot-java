package com.weather.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to handle application configuration
 */
public class ConfigUtil {
    private static final String API_KEY_ENV_VARIABLE = "OPENWEATHERMAP_API_KEY";
    private static final String CONFIG_FILE = "config.properties";
    private static final String API_KEY_PROPERTY = "api.key";

    /**
     * Gets the OpenWeatherMap API key from environment variables or config file
     * @return the API key
     * @throws ConfigException if the API key cannot be found
     */
    public static String getApiKey() throws ConfigException {
        // First try environment variable
        String apiKey = System.getenv(API_KEY_ENV_VARIABLE);
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            return apiKey;
        }

        // Then try properties file
        try {
            Properties properties = loadProperties();
            apiKey = properties.getProperty(API_KEY_PROPERTY);
            if (apiKey != null && !apiKey.trim().isEmpty()) {
                return apiKey;
            }
        } catch (IOException e) {
            throw new ConfigException("Failed to load configuration file", e);
        }

        throw new ConfigException("API key not found. Please set the " + API_KEY_ENV_VARIABLE +
                " environment variable or add " + API_KEY_PROPERTY + " to " + CONFIG_FILE);
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        }
        return properties;
    }

    /**
     * Exception thrown when there is an error in configuration
     */
    public static class ConfigException extends Exception {
        public ConfigException(String message) {
            super(message);
        }

        public ConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
