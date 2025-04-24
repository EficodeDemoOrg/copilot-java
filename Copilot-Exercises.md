# GitHub Copilot Exercises: Simple Weather CLI App (Revised)

This document provides a series of exercises designed to help you learn and practice using GitHub Copilot features within the context of the Simple Weather CLI Java application. We will cover exploring the codebase, ideating new features, and implementing them using Copilot's capabilities.

**Key Copilot Interaction Points:**

* **Chat View:** Used for asking questions, generating code/tests/docs, and initiating actions. Modes like "Ask" (default), "Edits", and "Agent" might be selectable via a dropdown menu within the Chat view interface itself.
* **Inline Chat:** Quick chat directly in the editor (Default: `Cmd+I` / `Ctrl+I`), often used for quick explanations or edits on selected code.
* **Participants (`@` references):** Used to bring specific, broad contexts into the chat, such as the entire workspace (`@workspace`) or the VS Code environment itself (`@vscode`). Note: `@terminal` is generally *not* used as a prefix for terminal-related variables.
* **Variables (`#` references):** Used to provide more granular context to Copilot (e.g., specific files `#file`, selections `#selection`, symbols `#sym`, changes `#changes`, problems `#problems`, codebase structure `#codebase`, web content `#fetch`, last terminal command `#terminalLastCommand`, terminal selection `#terminalSelection`).
* **Slash Commands:** Used within the Chat view or inline chat to direct Copilot's actions (e.g., `/explain`, `/tests`, `/fix`, `/new`).
* **Code Completion:** Automatic suggestions as you type.
* **Custom Instructions:** Files like `.github/copilot-instructions.md` can guide Copilot's suggestions for the workspace.

**Note on `@workspace` vs `#codebase`:**

Both `@workspace` and `#codebase` participants aim to provide Copilot with context about your entire project or workspace files. Historically, `@workspace` was commonly used. You might observe that `#codebase` is particularly effective or required when using specific modes like "Edits" or "Agent" (`/new`), while `@workspace` might function primarily in the standard "Ask" mode. While their underlying goal seems similar (providing broad project context), their availability or effectiveness might differ depending on the specific Copilot feature or mode you are using. These exercises will use `@workspace` for initial broad questions and `#codebase` where broad context is needed for generation or analysis tasks linked to specific modes, reflecting this potential distinction.

**Prerequisites:**

* Visual Studio Code installed.
* GitHub Copilot and Copilot Chat extensions installed and configured.
* The Simple Weather CLI project opened in VS Code.
* An integrated terminal open within VS Code (e.g., View > Terminal).
* Basic understanding of Java and Maven.
* An OpenWeatherMap API key set as the `OPENWEATHERMAP_API_KEY` environment variable (required for some implementation/testing steps).

---

## Section 1: Explore the Codebase and Environment

**Goal:** Use Copilot Chat with various context providers (`@workspace`, `#file`, `#folder`, `#sym`, `#fetch`, `#terminalLastCommand`, `#terminalSelection`, `@vscode`) to quickly understand the project, its dependencies, the development environment, and external information.

---

### Exercise 1.1: Project Overview (`@workspace`, `/explain`)

* **Purpose:** To get a high-level understanding of the project's goals, main components, and structure using the broad workspace context.
* **Aim:** Practice using the `@workspace` participant in Copilot Chat for broad project questions in "Ask" mode.
* **Steps:**
    1.  Open the Copilot Chat view in VS Code. Ensure the mode is "Ask".
    2.  In the chat input, type the following prompt and press Enter:
        ```
        @workspace /explain What is the main purpose of this project and how is it structured? What are the key Java classes involved according to the source code and README?
        ```
    3.  Review Copilot's explanation.

### Exercise 1.2: Understanding a Specific Class (`#file`, `/explain`)

* **Purpose:** To dive deeper into the functionality of a single class.
* **Aim:** Practice using the `#file` variable.
* **Steps:**
    1.  Open the file `src/main/java/com/weather/app/OpenWeatherMapClient.java` in the editor.
    2.  Open the Copilot Chat view.
    3.  Type the following prompt:
        ```
        #file:src/main/java/com/weather/app/OpenWeatherMapClient.java /explain Explain the role of this class. How does it fetch data from the OpenWeatherMap API? What dependencies does it seem to have?
        ```
    4.  Analyze the response.

### Exercise 1.3: Explaining Dependencies (`#file`, `/explain`)

* **Purpose:** To understand the external libraries.
* **Aim:** Practice using `#file` with `pom.xml`.
* **Steps:**
    1.  Open the `pom.xml` file.
    2.  In the Copilot Chat view, type:
        ```
        #file:pom.xml /explain Explain the roles of the main dependencies listed here, such as Jackson, JUnit, Mockito, and any relevant Maven plugins like Surefire or Assembly.
        ```
    3.  Review the explanation.

