# GitHub Copilot Exercises: Simple Weather CLI App (Revised)

This document provides a series of exercises designed to help you learn and practice using GitHub Copilot features within the context of the Simple Weather CLI Java application. We will cover exploring the codebase, ideating new features, and implementing them using Copilot's capabilities.

**Key Copilot Interaction Points:**

* **Chat View:** Used for asking questions, generating code/tests/docs, and initiating actions. Modes like "Ask" (default), "Edits", and "Agent" might be selectable via a dropdown menu within the Chat view interface itself.
* **Inline Chat:** Quick chat directly in the editor (Default: `Cmd+I` / `Ctrl+I`), often used for quick explanations or edits on selected code.
* **Slash Commands:** Used within the Chat view or inline chat to direct Copilot's actions (e.g., `/explain`, `/tests`, `/fix`, `/new`).
* **Variables (`#` references):** Used to provide specific context to Copilot (e.g., `#file`, `#selection`, `#codebase`, `#folder`, `#sym`, `#changes`, `#fetch`).
* **Code Completion:** Automatic suggestions as you type.

**Prerequisites:**

* Visual Studio Code installed.
* GitHub Copilot and Copilot Chat extensions installed and configured.
* The Simple Weather CLI project opened in VS Code.
* Basic understanding of Java and Maven.
* An OpenWeatherMap API key set as the `OPENWEATHERMAP_API_KEY` environment variable (required for some implementation/testing steps).

---

## Section 1: Explore the Codebase with Copilot Chat

**Goal:** Use Copilot Chat and its context awareness variables to quickly understand the existing project structure, components, logic, and external information without manually reading everything.

---

### Exercise 1.1: Project Overview (`#codebase`, `/explain`)

* **Purpose:** To get a high-level understanding of the project's goals, main components, and structure.
* **Aim:** Practice using the `#codebase` variable in Copilot Chat to ask broad questions about the entire project.
* **Steps:**
    1.  Open the Copilot Chat view in VS Code.
    2.  In the chat input, type the following prompt and press Enter:
        ```
        #codebase /explain What is the main purpose of this project and how is it structured? What are the key Java classes involved according to the source code and README?
        ```
    3.  Review Copilot's explanation.

---

### Exercise 1.2: Understanding a Specific Class (`#file`, `/explain`)

* **Purpose:** To dive deeper into the functionality of a single, important class.
* **Aim:** Practice using the `#file` variable combined with the `/explain` slash command.
* **Steps:**
    1.  Open the file `src/main/java/com/weather/app/OpenWeatherMapClient.java` in the editor.
    2.  Open the Copilot Chat view.
    3.  Type the following prompt:
        ```
        #file:src/main/java/com/weather/app/OpenWeatherMapClient.java /explain Explain the role of this class. How does it fetch data from the OpenWeatherMap API? What dependencies does it seem to have?
        ```
    4.  Analyze the response.

---

### Exercise 1.3: Explaining Dependencies (`#file`, `/explain`)

* **Purpose:** To understand the external libraries the project relies on.
* **Aim:** Practice using `#file` with `pom.xml`.
* **Steps:**
    1.  Open the `pom.xml` file.
    2.  In the Copilot Chat view, type:
        ```
        #file:pom.xml /explain Explain the roles of the main dependencies listed here, such as Jackson, JUnit, Mockito, and any relevant Maven plugins like Surefire or Assembly.
        ```
    3.  Review the explanation.

---

### Exercise 1.4: Generating Documentation (`#selection`)

* **Purpose:** To automatically generate documentation for existing code using the Chat view.
* **Aim:** Practice using the `#selection` variable to generate Javadoc.
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

---

### Exercise 1.5: Explore Folder Contents (`#folder`, `/explain`)

* **Purpose:** To get a summary of the code within a specific directory.
* **Aim:** Practice using the `#folder` variable.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt:
        ```
        #folder:src/main/java/com/weather/app /explain Summarize the purpose of the Java classes inside this application package.
        ```
    3.  Review Copilot's summary of the classes like `WeatherApp`, `WeatherService`, `OpenWeatherMapClient`, etc.

---

### Exercise 1.6: Explore a Specific Symbol (`#sym`, `/explain`)

* **Purpose:** To understand a specific function or class identified by its symbol name.
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

---

