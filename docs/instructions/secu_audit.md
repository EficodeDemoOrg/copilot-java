# AI Agent Security Audit Guidelines: Simple Weather CLI App

## 1. Introduction

**Purpose:** These guidelines direct an AI agent to perform a security audit of the Simple Weather CLI Java application.

**Project Context:** The application is a command-line tool written in Java, built with Maven. It takes a city name as input and fetches current weather data from the OpenWeatherMap API using an API key configured via an environment variable.

**Technology Stack:** Java, Maven, OpenWeatherMap API, JSON parsing library (likely Jackson or org.json), standard Java libraries.

## 2. Scope

The audit should focus on the application's source code (primarily within `src/main/java/com/weather/app/`), the Maven build configuration (`pom.xml`), and the documented configuration method (API key via environment variable).

## 3. Key Areas of Focus & Instructions

Perform checks based on the following areas. Reference specific files where applicable.

### 3.1. Input Validation (CLI Argument)

* **Analyze:** `WeatherApp.java` (specifically the `main` method or argument parsing logic).
* **Check:**
    * How is the command-line argument (`cityName`) processed before being used (e.g., in API calls)?
    * Is there any validation or sanitization applied to the `cityName` input?
    * Assess the risk of injection vulnerabilities (e.g., OS command injection, path traversal) based on how the input is used. *Note: Risk is likely low if input is only used in API call parameters, but confirm.*

### 3.2. Secrets Management (API Key Handling)

* **Analyze:** `ConfigUtil.java`, `OpenWeatherMapClient.java`, and any code involved in API key retrieval and usage.
* **Check:**
    * Confirm the OpenWeatherMap API key is obtained *exclusively* from the `OPENWEATHERMAP_API_KEY` environment variable as documented.
    * Verify the API key is **NOT** hardcoded anywhere in the source code (`.java` files, `pom.xml`, resource files).
    * Verify the API key is **NOT** logged at any log level.
    * Verify the API key is **NOT** written to any temporary or permanent files.
    * Assess if any intermediate handling could inadvertently expose the key (e.g., storing it in insecure variables, passing it unnecessarily).

### 3.3. External API Interaction Security

* **Analyze:** `OpenWeatherMapClient.java` (or the specific class making HTTP requests).
* **Check:**
    * **Protocol:** Confirm that all communication with the OpenWeatherMap API uses HTTPS.
    * **Endpoint:** Is the API base URL hardcoded? If so, note this (low risk here, but inflexibility).
    * **Error Handling (API):** How are HTTP error responses from the API handled? Specifically examine handling for:
        * `401 Unauthorized` / `403 Forbidden` (API key issues)
        * `404 Not Found` (Invalid city/endpoint)
        * `429 Too Many Requests` (Rate limiting)
        * `5xx Server Errors`
    * **Information Leakage:** Do error messages resulting from API failures reveal excessive detail about the external service or internal application state?
    * **Certificate Validation:** Does the HTTP client perform standard TLS/SSL certificate validation? (Assume standard Java client does unless custom configuration suggests otherwise).

### 3.4. Data Parsing and Handling Security

* **Analyze:** JSON parsing logic within `OpenWeatherMapClient.java` (identify the library used, e.g., Jackson, org.json).
* **Check:**
    * **Denial of Service (DoS):** Is the JSON parsing robust against potential DoS attacks via extremely large or deeply nested malicious JSON responses? Does the library offer any default protections or require specific configuration?
    * **Data Logging:** Is the raw or parsed JSON response from the API logged anywhere? This could inadvertently log sensitive information if the API response structure changes or contains unexpected data.
    * **Data Usage:** Is the data retrieved from the API used safely? (e.g., directly in output strings - generally safe here).

### 3.5. Dependency Security Analysis

* **Analyze:** `pom.xml`.
* **Check:**
    * List all direct and transitive dependencies declared in the `pom.xml`.
    * For each dependency, identify its version.
    * **Vulnerability Scanning:** Cross-reference the identified dependencies and versions against known vulnerability databases (e.g., NIST NVD, OWASP Dependency-Check database, Snyk DB). *Requires access to vulnerability data or tooling.*
    * Report any dependencies with known vulnerabilities, especially those rated MEDIUM severity or higher. Note the CVE identifier if available.

### 3.6. Error Handling and Information Leakage (Internal)

* **Analyze:** Exception handling (`try-catch` blocks, `WeatherApiException.java`) and logging across `WeatherApp.java`, `WeatherService.java`, `OpenWeatherMapClient.java`.
* **Check:**
    * Are custom exceptions (`WeatherApiException`) used appropriately without wrapping overly sensitive internal exceptions?
    * Do stack traces get printed directly to the console output intended for the user?
    * Do error messages logged internally contain sensitive data (e.g., configuration details beyond the API key, full file paths)?
    * Is logging configured appropriately (e.g., distinguishing between DEBUG, INFO, ERROR levels if a logging framework is used)?

### 3.7. General Secure Coding Practices (Java)

* **Analyze:** General Java code within `src/main/java/`.
* **Check:**
    * **Resource Management:** Are resources like network connections or file streams (if any were added) properly closed in `finally` blocks or using try-with-resources?
    * **Immutability:** Are data classes like `WeatherData` immutable where possible?
    * **Least Privilege:** Does any part of the code require elevated privileges unnecessarily? (Unlikely for this app).

## 4. Workflow

1.  **Analyze Code:** Systematically review the codebase (`src/main/java/com/weather/app/`), `pom.xml`, and related configuration details based on the instructions provided in Section 3 ("Key Areas of Focus & Instructions").
2.  **Generate Report:** Compile all findings from the analysis. Format these findings according to the specifications in Section 5 ("Output Format").
3.  **Create File:** Save the compiled findings into a new Markdown file named `secu_report.md` located in the root directory of the project workspace.

## 5. Output Format

Report findings clearly, categorized by the areas above (3.1 - 3.7). For each finding:

* **Description:** Briefly explain the potential vulnerability or weakness.
* **Location:** Specify the relevant file(s) and line number(s) where possible.
* **Severity:** Assign a qualitative severity (e.g., Critical, High, Medium, Low, Informational).
* **Recommendation:** Suggest specific mitigation steps or code changes.
