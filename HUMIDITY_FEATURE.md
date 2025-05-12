# Feature Specification: Display Humidity in Weather CLI

## 1. Overview

**Objective:**  
Enhance the Weather CLI application to display the current humidity for a specified city, alongside existing weather information.

**User Story:**  
_As a user, I want to see the current humidity percentage when I request the weather for a city, so I can better understand the atmospheric conditions._

---

## 2. API Support

The [OpenWeatherMap API](https://openweathermap.org/current) provides humidity data in its response:

```json
"main": {
    "temp": 284.2,
    "feels_like": 282.93,
    "temp_min": 283.06,
    "temp_max": 286.82,
    "pressure": 1021,
    "humidity": 60
}
```
- The `main.humidity` field represents humidity as a percentage.

---

## 3. Implementation Plan

### 3.1. Data Model

- **Update `WeatherData` class:**
  - Add a `private final int humidity;` field.
  - Update constructor, getter, and `toString()` to include humidity.

### 3.2. API Client

- **Update `OpenWeatherMapClient`:**
  - Parse the `humidity` value from the `main` JSON object.
  - Pass humidity to the `WeatherData` constructor.

### 3.3. Service Layer

- **Update `WeatherService` (if applicable):**
  - Ensure humidity is included in the returned `WeatherData`.

### 3.4. Output

- **Update `WeatherApp`:**
  - Display humidity in the CLI output, e.g.:
    ```
    Weather for London:
    Temperature: 15.5°C
    Conditions: scattered clouds
    Humidity: 60%
    ```

### 3.5. Testing

- **Update/Create Unit Tests:**
  - Test the new humidity field in `WeatherData`.
  - Test humidity parsing in `OpenWeatherMapClient`.
  - Update integration tests to check for humidity in output.

---

## 4. Backward Compatibility

- Ensure existing features (city, temperature, description) remain unchanged.
- If `humidity` is missing from the API response, handle gracefully (e.g., default to 0 or display "N/A").

---

## 5. Summary of Required Changes

| File/Class                       | Change Description                                 |
|-----------------------------------|----------------------------------------------------|
| `WeatherData.java`                | Add humidity field, getter, update constructor and `toString()` |
| `OpenWeatherMapClient.java`       | Parse humidity from JSON and pass to `WeatherData` |
| `WeatherApp.java`                 | Display humidity in output                         |
| `WeatherDataTest.java`            | Add/modify tests for humidity                      |
| `OpenWeatherMapClientTest.java`   | Add/modify tests for humidity parsing              |

---

## 6. Acceptance Criteria

- CLI output includes humidity percentage for a city.
- Unit tests cover the humidity field and its parsing.
- No regression in existing features or tests.

---

**References:**  
- [OpenWeatherMap API Docs – Current Weather Data](https://openweathermap.org/current)