### Exercise 1.7: Fetching External Info (`#fetch`, `/explain`)

* **Purpose:** To pull in information from an external URL relevant to the project.
* **Aim:** Practice using the `#fetch` variable to get context from the web.
* **Steps:**
    1.  The README mentions the OpenWeatherMap API. Let's ask about its current weather endpoint documentation.
    2.  Open the Copilot Chat view.
    3.  Type the following prompt:
        ```
        #fetch:[https://openweathermap.org/current](https://openweathermap.org/current) /explain Based on the content from this URL, what are the main parameters needed to call the current weather data API, and what key information is typically included in the JSON response (e.g., temperature, weather description)?
        ```
    4.  Review Copilot's summary based on the fetched web page content.

---

## Section 2: Ideate New Features with Copilot Chat

**Goal:** Use Copilot Chat as a brainstorming partner, leveraging its understanding of the codebase (`#codebase`).

---

### Exercise 2.1: Brainstorming Feature Ideas (`#codebase`)

* **Purpose:** To generate a list of potential enhancements.
* **Aim:** Practice using `#codebase` to ask Copilot for creative suggestions.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt:
        ```
        #codebase Suggest 3-5 ideas for new features or significant improvements for this command-line weather application. For each idea, briefly explain the potential benefit.
        ```
    3.  Consider the suggestions.

---

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

---

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

**Goal:** Use Copilot's code generation capabilities (autocompletion, Edits mode, agents, slash commands) to implement changes.

---

### Exercise 3.1: Adding a New Field (Code Completion & Edits Mode)

* **Purpose:** To add a simple new piece of data (e.g., humidity) and use the "Edits" mode for code modification.
* **Aim:** Practice code completion and using the Chat view's "Edits" mode on selected code.
* **Steps:**
    1.  **Modify `WeatherData.java` (Code Completion):**
        * Open the file. Add `private int humidity;`. Use completion for getter/setter (`getH`, `setH`). Add `int humidity` to the constructor and `this.humidity = humidity;` using completion.
    2.  **Modify `OpenWeatherMapClient.java` (Edits Mode):**
        * Open the file. Find where the `WeatherData` object is created after parsing JSON.
        * **Select the lines of code** within that block responsible for extracting values (like temperature, description) and creating the `WeatherData` instance.
        * Open the Copilot Chat view.
        * **From the dropdown menu** in the Chat input area, select the **"Edits"** mode.
        * In the chat input, **type the instruction** (without any slash command):
            ```
            Extract the 'humidity' integer value from the 'main' section of the JSON node and pass it to the WeatherData constructor along with the existing values.
            ```
        * Press Enter. Copilot should show a diff proposing the changes to your selected code. Review and apply if correct.
    3.  **Modify `WeatherApp.java` (Display - Code Completion):**
        * Open the file. Find the `System.out.println` statement. Modify it to include humidity, using code completion (e.g., start typing `+ ", Humidity: " + weatherData.getH`).

---

### Exercise 3.2: Generating Unit Tests (`#file`, `/tests`)

* **Purpose:** To automatically generate unit tests.
* **Aim:** Practice using the `/tests` slash command referencing relevant files.
* **Steps:**
    1.  Open `src/test/java/com/weather/app/OpenWeatherMapClientTest.java`.
    2.  In the Copilot Chat view, type:
        ```
        #file:src/test/java/com/weather/app/OpenWeatherMapClientTest.java #file:src/main/java/com/weather/app/OpenWeatherMapClient.java /tests Generate a new JUnit test method for OpenWeatherMapClient. It should mock the HTTP call and JSON response (include 'main: { humidity: 85 }') and verify that fetchWeatherData correctly parses the humidity into the WeatherData object.
        ```
    3.  Copy the generated test method into your file, adjust imports/mocks if needed, and run tests (`mvn test`).

---

### Exercise 3.3: Refactoring with Edits Mode

* **Purpose:** To modify existing code using natural language via the "Edits" mode.
* **Aim:** Practice using the Chat view's "Edits" mode for refactoring selected code.
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

---

### Exercise 3.4: Creating a New Component (`#codebase`, `/new`)

* **Purpose:** To use Copilot Agents to scaffold a new class structure.
* **Aim:** Practice using the `/new` agent command (ensure Agent mode might need selecting or `/new` implies it).
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

