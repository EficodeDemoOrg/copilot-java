# GitHub Copilot Exercises: Simple Weather CLI App (Revised)

This document provides a series of exercises designed to help you learn and practice using GitHub Copilot features within the context of the Simple Weather CLI Java application. We will cover exploring the codebase, ideating new features, and implementing them using Copilot's capabilities.

**Key Copilot Interaction Points:**

* **Chat View:** Used for asking questions, generating code/tests/docs, and initiating actions. Modes like "Ask" (default), "Edits", and "Agent" might be selectable via a dropdown menu within the Chat view interface itself.
* **Inline Chat:** Quick chat directly in the editor (Default: `Cmd+I` / `Ctrl+I`), often used for quick explanations or edits on selected code. Allows reviewing multiple suggestions using keyboard shortcuts (e.g., `Alt+]`/`Option+]` or check the Command Palette for "Copilot: View Next/Previous Suggestion").
* **Participants (`@` references):** Used to bring specific, broad contexts into the chat, such as the entire workspace (`@workspace`) or the VS Code environment itself (`@vscode`). **Important Limitation:** You can only use **one participant** (e.g., `@workspace` OR `@vscode`) in a single chat prompt.
* **Variables (`#` references):** Used to provide more granular context to Copilot (e.g., files `#file`, selections `#selection`, symbols `#sym`, symbol usages/definitions `#usage`, changes `#changes`, codebase structure `#codebase`, web content `#fetch`, last terminal command `#terminalLastCommand`, terminal selection `#terminalSelection`). Variables *can* be combined with a participant (e.g., `@workspace #file:SomeFile.java`).
    * **Interactive Selection:** For files, folders, symbols (`#sym`), and usage queries (`#usage`), you typically type `#` and then start typing the name; VS Code's UI will suggest matching items from your workspace for you to select easily (e.g., typing `#OpenWe` might suggest the `OpenWeatherMapClient.java` file and the `OpenWeatherMapClient` class symbol).
    * **Drag and Drop:** You can also often drag files or folders directly from the VS Code Explorer into the Chat input area to add them as context.
* **Slash Commands:** Used within the Chat view or inline chat to direct Copilot's actions (e.g., `/explain`, `/tests`, `/fix`, `/new`).
* **Code Completion:** Automatic suggestions as you type.
* **Custom Instructions:** Files like `.github/copilot-instructions.md` can guide Copilot's suggestions for the workspace.

**Note on `@workspace` vs `#codebase` and Participant Usage:**

Both `@workspace` and `#codebase` provide Copilot with context about your entire project or workspace files, serving **essentially the same core function**. However, their usage context can differ:
* `@workspace` is the standard **participant** for general questions about the project, typically used within the default "Ask" mode of the Chat view. As a participant, it adheres to the **one-participant-per-prompt** rule.
* `#codebase` is a **variable** that also refers to the workspace context. You might observe that `#codebase` is particularly effective or required when using specific modes like "Edits" or "Agent" (`/new`), where a deeper analysis or generation based on the entire codebase structure is required. Since it's a variable, it doesn't conflict with the one-participant rule if you needed to use `@vscode` alongside workspace context (though combining `@vscode` and `#codebase` is an uncommon scenario).

These exercises generally use `@workspace` for broad "Ask" queries and `#codebase` when broad context seems needed for Agent/Edit tasks, reflecting common patterns and the potential need for `#codebase` in those specific modes. Feel free to experiment to see what works best in your specific scenario.

**Prerequisites:**

* Visual Studio Code installed.
* GitHub Copilot and Copilot Chat extensions installed and configured.
* The Simple Weather CLI project (as described in the structure diagram) opened in VS Code.
* An integrated terminal open within VS Code (e.g., View > Terminal).
* Basic understanding of Java and Maven.
* An OpenWeatherMap API key set as the `OPENWEATHERMAP_API_KEY` environment variable (required for some implementation/testing steps).

---