### Exercise 1.4: Generating Documentation (`#selection`)

* **Purpose:** To automatically generate documentation.
* **Aim:** Practice using the `#selection` variable for editor content.
* **Steps:**
    1.  Open the file `src/main/java/com/weather/app/WeatherService.java`.
    2.  Locate and select the entire method signature and body of the `getWeather(String city)` method.
    3.  Open the Copilot Chat view.
    4.  Ensure the mode is set to "Ask" (the default).
    5.  Type the following prompt:
        ```
        #selection Generate Javadoc documentation for the selected method. Explain its purpose, parameters, return value, and potential exceptions based on the code.
        ```
    6.  Copilot should provide the Javadoc. Review it and potentially copy it into your code.

### Exercise 1.5: Explore Folder Contents (`#folder`, `/explain`)

* **Purpose:** To get a summary of the code within a directory.
* **Aim:** Practice using the `#folder` variable.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt:
        ```
        #folder:src/main/java/com/weather/app /explain Summarize the purpose of the Java classes inside this application package.
        ```
    3.  Review Copilot's summary of the classes like `WeatherApp`, `WeatherService`, `OpenWeatherMapClient`, etc.

### Exercise 1.6: Explore a Specific Symbol (`#sym`, `/explain`)

* **Purpose:** To understand a specific function or class.
* **Aim:** Practice using the `#sym` variable.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt (Copilot might suggest symbols as you type `#sym:`):
        ```
        #sym:OpenWeatherMapClient#fetchWeatherData /explain Explain what this method does, its parameters, and what it returns.
        ```
    3.  Alternatively, try explaining the data model class:
        ```
        #sym:WeatherData /explain Explain the purpose of this class and its fields.
        ```
    4.  Analyze the explanation provided.

### Exercise 1.7: Fetching External Info (`#fetch`, `/explain`)

* **Purpose:** To pull in information from an external URL.
* **Aim:** Practice using the `#fetch` variable.
* **Steps:**
    1.  The README mentions the OpenWeatherMap API. Let's ask about its current weather endpoint documentation.
    2.  Open the Copilot Chat view.
    3.  Type the following prompt:
        ```
        #fetch:[https://openweathermap.org/current](https://openweathermap.org/current) /explain Based on the content from this URL, what are the main parameters needed to call the current weather data API, and what key information is typically included in the JSON response (e.g., temperature, weather description)?
        ```
    4.  Review Copilot's summary based on the fetched web page content.

### Exercise 1.8: Asking About VS Code (`@vscode`, `/explain`)

* **Purpose:** To get help with VS Code features or settings relevant to the project.
* **Aim:** Practice using the `@vscode` participant to ask questions about the editor environment.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Think of a question about VS Code relevant to Java development (see examples below).
    3.  Type your prompt using `@vscode`:
        * Example 1: `@vscode /explain How can I see the definition of a Java method quickly without leaving my current file?`
        * Example 2: `@vscode /explain Are there settings to automatically organize imports in Java files when I save?`
        * Example 3: `@vscode /explain How do I configure task configurations in VS Code to run specific Maven goals for this project?`
    4.  Review Copilot's explanation about VS Code features.

### Exercise 1.9: Understanding Terminal Commands (`#terminalLastCommand`, `/explain`)

* **Purpose:** To use Copilot to explain commands executed in the integrated terminal.
* **Aim:** Practice using the `#terminalLastCommand` variable.
* **Steps:**
    1.  Open the integrated terminal in VS Code (View > Terminal).
    2.  Run a command relevant to the project, for example:
        ```bash
        mvn clean package -DskipTests
        ```
    3.  Wait for the command to complete.
    4.  Open the Copilot Chat view.
    5.  Type the following prompt:
        ```
        #terminalLastCommand /explain Explain what the last command run in the terminal does, including the purpose of any flags used (like -DskipTests).
        ```
    6.  Review Copilot's explanation of the Maven command.

### Exercise 1.10: Explaining Terminal Output (`#terminalSelection`, `/explain`)

* **Purpose:** To get clarification on specific parts of the output shown in the integrated terminal.
* **Aim:** Practice using the `#terminalSelection` variable.
* **Steps:**
    1.  In the integrated terminal, run a command that produces some detailed output, for example:
        ```bash
        mvn --version
        ```
    2.  **Select a specific part** of the output in the terminal, for instance, the line showing the Java version or the Maven home directory.
    3.  Open the Copilot Chat view.
    4.  Type the following prompt:
        ```
        #terminalSelection /explain What does the selected line from the terminal output signify in the context of my development environment?
        ```
    5.  Review Copilot's explanation of the selected output.

---

## Section 2: Ideate New Features with Copilot Chat

**Goal:** Use Copilot Chat as a brainstorming partner, leveraging its understanding of the codebase (`#codebase`).

