package com.weather.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// Import main application classes correctly
import com.weather.app.WeatherData;

/**
 * Unit tests for the WeatherData class
 */
public class WeatherDataTest {

    @Test
    public void testWeatherDataCreation() {
        WeatherData data = new WeatherData("London", 15.5, "Cloudy");
        
        assertEquals("London", data.getCity());
        assertEquals(15.5, data.getTemperatureCelsius(), 0.001);
        assertEquals("Cloudy", data.getDescription());
    }
    
    @Test
    public void testToString() {
        WeatherData data = new WeatherData("Berlin", 20.3, "Sunny");
        String result = data.toString();
        
        // Verify that the toString method contains all the required information
        assertTrue(result.contains("Berlin"));
        assertTrue(result.contains("20,3"));
        assertTrue(result.contains("Sunny"));
    }


}