## Section 1: Explore the Codebase and Environment

**Goal:** Use Copilot Chat with various context providers (`@workspace`, `#file`, `#folder`, `#sym`, `#usage`, `#fetch`, `#terminalLastCommand`, `#terminalSelection`, `@vscode`) to quickly understand the project, its dependencies, relationships between components, the development environment, and external information.

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

### Exercise 1.2: Understanding a Specific Class (`#` file reference, `/explain`)

* **Purpose:** To dive deeper into the functionality of a single class.
* **Aim:** Practice referencing a file using the `#` prefix with interactive selection.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type `#` in the chat input.
    3.  Start typing `OpenWeatherMapClient`. VS Code should suggest matching files and symbols. Select the *file* `src/main/java/com/weather/app/OpenWeatherMapClient.java` from the list (it will likely insert `#file:src/...`).
    4.  Append the command `/explain Explain the role of this class. How does it fetch data from the OpenWeatherMap API? What dependencies does it seem to have?` to the prompt and press Enter.
    5.  Analyze the response.
    6.  *(Alternative)* Try dragging the `OpenWeatherMapClient.java` file from the Explorer into the Chat input instead of using `#` to achieve the same context.

### Exercise 1.3: Explaining Dependencies (`#` file reference, `/explain`)

* **Purpose:** To understand the external libraries.
* **Aim:** Practice referencing `pom.xml` using the `#` prefix with interactive selection.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type `#` and start typing `pom`. Select `pom.xml` from the suggestions.
    3.  Append `/explain Explain the roles of the main dependencies listed here, such as Jackson, JUnit, Mockito, and any relevant Maven plugins like Surefire or Assembly.` (Note: The report mentioned org.json, ensure prompt reflects actual dependencies if different) and press Enter.
    4.  Review the explanation.

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

### Exercise 1.5: Explore Folder Contents (`#` folder reference, `/explain`)

* **Purpose:** To get a summary of the code within a directory.
* **Aim:** Practice referencing a folder using the `#` prefix with interactive selection.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type `#` and start typing `com/weather/app`. Select the *folder* `src/main/java/com/weather/app` from the suggestions (it will likely insert `#folder:src/...`).
    3.  Append `/explain Summarize the purpose of the Java classes inside this application package.` and press Enter.
    4.  Review Copilot's summary.
    5.  *(Alternative)* Try dragging the `app` folder from the Explorer into the Chat input.

### Exercise 1.6: Explore a Specific Symbol (`#` symbol reference, `/explain`)

* **Purpose:** To understand a specific function or class.
* **Aim:** Practice referencing a symbol using the `#` prefix with interactive selection.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  **Example 1 (Method):**
        * Type `#` and start typing `WorkspaceWeatherData`. Select the *symbol* `OpenWeatherMapClient#fetchWeatherData` from the suggestions (it will likely insert `#sym:...`).
        * Append `/explain Explain what this method does, its parameters, and what it returns.` and press Enter.
    3.  **Example 2 (Class):**
        * Type `#` start typing `WeatherData` and select the *class symbol* `WeatherData` (likely inserts `#sym:WeatherData`).
        * Append `/explain Explain the purpose of this class and its fields.` and press Enter.
    4.  Analyze the explanations provided for these valid symbols.

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
* **Aim:** Practice using the `@vscode` participant to ask questions about the editor environment. Remember only one `@` participant per prompt.
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

### Exercise 1.11: Finding Symbol Usages (`#usage`)

* **Purpose:** To understand where a specific class, method, or variable is used within the project.
* **Aim:** Practice using the `#usage` variable combined with interactive symbol selection to find references.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Think of a symbol you want to find usages for (e.g., the `WeatherData` class or the `WeatherService#getWeather` method).
    3.  Type `#` and start typing the symbol name (e.g., `WeatherData`). Select the desired *symbol* from the suggestions (it will likely insert `#usage:WeatherData` or similar).
    4.  Append `/explain Where is this symbol used throughout the codebase? List the files and lines.` and press Enter.
    5.  Review the locations identified by Copilot. This helps understand the impact of changing this symbol.