---

### Exercise 2.1: Brainstorming Feature Ideas (`#codebase`)

* **Purpose:** To generate a list of potential enhancements.
* **Aim:** Practice using `#codebase` for creative suggestions. (Note: `#codebase` is often effective here, potentially working better than `@workspace` if needing deeper analysis for suggestions).
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt:
        ```
        #codebase Suggest 3-5 ideas for new features or significant improvements for this command-line weather application. For each idea, briefly explain the potential benefit.
        ```
    3.  Consider the suggestions.

### Exercise 2.2: Exploring an Idea (`#codebase`)

* **Purpose:** To flesh out the details of one specific feature idea.
* **Aim:** Practice having a conversational follow-up using `#codebase` context.
* **Steps:**
    1.  Choose one idea (e.g., adding forecast data).
    2.  In the Copilot Chat view, ask:
        ```
        #codebase Let's explore adding a 3-day forecast option. How could we modify the application? Would we need new API calls (check OpenWeatherMap docs if needed)? How would the output look different? What classes might need changes?
        ```
    3.  Discuss the approach with Copilot.

### Exercise 2.3: Improving Error Handling (`#codebase`)

* **Purpose:** To identify areas where error handling could be improved.
* **Aim:** Practice using `#codebase` to analyze potential weaknesses.
* **Steps:**
    1.  In the Copilot Chat view, type:
        ```
        #codebase Review the error handling in this application (e.g., in WeatherService, OpenWeatherMapClient, WeatherApp main method). Suggest ways to make it more robust or provide better user feedback for errors like invalid API keys, network timeouts, city not found, or unexpected API responses.
        ```
    2.  Evaluate Copilot's suggestions.

---

## Section 3: Implement Features using Copilot

**Goal:** Use Copilot's code generation capabilities (autocompletion, Edits mode, agents, slash commands) to implement changes, using `#codebase` where broad context is needed for generation/editing modes.

---

### Exercise 3.1: Adding a New Field (Code Completion & Edits Mode)

* **Purpose:** Add data, use Edits mode.
* **Aim:** Practice completion & Edits mode.
* **Steps:**
    1.  **Modify `WeatherData.java` (Code Completion):**
        * Open the file. Add `private int humidity;`. Use completion for getter/setter (`getH`, `setH`). Add `int humidity` to the constructor and `this.humidity = humidity;` using completion.
    2.  **Modify `OpenWeatherMapClient.java` (Edits Mode):**
        * Open the file. Find where the `WeatherData` object is created after parsing JSON.
        * **Select the lines of code** within that block responsible for extracting values (like temperature, description) and creating the `WeatherData` instance.
        * Open the Copilot Chat view.
        * **From the dropdown menu** in the Chat input area, select the **"Edits"** mode.
        * In the chat input, **type the instruction** (without any slash command prefix):
            ```
            Extract the 'humidity' integer value from the 'main' section of the JSON node and pass it to the WeatherData constructor along with the existing values.
            ```
        * Press Enter. Copilot should show a diff proposing the changes to your selected code. Review and apply if correct.
    3.  **Modify `WeatherApp.java` (Display - Code Completion):**
        * Open the file. Find the `System.out.println` statement. Modify it to include humidity, using code completion (e.g., start typing `+ ", Humidity: " + weatherData.getH`).

### Exercise 3.2: Generating Unit Tests (`#file`, `/tests`)

* **Purpose:** Automatically generate unit tests.
* **Aim:** Practice `/tests` with `#file`.
* **Steps:**
    1.  Open `src/test/java/com/weather/app/OpenWeatherMapClientTest.java`.
    2.  In the Copilot Chat view, type:
        ```
        #file:src/test/java/com/weather/app/OpenWeatherMapClientTest.java #file:src/main/java/com/weather/app/OpenWeatherMapClient.java /tests Generate a new JUnit test method for OpenWeatherMapClient. It should mock the HTTP call and JSON response (include 'main: { humidity: 85 }') and verify that fetchWeatherData correctly parses the humidity into the WeatherData object.
        ```
    3.  Copy the generated test method into your file, adjust imports/mocks if needed, and run tests (`mvn test`).

### Exercise 3.3: Refactoring with Edits Mode

* **Purpose:** Modify existing code via Edits mode.
* **Aim:** Practice Edits mode for refactoring.
* **Steps:**
    1.  *(Assumption: `ConfigUtil.java` exists with `getApiKey()` reading only the environment variable.)*
    2.  Open `src/main/java/com/weather/app/ConfigUtil.java`.
    3.  **Select the entire body** of the `getApiKey()` method.
    4.  Open the Copilot Chat view.
    5.  **Select the "Edits" mode** from the dropdown.
    6.  In the chat input, **type the instruction**:
        ```
        Refactor this: First, try the 'OPENWEATHERMAP_API_KEY' environment variable. If null or blank, try loading 'config.properties' from classpath resources and read the 'openweathermap.api.key' property. If still not found, throw an IllegalStateException indicating the key is missing.
        ```
    7.  Review the proposed diff and apply the changes.

