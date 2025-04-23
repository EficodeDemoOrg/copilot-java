package com.weather.app;

/**
 * Service class that acts as an intermediary between the application and the weather API
 */
public class WeatherService {
    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    /**
     * Gets weather data for the specified city
     * @param city The name of the city
     * @return WeatherData object containing the weather information
     * @throws WeatherApiException if there's an error fetching or parsing the weather data
     */
    public WeatherData getWeather(String city) throws WeatherApiException {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherApiException("City name cannot be empty");
        }
        
        return weatherApiClient.getWeatherFromApi(city);
    }
}
