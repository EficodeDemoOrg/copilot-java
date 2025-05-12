# Technical Specification: Security Fixes for Simple Weather CLI

## 1. Overview

**Objective:** Implement the recommendations from the security audit report (`secu_report.md`) to enhance the security posture of the Simple Weather CLI application.

**Scope:** This specification covers changes related to input validation, configuration management, error handling, logging, and dependency management.

---

## 2. Detailed Implementation Tasks

### 2.1. Input Validation (Ref: Finding 1.1)

*   **File:** `OpenWeatherMapClient.java` (potentially `WeatherService.java` as well)
*   **Task:** Implement stricter input validation for the `city` parameter beyond just checking for null/empty.
    *   **Option A (Allow-list):** Define a regex pattern for acceptable city names (e.g., alphanumeric characters, spaces, hyphens) and validate the input against it before using it in the API URL. Throw `WeatherApiException` if validation fails.
    *   **Option B (Sanitization):** While URL encoding provides some safety, explicitly sanitize the input to remove or escape potentially harmful characters *before* URL encoding happens, if deemed necessary after further risk assessment. (Note: Current risk assessed as low).
*   **Goal:** Reduce potential risks if the input source or usage context changes.

### 2.2. Configuration Management (Ref: Finding 3.1)

*   **Files:** `ConfigUtil.java`, `OpenWeatherMapClient.java`, `src/main/resources/config.properties`
*   **Task:** Make the OpenWeatherMap API base URL configurable instead of hardcoding it.
    *   Add a new property `api.base.url` to `config.properties` with the default value `https://api.openweathermap.org/data/2.5/weather`.
    *   Update `ConfigUtil.java` to load this property, potentially alongside the API key, providing a method like `getApiBaseUrl()`. Add an environment variable fallback (e.g., `OPENWEATHERMAP_API_URL`).
    *   Modify `OpenWeatherMapClient.java` to retrieve the base URL from `ConfigUtil` instead of using the hardcoded `API_URL` constant. Update the constructor to accept the base URL or fetch it internally.
*   **Goal:** Increase flexibility and adhere to configuration best practices.

### 2.3. Error Handling - API Response Leakage (Ref: Finding 3.2)

*   **File:** `OpenWeatherMapClient.java`
*   **Task:** Modify the `handleErrorResponse` method to prevent leaking raw API response bodies in generic error messages.
    *   In the `else` block (for unhandled status codes), change the `WeatherApiException` message to something generic like `"API error: Received HTTP status code " + statusCode`.
    *   **(Optional but Recommended):** Add internal logging (using `java.util.logging`, see Task 2.4) within this block at `WARNING` or `SEVERE` level to log the full response body for debugging purposes, ensuring the API key is not logged.
*   **Goal:** Avoid exposing potentially sensitive details from unexpected API error responses to the end-user or in less secure logs.

### 2.4. Logging Implementation (Ref: Finding 6.1)

*   **Files:** `WeatherApp.java`, `OpenWeatherMapClient.java`, `ConfigUtil.java`, `WeatherService.java` (and potentially others)
*   **Task:** Replace direct usage of `System.out.println` and `System.err.println` for application status and errors with `java.util.logging.Logger`.
    *   In each relevant class, add a static final logger instance: `private static final Logger LOGGER = Logger.getLogger(ClassName.class.getName());`.
    *   Replace `System.out.println(weatherData)` in `WeatherApp.main` with `LOGGER.log(Level.INFO, weatherData.toString());`.
    *   Replace `System.err.println(...)` calls in `WeatherApp.main` catch blocks with `LOGGER.log(Level.SEVERE, "Error message", exception);`.
    *   Add informative logging within `OpenWeatherMapClient` (e.g., logging the URL being called at `INFO` or `FINE` level, logging specific handled errors like 401/404 at `WARNING`, logging the generic error body internally as per Task 2.3).
    *   Configure basic logging format/level if necessary (can be done programmatically or via a `logging.properties` file).
*   **Goal:** Implement standard logging practices for better control over log output and severity levels.

### 2.5. Dependency Management (Ref: Findings 5.1, 5.2)

*   **File:** `pom.xml`
*   **Task:** Perform dependency vulnerability scanning and update dependencies.
    *   Integrate and run a Maven dependency check plugin (e.g., `dependency-check-maven`).
    *   Analyze the report for vulnerabilities in `org.json:json:20231013` and `org.mockito:mockito-core:5.3.1` (and other dependencies).
    *   If vulnerabilities are confirmed, update the versions in `pom.xml` to the latest non-vulnerable releases compatible with the project (Java 11).
    *   Re-run `mvn clean package` and `mvn test` to ensure compatibility after updates.
*   **Goal:** Mitigate risks associated with known vulnerabilities in third-party libraries.

---

## 3. Acceptance Criteria

*   City name input undergoes explicit validation or sanitization before being used in API calls.
*   The OpenWeatherMap API base URL is configurable via `config.properties` and/or an environment variable.
*   Generic API error messages (`handleErrorResponse` else block) no longer contain the raw response body.
*   `System.out` and `System.err` calls for application logic/errors are replaced with `java.util.logging.Logger`.
*   A dependency vulnerability scan has been performed.
*   Vulnerable dependencies (if found and fixable) have been updated in `pom.xml`.
*   All existing unit tests pass after the changes.
*   The application builds successfully (`mvn clean package`).
*   The application runs correctly for valid inputs and handles errors gracefully according to the updated logic.
