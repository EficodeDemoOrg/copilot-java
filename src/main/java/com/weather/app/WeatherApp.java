package com.weather.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main entry point for the Weather Application
 */
public class WeatherApp {

    private static final Logger LOGGER = Logger.getLogger(WeatherApp.class.getName());

    // Initialize logging configuration
    static {
        try (InputStream is = WeatherApp.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (is != null) {
                LogManager.getLogManager().readConfiguration(is);
                LOGGER.log(Level.CONFIG, "Logging configured from properties file");
            } else {
                LOGGER.log(Level.WARNING, "Unable to find logging.properties file, using default configuration");
            }
        } catch (IOException e) {
            System.err.println("Could not load logging.properties file");
            e.printStackTrace();
        }
    }

    // Flag to control System.exit behavior (for testing)
    private static boolean exitOnError = true;

    /**
     * Set whether the application should exit on error.
     * This method is primarily used for testing.
     *
     * @param shouldExit true if the application should exit on error, false otherwise
     */
    public static void setExitOnError(boolean shouldExit) {
        exitOnError = shouldExit;
    }

    /**
     * Exit the application with the given status code if exitOnError is true.
     *
     * @param status the exit status code
     * @return true if the application would exit (for testing)
     */
    private static boolean exit(int status) {
        if (exitOnError) {
            System.exit(status);
        }
        return true;
    }

    /**
     * Load ASCII art from a resource file
     *
     * @param filename the name of the art file to load
     * @return the ASCII art as a string, or empty string if not found
     */
    private static String loadAsciiArt(String filename) {
        StringBuilder art = new StringBuilder();
        try (InputStream is = WeatherApp.class.getClassLoader().getResourceAsStream("art/" + filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                art.append(line).append(System.lineSeparator());
            }
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.FINE, "Could not load ASCII art file: " + filename, e);
        }
        return art.toString();
    }

    /**
     * Display weather data with ASCII sun art
     *
     * @param weatherData the weather data to display
     */
    private static void displayWeatherWithArt(WeatherData weatherData) {
        System.out.println();
        System.out.println(loadAsciiArt("sun.txt"));
        System.out.println("Current Weather for " + weatherData.getCity() + ":");
        System.out.println("========================================");
        System.out.println(weatherData);
        System.out.println();
    }

    public static void main(String[] args) {
        // Validate command line arguments
        if (args.length < 1) {
            System.out.println("Usage: java -jar WeatherApp.jar <city-name>");
            System.out.println("Example: java -jar WeatherApp.jar London");
            exit(1);
            return;
        }

        // Get the city name from command line arguments
        String city = args[0];
        LOGGER.log(Level.FINE, "Weather request for city: {0}", city);

        try {
            // Get API key from environment or config file
            String apiKey = ConfigUtil.getApiKey();

            // Initialize services
            WeatherApiClient weatherApiClient = new OpenWeatherMapClient(apiKey);
            WeatherService weatherService = new WeatherService(weatherApiClient);

            // Get and display weather data
            WeatherData weatherData = weatherService.getWeather(city);
            LOGGER.log(Level.FINE, weatherData.toString());

            // Display weather data to the user with ASCII art
            displayWeatherWithArt(weatherData);

        } catch (ConfigUtil.ConfigException e) {
            LOGGER.log(Level.SEVERE, "Configuration error: " + e.getMessage(), e);
            LOGGER.log(Level.SEVERE,
                    "Please set the OPENWEATHERMAP_API_KEY environment variable or add api.key to config.properties");
            exit(1);
        } catch (WeatherApiException e) {
            LOGGER.log(Level.SEVERE, "Error fetching weather data: " + e.getMessage(), e);
            exit(1);
        }
    }
}
