package com.weather.app;

/**
 * Main entry point for the Weather Application
 */
public class WeatherApp {
    
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
            System.out.println("Usage: java -jar weather-app.jar <city-name>");
            System.out.println("Example: java -jar weather-app.jar London");
            exit(1);
            return;
        }

        // Get the city name from command line arguments
        String city = args[0];

        try {
            // Get API key from environment or config file
            String apiKey = ConfigUtil.getApiKey();
            
            // Initialize services
            WeatherApiClient weatherApiClient = new OpenWeatherMapClient(apiKey);
            WeatherService weatherService = new WeatherService(weatherApiClient);

            // Get and display weather data
            WeatherData weatherData = weatherService.getWeather(city);
            System.out.println(weatherData);
            
        } catch (ConfigUtil.ConfigException e) {
            System.err.println("Configuration error: " + e.getMessage());
            System.err.println("Please set the OPENWEATHERMAP_API_KEY environment variable or add api.key to config.properties");
            exit(1);
        } catch (WeatherApiException e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
            exit(1);
        }
    }
}
