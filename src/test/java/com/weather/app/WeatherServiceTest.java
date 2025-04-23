package test.java.com.weather.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
// Import main application classes correctly
import com.weather.app.WeatherApiClient;
import com.weather.app.WeatherData;
import com.weather.app.WeatherApiException;
import com.weather.app.WeatherService;


/**
 * Unit tests for the WeatherService class
 */
public class WeatherServiceTest {

    private WeatherApiClient mockApiClient;
    private WeatherService weatherService;
    
    @BeforeEach
    public void setUp() {
        // Create a mock for the WeatherApiClient
        mockApiClient = mock(WeatherApiClient.class);
        weatherService = new WeatherService(mockApiClient);
    }
    
    @Test
    public void testGetWeatherValidCity() throws WeatherApiException {
        // Arrange
        String city = "Paris";
        WeatherData expectedData = new WeatherData("Paris", 22.5, "Clear");
        when(mockApiClient.getWeatherFromApi(city)).thenReturn(expectedData);
        
        // Act
        WeatherData actualData = weatherService.getWeather(city);
        
        // Assert
        assertEquals(expectedData, actualData);
        verify(mockApiClient).getWeatherFromApi(city);
    }
    
    @Test
    public void testGetWeatherEmptyCity() throws WeatherApiException {
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class, 
            () -> weatherService.getWeather(""));
        
        assertTrue(exception.getMessage().contains("cannot be empty"));
        // Verify that the API client was never called
        verify(mockApiClient, never()).getWeatherFromApi(any());
    }
    
    @Test
    public void testGetWeatherNullCity() throws WeatherApiException {
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class, 
            () -> weatherService.getWeather(null));
        
        assertTrue(exception.getMessage().contains("cannot be empty"));
        // Verify that the API client was never called
        verify(mockApiClient, never()).getWeatherFromApi(any());
    }
    
    @Test
    public void testGetWeatherApiException() throws WeatherApiException {
        // Arrange
        String city = "Error";
        String errorMessage = "API Error";
        when(mockApiClient.getWeatherFromApi(city)).thenThrow(
            new WeatherApiException(errorMessage));
        
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class,
            () -> weatherService.getWeather(city));
        
        assertEquals(errorMessage, exception.getMessage());
        verify(mockApiClient).getWeatherFromApi(city);
    }
}
