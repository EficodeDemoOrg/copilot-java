# GitHub Copilot in IntelliJ: Simple Weather CLI App Exercises

This document provides a series of exercises designed to help you learn and practice using the GitHub Copilot plugin within JetBrains IntelliJ IDEA. We will cover exploring the codebase, ideating new features, and implementing them using an advanced, Test-Driven Development (TDD) workflow.

---
## Strategies for Effective AI Interaction

To get the most out of Copilot, it helps to adopt a specific mindset and a few key strategies. This is not a "magic box"; it's a tool that you, the senior developer, must guide.

### Theme 1: Establish the Right Mindset (The Basics)

* **Treat Copilot as a brilliant junior colleague with a poor memory.** It can write excellent code for a specific, well-defined task but lacks long-term memory of past conversations or a deep understanding of your project's high-level goals. Your role is to provide the necessary context and direction for each task.

### Theme 2: Provide and Manage Context (Intermediate)

Because the AI's memory is limited to the current session, you must provide context strategically.

* **Use `.github/copilot-instructions.md` for Persistent Rules:** This file acts as Copilot's long-term memory for your entire project. Use it to define architectural principles, coding standards, and TDD rules that should apply to all its suggestions.
* **Use Specification Files for Task-Specific Context:** Before implementing a feature, define it in a simple markdown file (e.g., `docs/features/SailingAdvisor.md`). Attaching this file to the chat gives the AI a clear, unambiguous goal for the duration of the task.
* **Use `TODO.md` for State Tracking:** For complex features, ask the AI to break the plan into a `TODO.md` file. This serves as a shared checklist, allowing you and the AI to track progress without relying on chat history.

### Theme 3: Troubleshoot and Steer the Conversation (Advanced)

When the AI gets off track, steer it back with guiding questions instead of just correcting it.

* **Ask "Why?":** If Copilot gives a suboptimal suggestion, ask it to explain its reasoning. This can reveal a misunderstanding you can then correct.
    * **Example Prompt:** `"You suggested using System.out.println, but the project standard defined in my instructions is to use java.util.logging.Logger. Why did you make that choice?"`
* **Ask for Help Improving Its Instructions:** Turn a correction into a learning moment for the AI's configuration. This is more effective than just stating a rule.
    * **Example Prompt:** `"That's a good correction. Is this a rule I should add to the copilot-instructions.md file, or is there a better way to make this insight persist? Help me formulate the instruction in a way that is in line with best practices for instructing AI models."`
* **Know When to Start a New Chat:** If Copilot becomes confused or "stuck" on a previous idea, its context may be saturated. Starting a fresh chat is often the best solution.
* **Create a "Briefing Document" Before Switching Context:** To avoid losing progress when starting a new chat, ask the current chat to summarize its state.
    * **Example Prompt:** `"This chat is getting long. Please summarize our progress on the Sailing Advisor feature. Include the main goal, the key decisions we made, and the immediate next step from our TODO list. Format this as a concise brief that I can use to start a new chat."`

---

### Key GitHub Copilot Interaction Points in IntelliJ:

* **Copilot Chat Tool Window:** Your main interface for interacting with Copilot. You can open it from the right-hand sidebar.
    * **Chat Modes & Context:** The chat has different modes. The way you add file/folder context depends on the mode:
        * **Ask Mode:** Has a `+` button to add specific file(s) as context.
        * **Edits Mode:** Has a `+ Add Files` button to select files for modification.
        * **Agent Mode (Preview):** Has a `+ Add Context` button that allows you to add both files and folders.
* **Inline Chat:** Allows you to have a quick, contextual chat directly in the editor. Select a block of code and use the shortcut `Shift+Ctrl+I` (macOS) or `Shift+Ctrl+G` (Windows/Linux), or right-click and choose "Copilot > Start Inline Chat".
* **Context Participants (`@`):** Used in the Chat window to bring specific, broad contexts into your prompt.
    * `@project`: Gives Copilot context of your entire project.
