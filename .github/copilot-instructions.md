# GitHub Copilot Instructions for Simple Weather CLI Application

This document provides essential guidelines and context for GitHub Copilot to generate accurate, consistent, and secure code, documentation, and tests for the Simple Weather CLI Java application.

## Project Overview

- **Purpose:** A command-line Java application that fetches current weather data from the OpenWeatherMap API.
- **Technology Stack:** Java 11+, Maven, OpenWeatherMap API, org.json library for JSON parsing, JUnit 5, Mockito for testing.

## Coding Standards

- **Java Version:** Java 11 or later.
- **Indentation:** 4 spaces, no tabs.
- **Line Length:** Maximum 120 characters.
- **Encoding:** UTF-8.
- **Braces:** Always use braces for control structures, even single-line statements.
- **Comments:** Use Javadoc (`/** ... */`) for public classes and methods. Inline comments (`//`) should be concise and meaningful.

## Naming Conventions

- **Classes:** PascalCase (e.g., `WeatherService`).
- **Methods and Variables:** camelCase (e.g., `getWeatherData`, `cityName`).
- **Constants:** UPPER_SNAKE_CASE (e.g., `API_URL`).
- **Packages:** Lowercase hierarchical naming (e.g., `com.weather.app`).

## Exception Handling

- Use specific custom exceptions (`WeatherApiException`, `ConfigUtil.ConfigException`) for clarity.
- Provide clear, user-friendly error messages.
- Avoid catching generic exceptions unless necessary.

## Logging

- Use `java.util.logging.Logger` for logging.+
- Instantiate logger with: `Logger.getLogger(ClassName.class.getName())`.
- Log exceptions at `SEVERE` level, informational messages at `INFO` level.
- Avoid using `System.out.println` or `System.err.println` for logging purposes.

## Security Practices

- **API Key Management:** 
  - Never hardcode API keys or sensitive data.
  - Retrieve API keys exclusively from environment variables (`OPENWEATHERMAP_API_KEY`) or secure configuration files.
  - Do not log or expose API keys in error messages or logs.

- **Input Validation:**
  - Always validate and sanitize user inputs (e.g., city names) before using them in API calls.
  - Clearly handle invalid or empty inputs with informative exceptions.

## External API Interaction

- Always use HTTPS for API calls.
- Handle HTTP errors explicitly (e.g., 401 Unauthorized, 404 Not Found, 429 Too Many Requests, 5xx Server Errors).
- Ensure proper JSON parsing with robust error handling.

## Testing Guidelines

- **Frameworks:** JUnit 5 for unit tests, Mockito for mocking dependencies.
- **Test Structure:** Mirror the main package structure under `src/test/java`.
- **Test Naming:** Test classes end with `Test` (e.g., `WeatherServiceTest`), methods clearly describe the scenario (e.g., `testGetWeatherValidCity`).
- **Coverage:** Ensure tests cover positive scenarios, negative scenarios, and exception handling.

## Dependency Management

- Managed via Maven (`pom.xml`).
- Avoid unnecessary dependencies.
- Regularly check dependencies for known vulnerabilities.

## Code Structure and Organization

- **Class Members Order:**
  1. Static fields
  2. Instance fields
  3. Constructors
  4. Public methods
  5. Protected methods
  6. Private methods

- **Immutability:** Prefer immutable data classes (e.g., `WeatherData`).

## Resource Management

- Always close resources (streams, connections) using try-with-resources or finally blocks.

## Documentation Generation

- Generate clear Javadoc comments for all public APIs.
- Include method purpose, parameters, return values, and exceptions clearly in Javadoc.

## Feature Implementation Workflow

When implementing new features or changes, follow this structured approach:

1. **Understand Requirements:** Clearly define the feature or change.
2. **Update Data Models:** Modify or create data classes as needed.
3. **Implement Logic:** Update service and client classes accordingly.
4. **Update CLI Output:** Ensure the command-line output clearly reflects changes.
5. **Write Tests:** Add comprehensive unit tests covering new functionality.
6. **Run Lint and Build:** Always run linting and build commands (`mvn clean package`) after changes. Fix any errors before submitting code.

## Commit Messages

- Follow Conventional Commits specification:
  - `feat:` for new features
  - `fix:` for bug fixes
  - `docs:` for documentation updates
  - `refactor:` for code refactoring
  - `test:` for adding or updating tests
  - `chore:` for maintenance tasks

## Common Pitfalls to Avoid

- Hardcoding sensitive information.
- Insufficient input validation.
- Ignoring API error responses.
- Logging sensitive data.
- Poor exception handling.

By adhering to these instructions, GitHub Copilot will generate code and documentation that aligns closely with the project's standards, ensuring maintainability, security, and clarity.
