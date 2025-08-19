Of course. Here is the cheatsheet in markdown format without any emojis.

## GitHub Copilot in IntelliJ: Cheatsheet

This cheatsheet summarizes key strategies, commands, and workflows for using GitHub Copilot effectively within IntelliJ IDEA.

-----

### Core Strategies & Mindset

  * **Mindset**: Treat Copilot as a **brilliant junior colleague**. It's great at specific tasks but needs clear, contextual instructions from you, the senior developer.
  * **Context is King**: Copilot's memory is short. Provide context strategically:
      * **Project-Wide Rules**: Use `.github/copilot-instructions.md` to define persistent rules like coding standards, TDD principles, or preferred libraries.
      * **Task-Specific Goals**: For a new feature, write a simple spec (e.g., `docs/features/MyFeature.md`) and add it to the chat context.
      * **State Tracking**: For complex tasks, ask Copilot to generate a `TODO.md` to track progress step-by-step.
  * **Steer When it Drifts**: If suggestions are off, guide it back instead of just correcting.
      * **Ask "Why?"**: `Why did you suggest X when the standard is Y?` This helps uncover misunderstandings.
      * **Refine Instructions**: `That's a good correction. How can I update my copilot-instructions.md file to reflect this rule permanently?`
      * **Start a Fresh Chat**: If a conversation becomes confused, just start a new one. Ask the old chat for a summary first if needed.

-----

### Key Interactions in IntelliJ

| Feature | Description | How to Use |
| :--- | :--- | :--- |
| **Copilot Chat** | The main chat window for prompts. | Open from the right-hand sidebar. Use the `+` button to add file/folder context. |
| **Inline Chat** | A pop-up chat for quick edits in your code. | Select code, then press `Shift+Ctrl+I` (macOS) or `Shift+Ctrl+G` (Windows/Linux). |
| **Context (`@`)** | Broaden the scope of your prompt. | `@project` includes your entire project's context in the prompt. |
| **Slash Cmds (`/`)** | Shortcuts for common tasks. | `/explain`, `/tests`, `/fix`, `/doc` inside any chat window. |
| **Code Completion** | "Ghost text" suggestions as you type. | Press `Tab` to accept the suggestion. |
| **Commit Messages** | AI-generated commit messages. | Click the Copilot icon in the Git **Commit** tool window. |

-----

### Common Tasks & Prompts

#### Code Exploration & Understanding

  * **Get a project overview**:
    ```
    @project /explain What is the main purpose of this project?
    ```
  * **Explain a specific class**:
    1.  Open the **Copilot Chat**.
    2.  Click `+` and add the Java file (e.g., `WeatherService.java`).
    3.  Prompt: `/explain the role of this class.`
  * **Generate documentation for a method**:
    1.  Select the entire method in the editor.
    2.  Open **Inline Chat** (`Shift+Ctrl+I`).
    3.  Type `/doc` and press Enter.

-----

#### Implementation & Testing

  * **Generate unit tests for a class**:
    1.  Select the class content in the editor.
    2.  Open **Copilot Chat**.
    3.  Prompt: `/tests Generate JUnit tests for the selected code.`
  * **Create a new class from scratch (Agent Mode)**:
    ```
    Create a new Java class named 'WeatherCache' in 'com.weather.app.cache'. It should have a ConcurrentHashMap, a put() method, and a get() method that returns an Optional.
    ```
  * **Generate mock data from a schema**:
    1.  Open **Copilot Chat**.
    2.  Click `+` and add your API schema file (e.g., `OpenWeather.md`).
    3.  Prompt: `Using the attached schema, generate 3 valid JSON response strings for my unit tests.`
  * **Generate tests for edge cases**:
    1.  Add your schema file and the class-under-test to the chat context.
    2.  Prompt: `Using the schema, suggest edge cases to test. What if a required field is missing or the API returns a 404? Now, generate a test for the 'missing field' scenario.`

-----

#### Git & Version Control

  * **Generate a standard commit message**:
    1.  Stage your files in the **Commit** window.
    2.  Click the **Ask Copilot to Generate Commit Message** icon.
  * **Enforce a custom commit format (e.g., Conventional Commits)**:
    1.  Go to `Settings > Languages & Frameworks > GitHub Copilot`.
    2.  In **Git Commit Instructions**, add your rule:
        ```
        Generate messages using the Conventional Commits format: <type>(<scope>): <subject>.
        ```

-----

### Advanced TDD Workflow

This workflow uses Copilot to follow a strict Test-Driven Development process from idea to implementation.

1.  **SETUP: Define the Rules**.

      * Create a `.github/copilot-instructions.md` file.
      * Add your core principles: `1. All features must follow a TDD approach. 2. Always use java.util.logging.Logger, not System.out.println. 3. Tests must use JUnit 5.`

2.  **DEFINE: Write the User Story**.

      * Create a simple markdown file for the feature (e.g., `docs/features/SailingAdvisor.md`).
      * Clearly state the goal and acceptance criteria.

3.  **PLAN: Create the Implementation Plan**.

      * Start a chat and add the user story file (`SailingAdvisor.md`) to the context.
      * Prompt: `Based on the attached user story, create a TDD implementation plan. The plan must list the new tests we need to write first, followed by the code changes.`

4.  **TODO: Break Down the Plan**.

      * In the same chat, ask Copilot: `Now, break that plan down into a simple, incremental TODO list and create a TODO.md file.`

5.  **EXECUTE: The TDD Loop**.

      * For each item in `TODO.md`:
          * **A. Write a Failing Test**: Instruct Copilot (or use Inline Chat) to generate a test for the step. For example: `Add a test to WeatherDataTest for a new 'windSpeed' field.` Run it to confirm it fails.
          * **B. Write Code to Pass**: Use code completion or Inline Chat (`/fix`) to implement the feature just enough to make the test pass.
          * **C. Commit**: Use the commit message generator to create a descriptive message (e.g., `feat(model): add windSpeed to WeatherData`) and commit your changes.
          * **D. Repeat**: Move to the next item on the `TODO.md` list.
