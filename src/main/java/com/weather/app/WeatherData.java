package com.weather.app;

/**
 * Represents weather data for a specific city
 */
public class WeatherData {
    private final String city;
    private final double temperatureCelsius;
    private final String description;

    public WeatherData(String city, double temperatureCelsius, String description) {
        this.city = city;
        this.temperatureCelsius = temperatureCelsius;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Weather for %s:%n" +
                "Temperature: %.1f°C%n" +
                "Conditions: %s", 
                city, temperatureCelsius, description);
    }
}