* **Slash Commands (`/`):** Quick shortcuts for common tasks used within the Chat window or Inline Chat. Common commands include `/explain`, `/tests`, `/fix`, and `/doc`.
* **Code Completion (Ghost Text):** Automatic, inline suggestions that appear as you type. Press `Tab` to accept them.
* **Commit Message Generation:** A dedicated Copilot icon in the Git commit window that analyzes your staged changes and suggests a commit message.

### Prerequisites:

* JetBrains IntelliJ IDEA installed.
* The **GitHub Copilot** plugin installed and configured.
* The Simple Weather CLI project opened in IntelliJ.
* (For later exercises) A file named `docs/APIs/OpenWeather.md` that contains information or a schema for the OpenWeatherMap API.

---

## Section 1: Explore the Codebase

**Goal:** Use Copilot Chat with various context providers to quickly understand the project and its components.

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

### Exercise 1.2: Understanding a Specific Class (Adding File Context)

* **Purpose:** To dive deeper into the functionality of a single class.
* **Steps:**
    1.  Open the Copilot Chat window (in "Ask" or "Edits" mode).
    2.  Click the `+` or `+ Add Files` button.
    3.  In the file dialog, select `src/main/java/com/weather/app/OpenWeatherMapClient.java`.
    4.  With the file added to the context, type your question in the prompt:
        ```
        /explain Explain the role of this class. How does it fetch data from the API?
        ```
    5.  Analyze the response.

### Exercise 1.3: Explaining Dependencies (Adding File Context)

* **Purpose:** To understand the project's external libraries.
* **Steps:**
    1.  Open the Copilot Chat window.
    2.  Click the `+` button and add the `pom.xml` file to the context.
    3.  Type your prompt:
        ```
        /explain Explain the roles of the main dependencies listed here, such as Jackson and JUnit.
        ```
    4.  Review the explanation.

### Exercise 1.4: Generating Documentation (`/doc`)

* **Purpose:** To automatically generate documentation using Inline Chat.
* **Steps:**
    1.  Open the file `src/main/java/com/weather/app/WeatherService.java`.
    2.  Select the entire method signature and body of the `getWeather(String city)` method.
    3.  Start an Inline Chat session (`Shift+Ctrl+I` or right-click > Copilot > Start Inline Chat).
    4.  In the inline prompt that appears, type `/doc` and press Enter.
    5.  Copilot will generate the Javadoc. Review it and click "Accept" to add it to your code.

### Exercise 1.5: Explore Folder Contents (Agent Mode)

* **Purpose:** To get a summary of the code within an entire directory.
* **Steps:**
    1.  Open the Copilot Chat window and switch to **Agent** mode.
    2.  Click the `+ Add Context` button.
    3.  Select the `src/main/java/com/weather/app` folder.
    4.  With the folder added, type your prompt:
        ```
        Summarize the purpose of the Java classes inside the added application package.
        ```
    5.  Review Copilot's summary.

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

## Section 3: Implement and Test Features

**Goal:** Use Copilot's code generation capabilities to implement changes and improve test coverage efficiently.

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
    3.  Open the Copilot Chat window. With the code selected, the chat will automatically use it as context.
    4.  Type the prompt:
        ```
        /tests Generate a new JUnit test method for OpenWeatherMapClient. It should mock the HTTP call and a sample JSON response, then verify that fetchWeatherData correctly parses the data.
        ```
    5.  Copy the generated test method into your test file, adjust imports if needed, and run the tests (`mvn test`).

### Exercise 3.3: Creating a New Component (Agent Mode)

* **Purpose:** Use Copilot's Agent Mode to scaffold a new class from a detailed prompt.
* **Steps:**
    1.  Open the Copilot Chat window and switch to **Agent (Preview)** mode.
    2.  Type the following prompt:
        ```
        Create a new Java class named 'WeatherCache' in a new package 'com.weather.app.cache'. This class should have:
        1. A private static final ConcurrentHashMap<String, CacheEntry> for storage.
        2. A private static final long CACHE_DURATION_MS = 300000; // 5 minutes
        3. A nested static class 'CacheEntry' with 'WeatherData data' and 'long timestamp'.
        4. A public static method `put(String city, WeatherData data)`.
        5. A public static method `get(String city)` that returns an Optional<WeatherData> and checks for non-expired entries.
        Include Javadoc.
        ```
    3.  Copilot will analyze the request, propose a plan, and create the new file and package. Review and approve the changes.

