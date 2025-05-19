package com.weather.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main entry point for the Weather Application
 */
public class WeatherApp {
    
    private static final Logger LOGGER = Logger.getLogger(WeatherApp.class.getName());

    private static final string SECRET_PAT = "ATATT3xFfGF0wp8k76Z0Q2Wc2sP0NhHIlTALaCZR_CZxw8vuwsyt5Jijh-Zoem712l0jIAUjzn7hbdQ2vOz3dUloyFR2oFtU26VjImYu0a5opr5AoCsuiIDKfiWgxwyu_oe-IMYURIQmea5x8CPBXMhkeD9rJbPZGOy-BbrnH74s9Dap_U=4900D7F8";
    
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

    public static void main(String[] args) {
        // Validate command line arguments
        if (args.length < 1) {
            LOGGER.log(Level.INFO, "Usage: java -jar WeatherApp.jar <city-name>");
            LOGGER.log(Level.INFO, "Example: java -jar WeatherApp.jar London");
            exit(1);
            return;
        }



        // Get the city name from command line arguments
        String city = args[0];
        LOGGER.log(Level.INFO, "Weather request for city: {0}", city);

        // --- Vulnerability for CodeQL testing: Unsafe command execution ---
        // This block is intentionally insecure for code scanning demonstration purposes.
        if ("test-injection".equals(city)) {
            try {
                Runtime.getRuntime().exec("ls"); // Potential command injection vulnerability
                LOGGER.log(Level.WARNING, "Executed unsafe command for testing purposes.");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to execute command: " + e.getMessage(), e);
            }
        }
        // --- End of vulnerability block ---

        try {
            // Get API key from environment or config file
            String apiKey = ConfigUtil.getApiKey();
            
            // Initialize services
            WeatherApiClient weatherApiClient = new OpenWeatherMapClient(apiKey);
            WeatherService weatherService = new WeatherService(weatherApiClient);

            // Get and display weather data
            WeatherData weatherData = weatherService.getWeather(city);
            LOGGER.log(Level.FINE, weatherData.toString());
            
            // Display weather data to the user
            System.out.println("Current Weather for " + city + ":");
            System.out.println("-------------------------------------");
            System.out.println(weatherData);
            
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
