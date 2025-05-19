package com.weather.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
// Import main application classes correctly
import com.weather.app.OpenWeatherMapClient;
import com.weather.app.WeatherData;
import com.weather.app.WeatherApiException;


/**
 * Unit tests for the OpenWeatherMapClient class
 */
public class OpenWeatherMapClientTest {

    private final String API_KEY = "test-api-key";
    private OpenWeatherMapClient client;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockResponse;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockHttpClient = mock(HttpClient.class);
        mockResponse = mock(HttpResponse.class);
        
        // Create client with custom implementation
        client = new OpenWeatherMapClient(API_KEY, "https://test-api.example.com/weather") {
            // Custom implementation to return mock HttpClient
            protected HttpClient createHttpClient() {
                return mockHttpClient;
            }
        };
    }

    @Test
    public void testGetWeatherFromApiSuccess() throws Exception {
        // Arrange
        String city = "London";
        String jsonResponse = "{"
            + "\"name\":\"London\","
            + "\"main\":{\"temp\":15.5},"
            + "\"weather\":[{\"description\":\"scattered clouds\"}]"
            + "}";
        
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        
        // Act
        WeatherData result = client.getWeatherFromApi(city);
        
        // Assert
        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertEquals(15.5, result.getTemperatureCelsius(), 0.001);
        assertEquals("scattered clouds", result.getDescription());
        
        // Verify that the HTTP request was sent
        verify(mockHttpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
    
    @Test
    public void testGetWeatherFromApiEmptyCity() {
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class,
            () -> client.getWeatherFromApi(""));
        
        assertTrue(exception.getMessage().contains("cannot be empty"));
        
        // Verify no HTTP request was made
        verifyNoInteractions(mockHttpClient);
    }
    
    @Test
    public void testGetWeatherFromApiHttpError() throws Exception {
        // Arrange
        String city = "InvalidCity";
        
        when(mockResponse.statusCode()).thenReturn(404);
        when(mockResponse.body()).thenReturn("Not found");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class,
            () -> client.getWeatherFromApi(city));
        
        assertTrue(exception.getMessage().contains("City not found"));
        
        // Verify that the HTTP request was sent
        verify(mockHttpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
    
    @Test
    public void testGetWeatherFromApiNetworkError() throws Exception {
        // Arrange
        String city = "London";
        IOException ioException = new IOException("Network error");
        
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenThrow(ioException);
        
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class,
            () -> client.getWeatherFromApi(city));
        
        assertTrue(exception.getMessage().contains("Network error"));
        assertEquals(ioException, exception.getCause());
        
        // Verify that the HTTP request was sent
        verify(mockHttpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
    
    @Test
    public void testGetWeatherFromApiInvalidJson() throws Exception {
        // Arrange
        String city = "London";
        String invalidJson = "{invalid-json}";
        
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(invalidJson);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        
        // Act & Assert
        WeatherApiException exception = assertThrows(WeatherApiException.class,
            () -> client.getWeatherFromApi(city));
        
        assertTrue(exception.getMessage().contains("parsing weather data"));
        
        // Verify that the HTTP request was sent
        verify(mockHttpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
    
    @Test
    public void testConstructorWithEmptyApiKey() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new OpenWeatherMapClient(""));
        
        // Verify that an appropriate error message is provided
        assertTrue(exception.getMessage().contains("API key cannot be null or empty"));
    }
    
    @Test
    public void testConstructorWithNullApiKey() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new OpenWeatherMapClient(null));
        
        // Verify that an appropriate error message is provided
        assertTrue(exception.getMessage().contains("API key cannot be null or empty"));
    }
    
    @Test
    public void testGetWeatherFromApiInvalidCityFormat() {
        // Act & Assert - test with a city name containing invalid characters
        WeatherApiException exception = assertThrows(WeatherApiException.class,
            () -> client.getWeatherFromApi("London<script>alert('xss')</script>"));
        
        // Verify that an appropriate error message is provided
        assertTrue(exception.getMessage().contains("Invalid city name format"));
        
        // Verify no HTTP request was made
        verifyNoInteractions(mockHttpClient);
    }
}
