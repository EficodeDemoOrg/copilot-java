## Simple Weather Info Fetcher

This document provides information for building a command-line application to fetch weather information for a given city using Maven.

**1. Core Functionality**

* The application should accept a city name as a command-line argument.
* It should fetch current weather data for the given city using the OpenWeatherMap API.
* The fetched data should include:
    * City name
    * Current temperature (e.g., in Celsius)
    * Weather description (e.g., "Sunny", "Cloudy", "Rainy")
* The application should display the fetched weather information in a user-friendly format on the console.

**2. Architecture**

* **WeatherApp:** The main entry point of the application (`main` method).
    * Handles command-line argument parsing (expecting one argument: the city name).
    * Creates instances of `WeatherService` (which internally uses a `WeatherApiClient`).
    * Calls the `WeatherService` to get weather data.
    * Displays the weather information or error messages to the console.
* **WeatherService:**
    * Holds a reference to a `WeatherApiClient` instance (injected or created).
    * `getWeather(String city)`: Fetches weather data by delegating to the `WeatherApiClient`. Performs basic validation (e.g., non-empty city name).
* **WeatherApiClient (Interface):** Defines the contract for fetching weather data.
    * `getWeatherFromApi(String city)`: Method signature for fetching data.
* **OpenWeatherMapClient (Implementation):** Implements `WeatherApiClient`.
    * Requires an API key (should be configurable, not hardcoded).
    * `getWeatherFromApi(String city)`:
        * Constructs the correct OpenWeatherMap API URL using the city and API key.
        * Makes an HTTP GET request to the API URL.
        * Handles HTTP responses (success, errors).
        * Parses the JSON response from the API.
        * Creates and returns a `WeatherData` object upon success, or handles/throws exceptions on failure.
* **WeatherData:**
    * A simple POJO (Plain Old Java Object) / record to hold the fetched weather data: city name (String), temperature (double), and description (String).
    * Should include appropriate getters and potentially a `toString()` method for easy display.

**3. Implementation Details**

* **Language:** Java (Recommend Java 11 or later for `HttpClient`).
* **Build Tool / Dependency Management:** **Maven**. A `pom.xml` file will be needed to manage dependencies and build the project.
* **Libraries (Managed by Maven):**
    * **For HTTP requests:**
        * `java.net.http.HttpClient` (Built-in with Java 11+, no extra dependency needed *unless* targeting older Java).
        * *Alternative:* Apache HttpClient (`org.apache.httpcomponents.client5:httpclient5`) if preferred or needed for older Java versions.
    * **For JSON parsing (Choose one):**
        * `org.json:json` (Simple, good for basic parsing).
        * Jackson (`com.fasterxml.jackson.core:jackson-databind`) (Powerful, feature-rich, common standard).
        * Gson (`com.google.code.gson:gson`) (Another popular choice from Google).
* **Error Handling:**
    * Implement try-catch blocks for potential `IOException` during HTTP requests, `InterruptedException`, `URISyntaxException`, and JSON parsing errors.
    * Provide user-friendly error messages for:
        * Missing or invalid command-line arguments.
        * Invalid city names recognized by the API.
        * Network issues (e.g., no internet connection).
        * API connection errors (e.g., invalid API key, rate limits, server errors). Return specific error codes or messages if possible.
* **Command-Line Arguments:**
    * Check `args.length` in the `main` method. If no argument is provided, print usage instructions and exit.

**4. Additional Considerations**

* **API Key:**
    * Obtain a free API key from [OpenWeatherMap](https://openweathermap.org/appid).
    * **Crucial:** Do *not* hardcode the API key directly in the source code. Pass it via:
        * Environment variable (Recommended).
        * Configuration file (e.g., `config.properties`).
        * Command-line argument (Less secure for keys).
* **Temperature Units:** The OpenWeatherMap API defaults to Kelvin. Ensure the code converts the temperature to Celsius (or Fahrenheit, if preferred) before displaying it. The API usually supports specifying units via a query parameter (e.g., `&units=metric` for Celsius).
* **Packaging:** Configure Maven (e.g., using `maven-assembly-plugin` or `maven-shade-plugin`) to create an executable JAR file that includes all necessary dependencies, making it easy to run the application.