### Exercise 3.4: Generating and Customizing Commit Messages

* **Purpose:** To leverage Copilot for drafting standardized Git commit messages and customize its behavior.
* **Part A: Generating a Message**
    1.  Make sure you have uncommitted changes from the previous exercises.
    2.  Open the **Commit** tool window in IntelliJ (View > Tool Windows > Commit).
    3.  Stage your changed files.
    4.  In the Commit tool window, locate and click the **Ask Copilot to Generate Commit Message** icon (it looks like the Copilot logo).
    5.  Review the generated commit message.
* **Part B: Customizing the Format**
    1.  Navigate to your IDE's settings: `File > Settings...` (or `IntelliJ IDEA > Settings...` on macOS).
    2.  Go to `Languages & Frameworks > GitHub Copilot`.
    3.  Find the text area for **Git Commit Instructions**.
    4.  Enter a custom instruction to enforce a specific format. For example, for Conventional Commits, type:
        ```
        Generate commit messages following the Conventional Commits specification. The format must be `<type>(<scope>): <subject>`. For example: `feat(api): add humidity field to weather data`.
        ```
    5.  Click **Apply** or **OK**.
    6.  Go back to the Commit window and click the Generate Commit Message icon again. Observe how the new message adheres to your custom format.

### Exercise 3.5: Analyzing Test Coverage (Agent Mode)

* **Purpose:** To use Copilot as a QA engineer to analyze the quality and completeness of existing tests.
* **Steps:**
    1.  Switch the Copilot Chat to **Agent** mode.
    2.  Click `+ Add Context` and add the entire `src/test` folder.
    3.  Provide a detailed prompt asking for analysis:
        ```
        I need you to act as a Quality Assurance engineer. Please analyze the test classes in the attached `src/test` folder. Your goal is to assess the quality and coverage of our existing JUnit tests.

        First, provide a high-level summary of what each test class seems to be covering. Then, identify any obvious gaps in our testing strategy. For example, are there public methods in the main application code that have no corresponding tests? Are we only testing the "happy path" and ignoring potential error conditions like network failures or invalid user input? Provide your analysis in a clear, structured format with specific recommendations for improvement.
        ```
    4.  Review Copilot's assessment to identify weak spots in your test suite.

### Exercise 3.6: Generating Edge Case Tests from a Schema

* **Purpose:** To explicitly use an API schema to generate tests for potential failure modes and edge cases.
* **Steps:**
    1.  Open the Copilot Chat window.
    2.  Add the following files to the context: `WeatherService.java`, `WeatherServiceTest.java`, and `docs/APIs/OpenWeather.md`.
    3.  Provide a prompt that forces Copilot to use the schema:
        ```
        Your task is to act as a QA engineer and design edge case tests for our `WeatherService`. **You must strictly use the attached `OpenWeather.md` API schema** to inform your suggestions. Look at the data types, required fields, and array structures in the schema.

        Based on that schema, what happens if:
        - The API returns a 404 "Not Found" error?
        - The API returns a valid response but a required field like `main` is missing?
        - The API returns a `temp` value that is not a number, contradicting the schema?

        Now, for the "Malformed JSON" scenario, generate a complete JUnit 5 test method. The mock response string in the test must be a deliberately broken version of the JSON structure described in the schema.
        ```
    4.  Incorporate the generated test into your `WeatherServiceTest.java` file.

### Exercise 3.7: Generating Mock Test Data from a Schema

