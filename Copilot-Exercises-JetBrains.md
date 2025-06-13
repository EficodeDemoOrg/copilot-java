# GitHub Copilot in IntelliJ: Simple Weather CLI App Exercises

This document provides a series of exercises designed to help you learn and practice using the GitHub Copilot plugin within JetBrains IntelliJ IDEA. We will cover exploring the codebase, ideating new features, and implementing them using Copilot's capabilities in the JetBrains environment.

### Key GitHub Copilot Interaction Points in IntelliJ:

* **Copilot Chat Tool Window:** Your main interface for interacting with Copilot. You can open it from the right-hand sidebar. This is where you'll ask questions, use slash commands, and see detailed responses.
* **Inline Chat:** Allows you to have a quick, contextual chat directly in the editor. Select a block of code and use the shortcut `Shift+Ctrl+I` (macOS) or `Shift+Ctrl+G` (Windows/Linux), or right-click and choose "Copilot > Start Inline Chat".
* **Context Participants (`@`):** Used in the Chat window to bring specific, broad contexts into your prompt.
    * `@project`: The equivalent of `@workspace` in VS Code. It gives Copilot context of your entire project.
* **Chat Variables (`#`):** Used to provide more granular context from your editor to Copilot.
    * `#file`: References one or more specific files. You can type `#file:` and start typing a filename to get suggestions.
    * `#selection`: Automatically includes the code you have selected in the editor.
* **Slash Commands (`/`):** Quick shortcuts for common tasks used within the Chat window or Inline Chat. Common commands include `/explain`, `/tests`, `/fix`, and `/doc`.
* **Code Completion (Ghost Text):** Automatic, inline suggestions that appear as you type. Press `Tab` to accept them.
* **Commit Message Generation:** A dedicated Copilot icon in the Git commit window that analyzes your staged changes and suggests a commit message.
* **Agent Mode (Preview):** A mode in the Copilot Chat window for performing more complex, multi-step tasks. This may need to be enabled in settings and is still in preview.

### Prerequisites:

* JetBrains IntelliJ IDEA installed.
* The **GitHub Copilot** plugin installed from the JetBrains Marketplace and configured with your GitHub account.
* The Simple Weather CLI project opened in IntelliJ.
* An integrated terminal open within IntelliJ (View > Tool Windows > Terminal).
* Basic understanding of Java and Maven.
* An OpenWeatherMap API key set as the `OPENWEATHERMAP_API_KEY` environment variable.

---

## Section 1: Explore the Codebase

**Goal:** Use Copilot Chat with various context providers (`@project`, `#file`) to quickly understand the project and its components.

---

### Exercise 1.1: Project Overview (`@project`)

* **Purpose:** To get a high-level understanding of the project's goals and structure.
* **Steps:**
    1.  Open the Copilot Chat tool window.
    2.  In the chat input, type the following prompt and press Enter:
        ```
        @project /explain What is the main purpose of this project and how is it structured?
        ```
    3.  Review Copilot's explanation.

### Exercise 1.2: Understanding a Specific Class (`#file`)

* **Purpose:** To dive deeper into the functionality of a single class.
* **Steps:**
    1.  Open the Copilot Chat window.
    2.  In the chat input, type `#file:` and then start typing `OpenWeatherMapClient`. Select the correct file from the suggestions that appear.
    3.  Append your question to the prompt:
        ```
        #file:src/main/java/com/weather/app/OpenWeatherMapClient.java /explain Explain the role of this class. How does it fetch data from the API?
        ```
    4.  Analyze the response.

### Exercise 1.3: Explaining Dependencies (`#file`)

* **Purpose:** To understand the project's external libraries.
* **Steps:**
    1.  Open the Copilot Chat window.
    2.  Use the `#file` variable to reference the `pom.xml`:
        ```
        #file:pom.xml /explain Explain the roles of the main dependencies listed here, such as Jackson and JUnit.
        ```
    3.  Review the explanation.

### Exercise 1.4: Generating Documentation (`/doc`)

* **Purpose:** To automatically generate documentation using Inline Chat.
* **Steps:**
    1.  Open the file `src/main/java/com/weather/app/WeatherService.java`.
    2.  Select the entire method signature and body of the `getWeather(String city)` method.
    3.  Start an Inline Chat session (`Shift+Ctrl+I` or right-click > Copilot > Start Inline Chat).
    4.  In the inline prompt that appears, type `/doc` and press Enter.
    5.  Copilot will generate the Javadoc. Review it and click "Accept" to add it to your code.

---

## Section 2: Ideate New Features with Copilot Chat

**Goal:** Use Copilot Chat as a brainstorming partner with a deep understanding of the codebase.

---

### Exercise 2.1: Brainstorming Feature Ideas (`@project`)

* **Purpose:** To generate a list of potential enhancements.
* **Steps:**
    1.  Open the Copilot Chat window.
    2.  Type the following prompt:
        ```
        @project Suggest 3-5 ideas for new features or improvements for this command-line weather application.
        ```
    3.  Consider the suggestions.

