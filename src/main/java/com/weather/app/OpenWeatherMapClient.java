package com.weather.app;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Implementation of WeatherApiClient that fetches data from OpenWeatherMap API
 */
public class OpenWeatherMapClient implements WeatherApiClient {
    private static final Logger LOGGER = Logger.getLogger(OpenWeatherMapClient.class.getName());
    // Pattern for validating city names: allows letters, numbers, spaces, hyphens, and periods
    private static final Pattern VALID_CITY_PATTERN = Pattern.compile("^[\\p{L}\\p{N}\\s\\-\\.]+$");
    
    private final String apiKey;
    private final String apiBaseUrl;
    private final HttpClient httpClient;

    public OpenWeatherMapClient(String apiKey) {
        this(apiKey, ConfigUtil.getApiBaseUrl());
    }
    
    public OpenWeatherMapClient(String apiKey, String apiBaseUrl) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "API key cannot be null or empty");
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        
        this.apiKey = apiKey;
        this.apiBaseUrl = apiBaseUrl;
        this.httpClient = createHttpClient();
        LOGGER.log(Level.CONFIG, "OpenWeatherMapClient initialized with API URL: {0}", 
                this.apiBaseUrl);
    }
    
    /**
     * Creates an HTTP client. Extracted as a protected method to allow overriding in tests.
     * @return A new HttpClient instance
     */
    protected HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }

    @Override
    public WeatherData getWeatherFromApi(String city) throws WeatherApiException {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherApiException("City name cannot be empty");
        }
        
        // Validate city name format
        String trimmedCity = city.trim();
        if (!VALID_CITY_PATTERN.matcher(trimmedCity).matches()) {
            LOGGER.log(Level.WARNING, "Invalid city name format: {0}", trimmedCity);
            throw new WeatherApiException("Invalid city name format. City names should only contain letters, numbers, spaces, hyphens, and periods");
        }
        
        LOGGER.log(Level.FINE, "Fetching weather for city: {0}", trimmedCity);
        
        try {
            String url = buildApiUrl(trimmedCity);
            LOGGER.log(Level.FINE, "API request URL: {0}", url.replaceAll("appid=[^&]+", "appid=REDACTED"));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                handleErrorResponse(response);
            }

            return parseJsonResponse(response.body());
        } catch (URISyntaxException e) {
            throw new WeatherApiException("Invalid URL format", e);
        } catch (IOException e) {
            throw new WeatherApiException("Network error while fetching weather data", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherApiException("Weather data fetch was interrupted", e);
        }
    }

    private String buildApiUrl(String city) {
        return String.format("%s?q=%s&appid=%s&units=metric", apiBaseUrl, city, apiKey);
    }

    private WeatherData parseJsonResponse(String jsonResponse) throws WeatherApiException {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            
            // Get city name
            String cityName = json.getString("name");
            
            // Get temperature
            JSONObject main = json.getJSONObject("main");
            double temperature = main.getDouble("temp");
            
            // Get weather description
            String description = json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");
            
            return new WeatherData(cityName, temperature, description);
        } catch (Exception e) {
            throw new WeatherApiException("Error parsing weather data", e);
        }
    }

    private void handleErrorResponse(HttpResponse<String> response) throws WeatherApiException {
        int statusCode = response.statusCode();
        String body = response.body();
        
        if (statusCode == 401) {
            LOGGER.log(Level.WARNING, "Authentication failed with status code 401. Response: {0}", body);
            throw new WeatherApiException("Invalid API key");
        } else if (statusCode == 404) {
            LOGGER.log(Level.WARNING, "Resource not found with status code 404. Response: {0}", body);
            throw new WeatherApiException("City not found");
        } else if (statusCode == 429) {
            LOGGER.log(Level.WARNING, "Rate limit exceeded with status code 429. Response: {0}", body);
            throw new WeatherApiException("Rate limit exceeded");
        } else {
            // Log the full response body for debugging but don't expose it in the exception message
            LOGGER.log(Level.SEVERE, "Unexpected API error with status code {0}. Response: {1}", 
                    new Object[]{statusCode, body});
            throw new WeatherApiException("API error: Received HTTP status code " + statusCode);
        }
    }
}