* **Purpose:** To quickly create realistic, schema-compliant mock data for use in multiple unit tests.
* **Steps:**
    1.  Open the Copilot Chat in **Ask Mode**.
    2.  Click the `+` button and add the `docs/APIs/OpenWeather.md` file to the context.
    3.  Provide a prompt requesting varied mock data:
        ```
        I need to create mock data for my unit tests. Using the attached `OpenWeather.md` API schema as a strict reference, please generate three distinct Java strings representing valid JSON responses.

        - The first string should be a 'perfect' response with all fields populated with realistic data for a temperate climate.
        - The second string should represent a response for a city in a very cold climate (e.g., negative temperature, high wind).
        - The third string should represent a response where an optional field, like `wind.gust`, is not present.
        ```
    4.  Copy the generated JSON strings. You can now use them in your test files with Mockito (e.g., `when(mockClient.fetchRawData(anyString())).thenReturn(mockJsonString);`).

---

## Section 4: TDD Feature Implementation Workflow

**Goal:** Simulate a complete feature development lifecycle from ideation to implementation using a Test-Driven Development (TDD) approach. This section provides two paths: **Path A** for users with access to Copilot's **Agent Mode**, and **Path B** for users relying on **Ask and Edits Modes**.

### Step 4.1: Setup - Customizing Copilot Instructions

Before starting, it's crucial to guide Copilot's behavior for your project.

1.  **Create Instruction File:**
    * In the root of your project, create a folder named `.github` if it doesn't exist.
    * Inside `.github`, create a new file named `copilot-instructions.md`.
2.  **Define Instructions:**
    * Open `copilot-instructions.md` and add guidelines that are important for your project. For example:
        ```markdown
        # Copilot Instructions for Simple Weather CLI

        1.  All new features must be implemented following a TDD approach. Tests should always be written before the implementation code.
        2.  For logging, always instantiate and use `java.util.logging.Logger`. Avoid `System.out.println`.
        3.  Unit tests must use JUnit 5 and Mockito for mocking dependencies.
        4.  When generating code, ensure it is well-documented with Javadoc.
        ```
3.  **Troubleshooting Tip:** If Copilot repeatedly provides suggestions that contradict your project's standards, ask it directly: *"Why do you keep suggesting X when my project uses Y?"* Use its answer to refine your `copilot-instructions.md` file for better results.

### Step 4.2: Ideation & Exploration (Ask Mode)

Let's brainstorm a new feature based on potential user needs.

1.  Open the Copilot Chat window in **Ask Mode**.
2.  Click the `+` button and add your `docs/APIs/OpenWeather.md` file to the context.
3.  Type the following prompt:
    ```
    @project I want to add a feature to advise users on outdoor activities. A user might want to know if it's a good day for sailing or fishing. Considering the attached API schema, what data points like wind speed or cloud cover would be most useful? Suggest one concrete feature based on this data.
    ```
4.  Review Copilot's suggestion. For this exercise, let's assume it suggests a "Sailing Advisor" feature based on wind speed.

### Step 4.3: Feature Definition (User Story)

Distill the chosen idea into a clear, non-technical user story.

1.  Create a new file at `docs/features/SailingAdvisor.md`.
2.  Write the user story inside:
    ```markdown
    **Feature: Sailing Advisor**

    **As a user**, I want to know if it's a good day for sailing based on the current weather.
    **So that**, I can make plans safely and avoid dangerous conditions.

    **Acceptance Criteria:**
    * The app must fetch and display the current wind speed in km/h.
    * The app must provide a simple recommendation: "Good day for sailing" or "Not recommended for sailing" based on the wind speed.
    ```

### Step 4.4: Planning the Implementation (TDD Approach)

This is where the workflow splits based on whether you have Agent Mode.

* #### Path A: Using Agent Mode

    1.  Switch the Copilot Chat to **Agent (Preview)** mode.
    2.  Click `+ Add Context` and add the `docs/features/SailingAdvisor.md` file.
    3.  Provide the following prompt:
        ```
        I want to implement the feature described in the attached file using a TDD approach. Your first task is to create a detailed implementation plan. Assess the existing code and tests, then create a plan that outlines the **new tests we need to write first**, followed by the implementation changes. The plan should cover data models, services, and the main application output. Write this plan to a new file named `docs/plans/SailingAdvisor_Plan.md`.
        ```
    4.  The Agent will create the plan file. Review it for the next step.

