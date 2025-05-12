# Simple Weather CLI Application

A command-line application written in Java that fetches the current weather information for a specified city using the [OpenWeatherMap API](https://openweathermap.org/api).

## Features

* Fetches current weather conditions (temperature, description).
* Accepts city name as a command-line argument.
* Displays weather information directly in the console with formatted output.
* Provides detailed logging information for debugging and monitoring.
* Uses Maven for dependency management and building.

## Prerequisites

Before you begin, ensure you have the following installed on your system (macOS instructions provided based on prior discussion):

1.  **Java Development Kit (JDK):** Version 11 or later is required.
    * Verify installation: `java -version`
    * Install using Homebrew (recommended): `brew install openjdk@17` (or `brew install openjdk` for latest)
    * Ensure `JAVA_HOME` is set correctly if installed manually.
2.  **Apache Maven:** Used for building the project and managing dependencies.
    * Verify installation: `mvn -version`
    * Install using Homebrew (recommended): `brew install maven`

## Setup

1.  **Clone the Repository:**
    ```bash
    git clone <your-repository-url>
    cd <repository-directory>
    ```

2.  **Get an OpenWeatherMap API Key:**
    * Sign up for a free API key at [OpenWeatherMap Free Tier](https://openweathermap.org/appid).

3.  **Configure API Key:**
    * This application requires the OpenWeatherMap API key to function. **Do not hardcode your API key in the source code.**
    * The recommended way is to set an environment variable named `OPENWEATHERMAP_API_KEY`.
    * In your terminal session (or add to your `~/.zshrc` or `~/.bash_profile` for persistence):
        ```bash
        export OPENWEATHERMAP_API_KEY="<Your_API_Key>"
        ```
    * Replace `<Your_API_Key>` with the actual key you obtained. The application code will read this environment variable.

## Building the Project

Maven is used to compile the source code, run tests, and package the application into an executable JAR file.

1.  **Compile and Package:**
    * Navigate to the project's root directory (where `pom.xml` is located) in your terminal.
    * Run the following Maven command:
        ```bash
        mvn clean package
        ```
    * This command will:
        * Clean previous builds.
        * Compile the source code.
        * Run unit tests (via Surefire plugin).
        * Package the application into an executable JAR file with all dependencies included (using the Assembly plugin).
    * The resulting JAR file will be located in the `target/` directory, typically named something like `weather-app-1.0-SNAPSHOT-jar-with-dependencies.jar`.

## Running the Application

Once the project is built, you can run the application from the command line using the packaged JAR file.

1.  **Execute the JAR:**
    * Make sure you are in the project's root directory.
    * Run the application using `java -jar`, providing the city name as an argument:

      ```sh
      java -jar target/weather-app-1.0-SNAPSHOT-jar-with-dependencies.jar <city-name>
      ```

      - `<city-name>`: The name of the city for which you want to fetch weather information. Use quotes if the city name contains spaces (e.g., "New York").

    * **Example:**

      ```sh
      java -jar target/weather-app-1.0-SNAPSHOT-jar-with-dependencies.jar Helsinki
      ```

    * The application will output the current weather information for the specified city or an error message if something goes wrong.

    * **Note:**
      - The application will attempt to read the OpenWeatherMap API key from the `OPENWEATHERMAP_API_KEY` environment variable or from the `src/main/resources/config.properties` file (property: `api.key`).
      - If neither is set, the application will exit with a configuration error.
      - The application will show detailed logging information alongside the weather output. See the **Logging** section under **Configuration** for instructions on adjusting verbosity.

## Running Tests

The project includes unit and integration tests.

1.  **Run Unit Tests Only:**
    * This executes tests that do not require network access or external dependencies (using mocks).
    * Command:
        ```bash
        mvn test
        ```

2.  **Run Unit and Integration Tests:**
    * This executes both unit tests and integration tests (which might hit the actual OpenWeatherMap API).
    * **Requires:** The `OPENWEATHERMAP_API_KEY` environment variable must be set correctly for integration tests to pass.
    * Command (uses the Failsafe plugin, typically bound to the `verify` phase):
        ```bash
        mvn verify
        ```

## Project Structure

The project follows standard Maven directory layout:

* `src/main/java`: Contains the main application source code.
    * `com.weather.app`: Contains all application classes:
        * `WeatherApp.java`: Main application class.
        * `WeatherService.java`: Service layer.
        * `WeatherApiClient.java`: API client interface.
        * `OpenWeatherMapClient.java`: OpenWeatherMap implementation of the API client.
        * `WeatherData.java`: Data model.
        * `WeatherApiException.java`: Custom exception class.
        * `ConfigUtil.java`: Configuration utility for API key management.
* `src/test/java`: Contains the test source code (JUnit/Mockito tests).
    * `test.java.com.weather.app`: Contains all test classes.
* `pom.xml`: Maven project configuration file (dependencies, plugins, build settings).
* `target/`: Directory where compiled code and packaged JARs are placed by Maven.

## Configuration

* **API Key:** The OpenWeatherMap API key is configured via the `OPENWEATHERMAP_API_KEY` environment variable (see Setup section).

* **Logging:** The application uses Java's built-in logging framework (`java.util.logging`).
    * Logs are displayed in the console with this format: `[timestamp] [log-level] class-name - message`
    * Log configuration is stored in `src/main/resources/logging.properties`.
    * To adjust logging verbosity, modify the `.level` property in `logging.properties`:
        ```properties
        # Set global logging level (OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL)
        .level=INFO
        ```
    * For example, to disable all logging, change it to `.level=OFF`
    * To see only errors, change to `.level=SEVERE`
    * Individual class logging levels can also be configured:
        ```properties
        com.weather.app.WeatherService.level=WARNING
        ```
    * After changing logging configuration, rebuild the application with `mvn clean package`.