### Exercise 1.12: Finding Interface Implementations (`#usage`)

* **Purpose:** To discover all classes that implement a specific interface.
* **Aim:** Practice using `#usage` with an interface symbol to find its implementations.
* **Steps:**
    1.  The project structure indicates a `WeatherApiClient` interface. Let's find its implementations.
    2.  Open the Copilot Chat view.
    3.  Type `#` and start typing `WeatherApiClient`. Select the *interface symbol* `WeatherApiClient` from the suggestions.
    4.  Append `/explain Find all classes in the workspace that implement this interface.` and press Enter.
    5.  Copilot should identify `OpenWeatherMapClient` (and potentially others if they existed) as implementations.

---

## Section 2: Ideate New Features with Copilot Chat

**Goal:** Use Copilot Chat as a brainstorming partner, leveraging its understanding of the codebase (`#codebase` or `@workspace`).

---

### Exercise 2.1: Brainstorming Feature Ideas (`#codebase`)

* **Purpose:** To generate a list of potential enhancements.
* **Aim:** Practice using `#codebase` (or `@workspace`) for creative suggestions.
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type the following prompt:
        ```
        #codebase Suggest 3-5 ideas for new features or significant improvements for this command-line weather application. For each idea, briefly explain the potential benefit.
        ```
    3.  Consider the suggestions.

### Exercise 2.2: Exploring an Idea (`#codebase`)

* **Purpose:** To flesh out the details of one specific feature idea.
* **Aim:** Practice having a conversational follow-up using `#codebase` (or `@workspace`) context.
* **Steps:**
    1.  Choose one idea (e.g., adding forecast data).
    2.  In the Copilot Chat view, ask:
        ```
        #codebase Let's explore adding a 3-day forecast option. How could we modify the application? Would we need new API calls (check OpenWeatherMap docs if needed)? How would the output look different? What classes might need changes?
        ```
    3.  Discuss the approach with Copilot.

### Exercise 2.3: Improving Error Handling (`#codebase`)

* **Purpose:** To identify areas where error handling could be improved.
* **Aim:** Practice using `#codebase` (or `@workspace`) to analyze potential weaknesses.
* **Steps:**
    1.  In the Copilot Chat view, type:
        ```
        #codebase Review the error handling in this application (e.g., in WeatherService, OpenWeatherMapClient, WeatherApp main method). Suggest ways to make it more robust or provide better user feedback for errors like invalid API keys, network timeouts, city not found, or unexpected API responses.
        ```
    2.  Evaluate Copilot's suggestions.

---

## Section 3: Implement Features using Copilot

**Goal:** Use Copilot's code generation capabilities (autocompletion, Edits mode, agents, slash commands, inline chat suggestions) to implement changes, using `#codebase` where broad context is needed for generation/editing modes.

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

### Exercise 3.2: Generating Unit Tests (`#` file references, `/tests`)

* **Purpose:** Automatically generate unit tests.
* **Aim:** Practice `/tests` with `#` file referencing (interactive selection).
* **Steps:**
    1.  Open the Copilot Chat view.
    2.  Type `#` and select `src/test/java/com/weather/app/OpenWeatherMapClientTest.java`.
    3.  Type `#` again and select `src/main/java/com/weather/app/OpenWeatherMapClient.java`.
    4.  Append the following prompt and press Enter:
        ```
        /tests Generate a new JUnit test method for OpenWeatherMapClient. It should mock the HTTP call and JSON response (include 'main: { humidity: 85 }') and verify that fetchWeatherData correctly parses the humidity into the WeatherData object.
        ```
    5.  Copy the generated test method into your test file, adjust imports/mocks if needed, and run the tests (`mvn test`).

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

### Exercise 3.5: Reviewing Code Changes (`#changes`, `/explain`)

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