---

### Exercise 3.5: Fixing Code (`#problems`, `/fix`)

* **Purpose:** To use Copilot to fix detected problems in the code.
* **Aim:** Practice using the `/fix` command, potentially with the `#problems` context variable.
* **Steps:**
    1.  **Introduce a bug:** Open `src/main/java/com/weather/app/WeatherService.java`. In the `getWeather` method, intentionally introduce a typo, for example, change `WeatherData weatherData = weatherApiClient.fetchWeatherData(city);` to `WetherData weatherData = weatherApiClient.fetchWeatherData(city);` (typo in `WetherData`).
    2.  **Save the file.** VS Code's Java extension should detect this compilation error and list it in the "Problems" panel (View > Problems).
    3.  Open the Copilot Chat view.
    4.  Type the following prompt:
        ```
        #problems /fix Fix the errors listed in the problems panel.
        ```
    5.  Copilot should identify the typo and suggest a fix. Review the proposed change (it might show a diff or just apply it directly depending on configuration/context) and verify the problem is resolved. Alternatively, select the line with the error and use inline chat (`Cmd+I`/`Ctrl+I`) and type `/fix`.

---

### Exercise 3.6: Reviewing Code Changes (`#changes`, `/explain`)

* **Purpose:** To use Copilot to summarize pending source control changes.
* **Aim:** Practice using the `#changes` variable.
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

---

### Exercise 3.7: Customizing Copilot with Shared Instructions

