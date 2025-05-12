# Code Style Guidelines for Simple Weather CLI Application

This document outlines the coding style and conventions used in the Simple Weather CLI application. These guidelines are derived from the existing codebase.

## General Guidelines

- **Language:** Java 11 or later.
- **Indentation:** Use 4 spaces for indentation. Do not use tabs.
- **Line Length:** Limit lines to 120 characters where possible.
- **File Encoding:** UTF-8.
- **Comments:**
  - Use Javadoc-style comments (`/** ... */`) for classes, methods, and public APIs.
  - Use `//` for inline comments.
  - Avoid redundant comments; ensure comments add value beyond what the code itself conveys.

## Naming Conventions

- **Classes:** Use PascalCase (e.g., `WeatherApp`, `WeatherService`).
- **Methods:** Use camelCase (e.g., `getWeather`, `setExitOnError`).
- **Variables:** Use camelCase (e.g., `cityName`, `apiKey`).
- **Constants:** Use UPPER_SNAKE_CASE (e.g., `API_URL`, `CACHE_DURATION_MS`).
- **Packages:** Use lowercase, hierarchical naming (e.g., `com.weather.app`).

## Class Structure

- **Order of Members:**
  1. Static fields
  2. Instance fields
  3. Constructors
  4. Public methods
  5. Protected methods
  6. Private methods

- **Modifiers:**
  - Use `private` for fields unless wider access is explicitly required.
  - Use `final` for fields that should not be reassigned.

## Exception Handling

- Use specific exception types (e.g., `WeatherApiException`, `ConfigUtil.ConfigException`).
- Provide meaningful error messages when throwing exceptions.
- Avoid catching generic `Exception` unless absolutely necessary.
- Log or print error messages to `System.err` for user-facing errors.

## Logging

- Use `System.out` for standard output and `System.err` for error messages.
- Avoid using `System.out.println` for debugging; use proper logging frameworks if needed in the future.

## Method Guidelines

- **Method Length:** Keep methods short and focused. A method should ideally fit within 20-30 lines.
- **Parameter Validation:** Validate method parameters at the beginning of the method (e.g., check for `null` or empty strings).
- **Return Values:**
  - Use meaningful return values.
  - Avoid returning `null` where possible; use `Optional` if applicable.

## Code Formatting

- **Braces:**
  - Use braces for all control structures, even single-line statements.
  - Place the opening brace on the same line as the control structure or method declaration.

  ```java
  if (condition) {
      // Do something
  } else {
      // Do something else
  }
  ```

- **Spacing:**
  - Add a single space before opening parentheses in control structures (e.g., `if (condition)`).
  - Do not add spaces between method names and parentheses (e.g., `getWeather(city)`).

## Dependency Management

- Use Maven for dependency management.
- Declare dependencies in `pom.xml` with specific versions.
- Avoid unnecessary dependencies.

## Testing

- Use JUnit 5 for unit tests.
- Use Mockito for mocking dependencies.
- Place test classes in the `src/test/java` directory, mirroring the package structure of the main code.
- Name test classes with the suffix `Test` (e.g., `WeatherServiceTest`).
- Use meaningful test method names (e.g., `testGetWeatherValidCity`).

## Security Practices

- Do not hardcode sensitive information (e.g., API keys).
- Use environment variables or configuration files for sensitive data.
- Validate and sanitize user inputs to prevent injection vulnerabilities.

## Additional Notes

- Follow the Single Responsibility Principle (SRP) for classes and methods.
- Use immutability where possible (e.g., `WeatherData` class).
- Close resources (e.g., streams) using try-with-resources or `finally` blocks.

By adhering to these guidelines, we ensure that the codebase remains clean, maintainable, and consistent.