### Exercise 3.6: Customizing Copilot with Shared Instructions

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

### Exercise 3.7: Full Implementation Workflow (Ideate -> Spec -> Implement -> Refactor)

* **Purpose:** To simulate a small feature development lifecycle using various Copilot capabilities sequentially.
* **Aim:** Practice using Ask mode for ideation/spec, `#` file referencing for implementation guidance, and Edits mode for refinement.
* **Steps:**
    1.  **A. Ideate (Ask):** In Copilot Chat (Ask mode), prompt:
        ```
        @workspace Suggest a simple new feature for this weather CLI app. For example, something related to units or output format.
        ```
        Let's assume Copilot suggests adding a choice between Celsius and Fahrenheit units.
    2.  **B. Specify (Ask):** Continue the chat:
        ```
        Generate a short technical specification in Markdown format for adding a command-line option (`--units`) to specify temperature units (Celsius 'C' or Fahrenheit 'F'). Default should be Celsius. Specify required changes to input handling, data storage/processing (if any), and output display.
        ```
    3.  **C. Save Specification:** Copy the generated Markdown spec. Create a new file in the `docs/specs/` directory named `UnitsFeature.md` (create the `specs` folder if needed) and paste the content. Save the file.
    4.  **D. Plan Implementation (Ask):** In Copilot Chat:
        ```
        #codebase # Explain Based on the feature described in # (start typing UnitsFeature.md and select it), outline the implementation steps. Which Java files likely need changes, and what are the key modifications required in each (e.g., argument parsing, data model, service logic, output formatting)?
        ```
        Review the plan provided by Copilot.
    5.  **E. Implement Changes (Edits/Ask/Completion):** Based on the plan from Step D:
        * Open the primary file for argument parsing (likely `WeatherApp.java`). Use Edits mode (selecting relevant sections) or Ask mode (`# (select UnitsFeature.md) # (select WeatherApp.java) /explain Show me how to add command-line argument parsing for '--units' using a library like commons-cli if it's available, or basic args loop otherwise.`) to implement argument handling. Use code completion.
        * Open `WeatherData.java`. If the spec requires storing the unit or performing conversion, use Edits mode or Ask (`# (select UnitsFeature.md) # (select WeatherData.java) /explain Suggest changes needed in this class based on the spec.`)
        * Open `WeatherApp.java` again (or the relevant display class). Use Edits mode or Ask (`# (select UnitsFeature.md) # (select WeatherApp.java) /explain Update the output display logic to show the temperature in the selected unit (C or F) and include the unit symbol.`) Apply changes.
    6.  **F. Refine (Edits):** Review the implemented code. Select sections that could be cleaner or more robust. Use Edits mode with prompts like "Refactor this temperature conversion logic for clarity" or "Add error handling if the --units argument is invalid."

### Exercise 3.8: Reviewing Inline Chat Suggestions

* **Purpose:** To practice exploring multiple code suggestions provided by Copilot's inline chat.
* **Aim:** Use inline chat for a simple task and explicitly cycle through the different options Copilot offers.
* **Steps:**
    1.  Open `src/main/java/com/weather/app/WeatherData.java`.
    2.  Select the entire constructor definition (signature and body).
    3.  Open inline chat (Default: `Cmd+I` / `Ctrl+I`).
    4.  Type the prompt: `/doc Generate Javadoc for this constructor.` and press Enter.
    5.  Copilot will show its first suggestion.
    6.  **Cycle Suggestions:** Use the keyboard shortcut for viewing the next/previous suggestion (e.g., `Alt+]` / `Alt+[` or `Option+]` / `Option+[` - check VS Code's keybindings or the Copilot documentation if these don't work). Observe if Copilot offers alternative phrasings or formats for the Javadoc.
    7.  Choose the suggestion you prefer and accept it (often by pressing `Tab` or clicking "Accept").

---