### Exercise 2.2: Exploring an Idea (`@project`)

* **Purpose:** To flesh out the details of one specific feature idea.
* **Steps:**
    1.  Choose one idea from the previous exercise (e.g., adding forecast data).
    2.  In the same Copilot Chat session, ask a follow-up question:
        ```
        @project Let's explore adding a 3-day forecast. How could we modify the application? What classes might need changes, and what would the output look like?
        ```
    3.  Discuss the approach with Copilot.

---

## Section 3: Implement Features using Copilot

**Goal:** Use Copilot's code generation capabilities to implement changes efficiently.

---

### Exercise 3.1: Adding a New Field (Code Completion & Inline Chat)

* **Purpose:** To practice using both automatic code completion and targeted inline edits.
* **Steps:**
    1.  **Modify `WeatherData.java` (Code Completion):** Open the file. Add the line `private int humidity;`. Use Copilot's "ghost text" suggestions to quickly add the getter, setter, and update the constructor.
    2.  **Modify `OpenWeatherMapClient.java` (Inline Chat):**
        * Open the file and select the block of code inside `fetchWeatherData` where the `WeatherData` object is created.
        * Start an Inline Chat.
        * Type the instruction: `/fix Extract the 'humidity' integer from the 'main' section of the JSON node and pass it to the WeatherData constructor.`
        * Review the proposed diff and accept the changes.
    3.  **Modify `WeatherApp.java` (Code Completion):** Open the file. Find the `System.out.println` statement and start modifying it to include humidity, accepting Copilot's suggestions as you type.

### Exercise 3.2: Generating Unit Tests (`/tests`)

* **Purpose:** To automatically generate unit tests for selected code.
* **Steps:**
    1.  Open `src/main/java/com/weather/app/OpenWeatherMapClient.java`.
    2.  Select the entire class content.
    3.  Open the Copilot Chat window. With the code selected, the chat will use it as context.
    4.  Type the prompt:
        ```
        /tests Generate a new JUnit test method for OpenWeatherMapClient. It should mock the HTTP call and a sample JSON response, then verify that fetchWeatherData correctly parses the data.
        ```
    5.  Copy the generated test method into your test file, adjust imports if needed, and run the tests (`mvn test`).

### Exercise 3.3: Creating a New Component (Agent Mode)

* **Purpose:** Use Copilot's Agent Mode to scaffold a new class from a detailed prompt.
* **Steps:**
    1.  Open the Copilot Chat window.
    2.  From the dropdown menu in the chat input area, select **Agent (Preview)** mode.
    3.  Type the following prompt:
        ```
        Create a new Java class named 'WeatherCache' in a new package 'com.weather.app.cache'. This class should have:
        1. A private static final ConcurrentHashMap<String, CacheEntry> for storage.
        2. A private static final long CACHE_DURATION_MS = 300000; // 5 minutes
        3. A nested static class 'CacheEntry' with 'WeatherData data' and 'long timestamp'.
        4. A public static method `put(String city, WeatherData data)`.
        5. A public static method `get(String city)` that returns an Optional<WeatherData> and checks for non-expired entries.
        Include Javadoc.
        ```
    4.  Copilot will analyze the request, propose a plan, and create the new file and package. Review and approve the changes.

### Exercise 3.4: Generating Commit Messages

* **Purpose:** To leverage Copilot for drafting standardized Git commit messages.
* **Steps:**
    1.  Make sure you have uncommitted changes from the previous exercises.
    2.  Open the **Commit** tool window in IntelliJ (View > Tool Windows > Commit).
    3.  Stage your changed files.
    4.  In the Commit tool window, locate and click the **Ask Copilot to Generate Commit Message** icon (it looks like the Copilot logo).
    5.  Review the generated commit message. You can use it as a starting point for your actual commit.

### Exercise 3.5: Customizing Copilot with Instructions

* **Purpose:** To influence Copilot's suggestions for the entire project by creating an instructions file.
* **Steps:**
    1.  **Create Instruction File:**
        * In the root of your project, create a folder named `.github` if it doesn't exist.
        * Inside `.github`, create a new file named `copilot-instructions.md`.
    2.  **Define Instruction:**
        * Open `copilot-instructions.md` and add the following content:
            ```markdown
            # Copilot Instructions for Simple Weather CLI

            - For application logging, always use `java.util.logging.Logger`.
            - Avoid using `System.out.println` for logging inside methods.
            - When catching exceptions, log them at the `SEVERE` level.
            ```
    3.  **Apply Instruction:**
        * Open `src/main/java/com/weather/app/WeatherService.java`.
        * Select the `getWeather` method.
        * Start an Inline Chat and type: `/fix Add logging to this method for the start of the call and for any exceptions.`
    4.  **Observe Result:** Review the proposed changes. Verify that Copilot used `java.util.logging.Logger` as specified in your instructions file. The `.github/copilot-instructions.md` file provides persistent context for all of Copilot's actions in the repository.