### Exercise 3.4: Creating a New Component (`#codebase`, `/new`)

* **Purpose:** Use Copilot Agents (`/new`) to scaffold.
* **Aim:** Practice the `/new` command with `#codebase` context (as `/new` often requires broad project understanding).
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt:
        ```
        #codebase /new Create a new Java class named 'WeatherCache' in a new package 'com.weather.app.cache'. Include:
        1. A private static final ConcurrentHashMap<String, CacheEntry> cache storage.
        2. A private static final long CACHE_DURATION_MS = 300000; // 5 minutes
        3. A nested static class 'CacheEntry' with 'WeatherData data' and 'long timestamp'.
        4. A public static void put(String city, WeatherData data) method.
        5. A public static Optional<WeatherData> get(String city) method checking for non-expired entries.
        Include Javadoc.
        ```
    3.  Copilot should propose creating the new file (`src/main/java/com/weather/app/cache/WeatherCache.java`) and package. Review and approve.
    4.  *(Follow-up Task)* Manually integrate this `WeatherCache` into `WeatherService` (you could use "Edits" mode for this).

### Exercise 3.5: Fixing Code (`#problems`, `/fix`)

* **Purpose:** Use Copilot to fix detected problems.
* **Aim:** Practice `/fix` with `#problems`.
* **Steps:**
    1.  **Introduce a bug:** Open `src/main/java/com/weather/app/WeatherService.java`. In the `getWeather` method, intentionally introduce a typo, for example, change `WeatherData weatherData = weatherApiClient.fetchWeatherData(city);` to `WetherData weatherData = weatherApiClient.fetchWeatherData(city);` (typo in `WetherData`).
    2.  **Save the file.** VS Code's Java extension should detect this compilation error and list it in the "Problems" panel (View > Problems).
    3.  Open the Copilot Chat view.
    4.  Type the following prompt:
        ```
        #problems /fix Fix the errors listed in the problems panel.
        ```
    5.  Copilot should identify the typo and suggest a fix. Review the proposed change (it might show a diff or just apply it directly depending on configuration/context) and verify the problem is resolved. Alternatively, select the line with the error and use inline chat (`Cmd+I`/`Ctrl+I`) and type `/fix`.

### Exercise 3.6: Reviewing Code Changes (`#changes`, `/explain`)

* **Purpose:** Use Copilot to summarize pending changes.
* **Aim:** Practice using `#changes`.
* **Steps:**
    1.  Make a few small, distinct changes to one or two files (e.g., add a comment in `WeatherApp.java`, modify the output format slightly).
    2.  **Save the files.**
    3.  Open the Source Control view in VS Code (usually the Git icon). You should see your modified files listed under "Changes".
    4.  *(Optional)* Stage one of the changes, leaving another unstaged.
    5.  Open the Copilot Chat view.
    6.  Type the following prompt:
        ```
        #changes /explain Summarize the main themes or purposes of the current staged and unstaged code changes.
        ```
    7.  Review Copilot's summary of your pending modifications.

### Exercise 3.7: Customizing Copilot with Shared Instructions

* **Purpose:** Influence Copilot generation via `.github/copilot-instructions.md`.
* **Aim:** Define instruction, observe effect.
* **Steps:**
    1.  **Create Instruction File:**
        * In the root of your project workspace, create a folder named `.github` if it doesn't already exist.
        * Inside the `.github` folder, create a new file named `copilot-instructions.md`.
    2.  **Define Instruction:**
        * Open `copilot-instructions.md` and add the following content:
            ```markdown
            # Copilot Instructions for Simple Weather CLI App

            ## Java Development Guidelines

            - **Logging:** For application logging, always instantiate and use `java.util.logging.Logger`. Get the logger instance using `Logger.getLogger(YourClassName.class.getName())`. Avoid using `System.out.println` or `System.err.println` for routine logging within application logic. Log exceptions within catch blocks using the logger's `log` method with level `SEVERE` and the caught exception.
            ```
        * Save the file. *Note: Copilot should automatically detect and use these instructions for subsequent requests in this workspace. It might take a moment to register.*
    3.  **Apply Instruction (Add Logging):**
        * Open the file `src/main/java/com/weather/app/WeatherService.java`.
        * Locate the `getWeather(String city)` method.
        * **Select the entire body** of the `getWeather` method (from the opening `{` to the closing `}`).
        * Open the Copilot Chat view.
        * **Select the "Edits" mode** from the dropdown menu.
        * In the chat input, **type the instruction** (notice we *don't* specify *how* to log,