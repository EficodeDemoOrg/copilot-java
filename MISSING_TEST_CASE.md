# Missing Test Case: Handling Null or Missing API Key

## Description

The application should handle the scenario where the OpenWeatherMap API key is missing (null or empty string). If the API key is not provided, the application must not attempt to make an API call and should throw a clear exception (e.g., `ConfigUtil.ConfigException` or `WeatherApiException`) with an informative error message.

## Expected Inputs and Outputs

- **Input:**  
  - API key is `null` or an empty string.
  - City name is a valid string (e.g., `"London"`).

- **Expected Output:**  
  - The application throws a `WeatherApiException` (or `ConfigUtil.ConfigException` if tested at config level).
  - The exception message should clearly indicate that the API key is missing or invalid.
  - No HTTP request should be made to the OpenWeatherMap API.

## Example Test Case

**Reference:**  
Add the following test to `/src/test/java/com/weather/app/WeatherServiceTest.java` after the last test method (see lines similar to those for other exception tests):

```java
@Test
public void testGetWeatherWithMissingApiKey() {
    // Arrange: Create a client with a null or empty API key
    WeatherApiClient client = new OpenWeatherMapClient("");
    WeatherService service = new WeatherService(client);

    // Act & Assert: Expect an exception when fetching weather
    WeatherApiException exception = assertThrows(WeatherApiException.class, () -> {
        service.getWeather("London");
    });
    assertTrue(exception.getMessage().toLowerCase().contains("api key"));
}
```

- Place this after the last test in `WeatherServiceTest.java` (see lines similar to 61–73 in the current file).

## Rationale

- Prevents accidental API calls with an invalid key.
- Ensures user receives a clear error message about missing configuration.
- Improves robustness and user experience.