* **Purpose:** To influence Copilot's code generation to follow project-specific guidelines using shared instructions.
* **Aim:** Define a workspace-level instruction in `.github/copilot-instructions.md` and observe its effect when asking Copilot to add functionality (like logging).
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
        * In the chat input, **type the instruction** (notice we *don't* specify *how* to log, letting the instructions file guide Copilot):
            ```
            Add logging to this method:
            1. At the beginning, log the city being requested at INFO level.
            2. After successfully fetching weatherData, log the retrieved temperature at INFO level.
            3. Within the catch block, log the caught WeatherApiException at SEVERE level, including the exception object itself.
            Ensure the necessary logger instance is created if it doesn't exist.
            ```
        * Press Enter.
    4.  **Observe Result:**
        * Review the diff proposed by Copilot.
        * **Verify:** Did Copilot add code to instantiate `java.util.logging.Logger`? Did it use `logger.log(Level.INFO, ...)` and `logger.log(Level.SEVERE, ..., exception)` for logging, rather than `System.out.println`?
        * If the instructions were picked up correctly, the generated code should follow the guideline specified in `copilot-instructions.md`. Apply the changes if they look correct and follow the instructions.

---
## Section 4: Optional Advanced Exercises

**Goal:** Explore more nuanced or specialized applications of GitHub Copilot beyond the basic workflows. These exercises are optional and demonstrate using Copilot for tasks like debugging runtime errors, generating commit messages, performing code analysis, and exploring alternative solutions.

---

### Exercise 4.1: Debugging Assistance (Runtime Errors)

* **Purpose:** To practice using Copilot Chat to understand and potentially resolve runtime errors.
* **Aim:** Simulate or encounter a runtime error, capture its stack trace, and ask Copilot for insights using relevant code context.
* **Steps:**
    1.  **(Optional Setup - Induce an Error):** Modify `OpenWeatherMapClient.java`. Find where data is extracted from the `JsonNode`, for example, `rootNode.path("main").path("temp").asDouble();`. Temporarily remove any null checks or default values around a potentially missing field (like `humidity` if you added it, or even `description`). Alternatively, modify a unit test's mock response to return incomplete JSON.
    2.  **Trigger the Error:** Run the application (`java -jar ...`) or the modified unit test (`mvn test`) in a way that triggers the error (e.g., using a specific city or relying on the faulty mock data). You should see an error message and stack trace (like `NullPointerException`) printed to the console/terminal.
    3.  **Copy the Stack Trace:** Select and copy the complete stack trace from the terminal output.
    4.  **Ask Copilot:** Open the Copilot Chat view. Type a prompt including the relevant file context and the pasted stack trace:
        ```
        #file:src/main/java/com/weather/app/OpenWeatherMapClient.java /explain I encountered the following runtime error when running the application. Based on the code in the referenced file and this stack trace, what could be the likely cause? What are some potential fixes or checks I should add?

        [Paste the full stack trace here]
        ```
    5.  **Analyze Suggestion:** Review Copilot's explanation of the error and the suggested fixes (e.g., adding null checks, using default values, checking path existence).

---

### Exercise 4.2: Commit Message Generation

* **Purpose:** To leverage Copilot for drafting standardized Git commit messages.
* **Aim:** Use the `#changes` context variable to ask Copilot Chat to generate a commit message based on pending code changes.
* **Steps:**
    1.  **Ensure Pending Changes:** Make sure you have some uncommitted changes in your workspace (staged or unstaged), perhaps from completing previous exercises.
    2.  **Open Copilot Chat:** Navigate to the Copilot Chat view.
    3.  **Generate Commit Message:** Type the following prompt:
        ```
        #changes /explain Generate a concise Git commit message summarizing these code changes. Follow the Conventional Commits specification (e.g., using prefixes like 'feat:', 'fix:', 'refactor:', 'test:', 'docs:', 'chore:').
        ```
    4.  **Review:** Evaluate the generated commit message. Is it accurate? Does it follow the requested format? You can use this as a starting point for your actual commit.

---

### Exercise 4.3: Code Review Assistance (Security & Performance)

* **Purpose:** To use Copilot as a preliminary reviewer to identify potential areas of concern in the codebase.
* **Aim:** Practice asking targeted questions about security and performance using the `#codebase` context.
* **Steps:**
    1.  **Open Copilot Chat:** Navigate to the Copilot Chat view.
    2.  **Ask about Security:** Type the following prompt:
        ```
        #codebase /explain Review the application, particularly how the OpenWeatherMap API key is handled and how external data is fetched and processed. Are there any potential security vulnerabilities, risks, or recommended best practices being missed?
        ```
        Review Copilot's analysis (it might point out risks if the key were hardcoded, suggest environment variables - which you already use, or comment on input validation if applicable).
    3.  **Ask about Performance:** Type the following prompt:
        ```
        #codebase /explain Analyze the application's code, focusing on areas like API client interactions, JSON parsing, and any data manipulation. Are there any obvious potential performance bottlenecks or suggestions for optimization?
        ```
        Review Copilot's suggestions (it might mention caching - which you explored, efficient string handling, or minimizing API calls if applicable).

---

### Exercise 4.4: Exploring Alternative Implementations

* **Purpose:** To ask Copilot for different ways to achieve the same programming task.
* **Aim:** Select a specific code block and ask Copilot Chat to suggest alternative approaches or libraries.
* **Steps:**
    1.  **Select Code:** Open `src/main/java/com/weather/app/OpenWeatherMapClient.java`. Select the block of code inside the `WorkspaceWeatherData` method that is responsible for parsing the JSON response string into the `WeatherData` object (likely involving `ObjectMapper`).
    2.  **Open Copilot Chat:** Navigate to the Copilot Chat view.
    3.  **Request Alternatives:** Type the following prompt:
        ```
        #selection #file:pom.xml /explain Show me an alternative way to implement the selected code's functionality (parsing JSON to a Java object). Could it be done using a different feature of the Jackson library, or perhaps using another JSON processing library if one were suitable and available (check pom.xml)? Briefly discuss any trade-offs.
        ```
    4.  **Evaluate Options:** Review the alternative implementation(s) suggested by Copilot. Consider their clarity, efficiency, and dependencies compared to the original code.

---

### Note on Advanced Customization: Reusable Prompt Files

Beyond the workspace-level `.github/copilot-instructions.md` explored in Exercise 3.7, Copilot also supports **reusable prompt files** (often with a `.prompt.md` extension, though this feature might be experimental or evolve).

These allow you to define more complex, multi-step prompts or instructions for specific, repeatable tasks (e.g., a standard refactoring pattern, generating code from a specific template, a detailed code review checklist). You can include placeholders and combine instructions with context variables. While we haven't created a specific exercise for this, it's a powerful feature to explore if you find yourself repeatedly giving Copilot the same complex instructions for common tasks within your project. You could investigate the official VS Code Copilot documentation for the latest details on creating and using these files.

---

Remember to experiment with different phrasings and context variables. The goal is to integrate Copilot effectively into your development workflow.