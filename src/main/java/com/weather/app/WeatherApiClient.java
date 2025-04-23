package com.weather.app;

/**
 * Interface defining the contract for fetching weather data from an API
 */
public interface WeatherApiClient {
    /**
     * Fetches weather data for the specified city
     * @param city The name of the city
     * @return WeatherData object containing the weather information
     * @throws WeatherApiException if there's an error fetching or parsing the weather data
     */
    WeatherData getWeatherFromApi(String city) throws WeatherApiException;
}
