package com.weather.app;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Implementation of WeatherApiClient that fetches data from OpenWeatherMap API
 */
public class OpenWeatherMapClient implements WeatherApiClient {
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey;
    private final HttpClient httpClient;

    public OpenWeatherMapClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = createHttpClient();
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

        try {
            String url = buildApiUrl(city);
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
        return String.format("%s?q=%s&appid=%s&units=metric", API_URL, city, apiKey);
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
            throw new WeatherApiException("Invalid API key");
        } else if (statusCode == 404) {
            throw new WeatherApiException("City not found");
        } else if (statusCode == 429) {
            throw new WeatherApiException("Rate limit exceeded");
        } else {
            throw new WeatherApiException("API error: HTTP " + statusCode + " - " + body);
        }
    }
}