* #### Path B: Using Ask Mode

    1.  Stay in **Ask Mode**.
    2.  Click the `+` button and add the following files to your prompt's context:
        * `docs/features/SailingAdvisor.md`
        * `src/main/java/com/weather/app/OpenWeatherMapClient.java`
        * `src/main/java/com/weather/app/WeatherService.java`
        * `src/test/java/com/weather/app/WeatherServiceTest.java`
    3.  Provide the following prompt:
        ```
        Based on the attached user story and source files, create a detailed implementation plan for this feature using a TDD approach. The plan must first outline the new unit tests required, and then the code changes needed to make those tests pass.
        ```
    4.  Copilot will generate the plan in the chat. Copy this plan and manually create a new file, `docs/plans/SailingAdvisor_Plan.md`, and paste the content.

### Step 4.5: Creating an Incremental TODO List

Break the high-level plan into small, actionable steps.

* #### Path A: Using Agent Mode

    1.  In the same Agent chat, provide a follow-up prompt:
        ```
        Based on the `SailingAdvisor_Plan.md` you just created, break it down into an incremental TODO list with simple, actionable steps. Each step should be small enough for a single Git commit. Write this list to a new file named `TODO.md`.
        ```

* #### Path B: Using Ask Mode

    1.  In a new chat, click `+` and add the `docs/plans/SailingAdvisor_Plan.md` file you created.
    2.  Provide the following prompt:
        ```
        Based on the attached implementation plan, create a simple, incremental TODO list in a new file named `TODO.md`. Each step should be a small, logical change suitable for a single commit.
        ```
    3.  Manually create the `TODO.md` file and paste the generated list.

### Step 4.6: Step-by-Step Implementation (The TDD Flow)

Now, execute the plan from your `TODO.md` file. The core loop is: **Write a failing test -> Write code to make it pass -> Refactor**.

1.  **Pick the First Task:** Look at the first item in `TODO.md`. It will likely be something like: "1. Add `windSpeed` field to `WeatherData` class." The TDD equivalent is testing this new field.

2.  **Write the Failing Test First:**
    * **Path A (Agent):** You can instruct the agent: `Implement step 1 from the TODO.md. Start by modifying WeatherDataTest.java to include a test for the new windSpeed field. This test should fail initially.`
    * **Path B (Inline Chat):** Open `WeatherDataTest.java`. Select a relevant area, start an Inline Chat, and type: `/tests Add a new test to verify that a windSpeed field can be set and retrieved on the WeatherData object.` Run the test; it should fail because the field and methods don't exist.
    * **Pro Tip: Consider Edge Cases.** When writing tests for the new feature, don't just test the "happy path." Use what you learned in Exercises 3.6 and 3.7. Ask Copilot to help you write tests for potential edge cases related to your new feature. For the Sailing Advisor, this could mean testing what happens if the wind speed is missing from the API response, is exactly on the boundary of your recommendation threshold, or is an invalid value.

3.  **Write Code to Pass the Test:**
    * **Path A (Agent):** The agent should automatically proceed to modify `WeatherData.java` to make the test pass after you approve the test creation.
    * **Path B (Inline Chat):** Open `WeatherData.java`. Use code completion or an inline prompt (`/fix Add the necessary field and methods to satisfy the failing test in WeatherDataTest.java`) to add the `windSpeed` field and its getter/setter. Run the tests again; they should now pass.

4.  **Commit Your Changes:**
    * You have completed a full TDD cycle for one small step.
    * Open the **Commit** tool window. Stage your changes.
    * Click the **Ask Copilot to Generate Commit Message** icon.
    * Approve or edit the message (e.g., `feat: Add windSpeed to WeatherData model and tests`) and commit.

5.  **Repeat for All TODOs:** Continue this process for every item in your `TODO.md`. For more complex steps like modifying service logic, the loop remains the same: add a failing test to the service test class first, then implement the service logic to make it pass.