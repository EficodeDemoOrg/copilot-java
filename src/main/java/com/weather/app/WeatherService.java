package com.weather.app;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class that acts as an intermediary between the application and the weather API
 */
public class WeatherService {
    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getName());
    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
        LOGGER.log(Level.FINE, "WeatherService initialized");
    }

    /**
     * Gets weather data for the specified city
     * @param city The name of the city
     * @return WeatherData object containing the weather information
     * @throws WeatherApiException if there's an error fetching or parsing the weather data
     */
    public WeatherData getWeather(String city) throws WeatherApiException {
        LOGGER.log(Level.INFO, "Getting weather data for city: {0}", city);

        if (city == null || city.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "City name cannot be empty");
            throw new WeatherApiException("City name cannot be empty");
        }
        
        try {
            WeatherData weatherData = weatherApiClient.getWeatherFromApi(city);
            LOGGER.log(Level.INFO, "Successfully retrieved weather data. Temperature: {0}°C",
                    weatherData.getTemperatureCelsius());
            return weatherData;
        } catch (WeatherApiException e) {
            LOGGER.log(Level.SEVERE, "Error fetching weather data for city: " + city, e);
            throw e;
        }
    }
}