## Section 4: Optional Advanced Exercises

**Goal:** Explore more nuanced or specialized applications of GitHub Copilot beyond the basic workflows.

---

### Exercise 4.1: Debugging Assistance (Runtime Errors)

* **Purpose:** Practice using Copilot Chat to understand runtime errors.
* **Aim:** Use `#` file referencing and pasted stack traces to ask Copilot for insights.
* **Steps:**
    1.  **(Optional Setup - Induce an Error):** Modify `OpenWeatherMapClient.java`. Find where data is extracted from the `JsonNode`, for example, `rootNode.path("main").path("temp").asDouble();`. Temporarily remove any null checks or default values around a potentially missing field (like `humidity` if you added it, or even `description`). Alternatively, modify a unit test's mock response to return incomplete JSON.
    2.  **Trigger the Error:** Run the application (`java -jar ...`) or the modified unit test (`mvn test`) in a way that triggers the error (e.g., using a specific city or relying on the faulty mock data). You should see an error message and stack trace (like `NullPointerException`) printed to the console/terminal.
    3.  **Copy the Stack Trace:** Select and copy the complete stack trace from the terminal output.
    4.  **Ask Copilot:** Open the Copilot Chat view. Type a prompt including the relevant file context (using `#` interactive selection) and the pasted stack trace:
        ```
        # (start typing OpenWeatherMapClient.java and select it) /explain I encountered the following runtime error when running the application. Based on the code in the referenced file and this stack trace, what could be the likely cause? What are some potential fixes or checks I should add?

        [Paste the full stack trace here]
        ```
    5.  **Analyze Suggestion:** Review Copilot's explanation of the error and the suggested fixes (e.g., adding null checks, using default values, checking path existence).

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

### Exercise 4.3: Code Review Assistance (Security & Performance) (`#codebase`)

* **Purpose:** To use Copilot as a preliminary reviewer to identify potential areas of concern in the codebase.
* **Aim:** Practice asking targeted questions about security and performance using `#codebase` (often works well for analysis requiring broad context).
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

### Exercise 4.4: Exploring Alternative Implementations

* **Purpose:** To ask Copilot for different ways to achieve the same programming task.
* **Aim:** Use `#selection` and `#` file referencing (interactive) to request alternatives.
* **Steps:**
    1.  **Select Code:** Open `src/main/java/com/weather/app/OpenWeatherMapClient.java`. Select the block of code inside the `WorkspaceWeatherData` method that is responsible for parsing the JSON response string into the `WeatherData` object (likely involving `ObjectMapper`).
    2.  **Open Copilot Chat:** Navigate to the Copilot Chat view.
    3.  **Add Context and Prompt:**
        * Type `#selection` (to add selected code).
        * Type `#` and select `pom.xml`.
        * Append the following prompt and press Enter:
          ```
          /explain Show me an alternative way to implement the selected code's functionality (parsing JSON to a Java object). Could it be done using a different feature of the Jackson library, or perhaps using another JSON processing library if one were suitable and available (check pom.xml)? Briefly discuss any trade-offs.
          ```
    4.  **Evaluate Options:** Review the alternative implementation(s) suggested by Copilot. Consider their clarity, efficiency, and dependencies compared to the original code.

---

### Note on Advanced Customization: Reusable Prompt Files

Beyond the workspace-level `.github/copilot-instructions.md` explored in Exercise 3.6, Copilot also supports **reusable prompt files** (often with a `.prompt.md` extension, though this feature might be experimental or evolve).

These allow you to define more complex, multi-step prompts or instructions for specific, repeatable tasks (e.g., a standard refactoring pattern, generating code from a specific template, a detailed code review checklist). You can include placeholders and combine instructions with context variables. While we haven't created a specific exercise for this, it's a powerful feature to explore if you find yourself repeatedly giving Copilot the same complex instructions for common tasks within your project. You could investigate the official VS Code Copilot documentation for the latest details on creating and using these files.

---