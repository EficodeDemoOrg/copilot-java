package com.weather.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to handle application configuration
 */
public class ConfigUtil {
    private static final Logger LOGGER = Logger.getLogger(ConfigUtil.class.getName());
    private static final String API_KEY_ENV_VARIABLE = "OPENWEATHERMAP_API_KEY";
    private static final String API_URL_ENV_VARIABLE = "OPENWEATHERMAP_API_URL";
    private static final String CONFIG_FILE = "config.properties";
    private static final String API_KEY_PROPERTY = "api.key";
    private static final String API_URL_PROPERTY = "api.base.url";
    private static final String DEFAULT_API_URL = "https://api.openweathermap.org/data/2.5/weather";

    /**
     * Gets the OpenWeatherMap API key from environment variables or config file
     * @return the API key
     * @throws ConfigException if the API key cannot be found
     */
    public static String getApiKey() throws ConfigException {
        // First try environment variable
        String apiKey = System.getenv(API_KEY_ENV_VARIABLE);
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            LOGGER.log(Level.FINE, "API key found in environment variable");
            return apiKey;
        }

        // Then try properties file
        try {
            Properties properties = loadProperties();
            apiKey = properties.getProperty(API_KEY_PROPERTY);
            if (apiKey != null && !apiKey.trim().isEmpty()) {
                LOGGER.log(Level.FINE, "API key found in properties file");
                return apiKey;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load configuration file", e);
            throw new ConfigException("Failed to load configuration file", e);
        }

        LOGGER.log(Level.SEVERE, "API key not found in environment variable or properties file");
        throw new ConfigException("API key not found. Please set the " + API_KEY_ENV_VARIABLE +
                " environment variable or add " + API_KEY_PROPERTY + " to " + CONFIG_FILE);
    }
    
    /**
     * Gets the OpenWeatherMap API base URL from environment variables or config file
     * @return the API base URL
     */
    public static String getApiBaseUrl() {
        // First try environment variable
        String apiUrl = System.getenv(API_URL_ENV_VARIABLE);
        if (apiUrl != null && !apiUrl.trim().isEmpty()) {
            LOGGER.log(Level.FINE, "API base URL found in environment variable");
            return apiUrl;
        }

        // Then try properties file
        try {
            Properties properties = loadProperties();
            apiUrl = properties.getProperty(API_URL_PROPERTY);
            if (apiUrl != null && !apiUrl.trim().isEmpty()) {
                LOGGER.log(Level.FINE, "API base URL found in properties file");
                return apiUrl;
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load configuration file, using default API URL", e);
            return DEFAULT_API_URL;
        }

        // If not found, return default
        LOGGER.log(Level.FINE, "Using default API base URL");
        return DEFAULT_API_URL;
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
