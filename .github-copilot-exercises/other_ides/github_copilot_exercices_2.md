# GitHub Copilot Comprehensive Training Exercises - Session 2: Agent-Based Development Workflow (JetBrains)

Welcome to Session 2! You'll now dive into advanced agent-based development workflows. These exercises implement a structured approach focusing on **multi-agent collaboration** and **complex feature implementation**.

> **💡 About Custom Agents**
>
> JetBrains IDEs now support custom agents similar to VS Code! You can create specialized agent personas by defining `.agent.md` files in the `.github/agents/` directory. These agents appear in the agent picker dropdown alongside built-in agents (Ask, Edit, Agent, Plan).
>
> **What this means for you:**
> - Create **custom agents** in `.github/agents/` as `.agent.md` files
> - Each agent defines a specialized role (Lead Developer, Implementer, QA Specialist)
> - Select agents from the **agent picker dropdown** in Copilot Chat
> - Agents maintain their role and instructions throughout the chat session
> - You can also use **reusable prompt files** (`.prompt.md`) in `.github/prompts/` for specific commands

## Model Recommendations

Different agents work best with different AI models:

- **Lead Developer**: Claude Sonnet 4/4.5 or GPT-4 (better at detailed planning and research)
- **Implementer**: Claude Sonnet 4/4.5 (superior code generation and precision)
- **Deep reasoning and debugging**: Gemini 2.5 Pro or o1

**Always verify before running a prompt:**
1. Check the model selector shows your preferred model for that task
2. Manually switch models if needed
3. Keep track of which role you're using in each chat session

## Exercise 1: Complete Weather Forecast System Implementation

### Scenario: Multi-Agent Epic Development

You've been tasked with adding a comprehensive weather forecast system to the Simple Weather CLI Application. This comprehensive exercise demonstrates the full agent-based development workflow from requirements analysis through implementation and completion. This will require 5-day forecast support, weather alerts, historical data, and enhanced CLI output formatting.

### Phase 1: Multi-Agent Feature Planning

#### Part 1.1: Requirements Analysis with Ask Agent

1. **Create Context Understanding**
   - Open a new Copilot Chat and set the context: "I am analyzing requirements for a weather forecast system"
   - Ask: `@project Analyze the current CLI architecture. How would adding forecast capabilities impact the existing weather data system?`
   - Follow up: `What are the main challenges and considerations for adding 5-day forecasts to this Java CLI weather application?`
   - Request: `Identify all Java classes that would need modification and new classes that need creation for forecast support`

2. **API Integration and Data Flow Analysis**
   - Ask: `From an API perspective, what additional endpoints and data structures should I implement for weather forecasts in Java?`
   - Request: `How should forecast data flow through the existing WeatherService and OpenWeatherMapClient classes?`
   - Analyze: `What changes would be required to the WeatherData model to support forecast information?`

3. **Integration Impact Assessment**
   - Ask: `How will forecast functionality affect existing classes in src/main/java/com/weather/app/ and the CLI output format?`
   - Request: `What backwards compatibility considerations do I need for existing weather data output?`
   - Evaluate: `What additional Maven dependencies would be recommended for enhanced weather data processing in Java?`

**Deliverable:** Create a `docs/REQUIREMENT-ANALYSIS.md` file documenting all findings, challenges, and recommendations.

#### Part 1.2: Strategic Planning with Plan Agent

1. **Create High-Level Implementation Strategy**
   - Start a fresh Copilot Chat session
   - Select **Plan** from the agent picker
   - Attach the `docs/REQUIREMENT-ANALYSIS.md` file as context
   - Request: `Create a strategic plan for implementing weather forecasts in this Java CLI application. Break down the work into logical phases and identify dependencies.`

   **The Plan agent will:**
   - Analyze the requirements and create a high-level implementation strategy
   - Identify major phases of work (e.g., API integration, data models, CLI output, testing)
   - Suggest the order of implementation
   - Highlight potential risks and dependencies

2. **Review and Refine the Strategy**
   - Review the strategic plan provided by the Plan agent
   - Ask follow-up questions to clarify any ambiguous areas
   - Request: `What are the critical milestones for this forecast implementation?`
   - Ask: `What would be a good MVP (Minimum Viable Product) for weather forecast support?`

**Deliverable:** Document the strategic plan in `docs/epic_weather_forecast/STRATEGIC_PLAN.md`

#### Part 1.3: Detailed Task Planning with Lead Developer Agent

1. **Convert Strategy into Executable Tasks**
   - Start a fresh Copilot Chat session
   - In the chat interface, locate the agent picker dropdown
   - Select **"Lead Developer"** custom agent from the picker
   - Attach both `REQUIREMENT-ANALYSIS.md` and `STRATEGIC_PLAN.md` as context
   - Use the reusable prompt: `/lead-plan`

   **The Lead Developer will:**
   - Convert the strategic plan into detailed, actionable tasks
   - Generate numbered task files (01_task_name.md, 02_task_name.md, etc.)
   - Document technical decisions in a decision log
   - Create a task manifest

   **Task numbering:** Tasks are numbered sequentially (01, 02, 03...) to enforce execution order. Each task is designed to be completed without blocking on others.

2. **Review the Detailed Plan**
   - Read each task file to ensure it makes sense
   - Verify tasks are small enough (each should be completable in one session)
   - Check that file paths use project root (`/`) not placeholders
   - Ensure tasks align with the strategic plan from Part 1.2

   **Deliverable:**
   - Output files in `docs/epic_[name]/`:
   - `plans/IMPLEMENTATION_PLAN.md`
   - `plans/DECISION_LOG.md`
   - `tasks/01_[name].md`, `tasks/02_[name].md`, etc.
   - `MANIFEST.md`

#### Part 1.4: Experimenting with Custom Planning Prompts (Optional)

Instead of using the structured prompt file, you can experiment with generating the plan using your own custom prompts. This is a great way to practice prompt engineering and compare outputs.

1. **Start a new chat session** with your preferred model (e.g., Claude Sonnet 4, GPT-4)
2. **Provide Context**: Add `#file:REQUIREMENT-ANALYSIS.md`
3. **Craft Your Own Prompt**: Try variations like:
   > "Based on the attached requirements analysis, create a detailed implementation plan for adding weather forecast support to this Java CLI application. Break it into 5-7 numbered, sequential task files. Each task should focus on a specific component (API client extensions, data models, service layer, CLI output formatting, etc.). Use Java 11+, Maven, OpenWeatherMap API, and JUnit 5 best practices. Generate a MANIFEST.md listing all files you create."

4. **Compare Results**: 
   - How does your custom prompt compare to the structured prompt?
   - Which produces clearer task definitions?
   - What prompt patterns work best for planning?

### Phase 2: Collaborative Implementation Workflow

#### Part 2.1: Implement the Tasks

1. **Start a new Copilot Chat session**
2. **Select the Implementer agent**: Click the agent picker dropdown and select **"Implementer"**
3. **Add task context**: Add `#file:docs/epic_weather_analytics/tasks/01_[task_name].md`
4. Run: `/implement`

**The Implementer will:**
- Read and summarize what it plans to do
- List all files it will create/modify
- Ask for your approval to proceed

**Your responsibility:**
- Review the implementation plan
- Confirm it matches the task specification
- Check that it follows Java and Maven project conventions
- Approve with "yes" or request clarification

**Once approved, the Implementer will:**
- Execute the task step by step
- Run Maven build and linter on modified files
- Execute tests if applicable
- Report completion status

#### Part 2.2: Handle Implementation Issues

**If the task succeeds:**
- Review the code changes
- Test manually by running Maven tests and the CLI application
- Move to the next task (repeat Part 2.2 with `02_[task_name].md`)

**If verification fails:**
- Read the Implementer's explanation
- Minor issues: Let it proceed if non-critical items failed
- Major issues: Ask the agent to propose solutions

**If the Implementer gets blocked:**
The agent will present what went wrong and propose solutions.

You can:
- Approve a proposed solution
- Provide an alternative approach
- Modify the task specification
- Go back to Lead Developer for task revision

#### Part 2.3: Complete Remaining Tasks

Repeat Part 2.2 for each task file in sequence (02, 03, etc.) until all tasks in the epic are complete.

**Important:** Each task should be run in a fresh Implementer session with just that task file as context.

#### Part 2.4: Experimenting with Custom Implementation Prompts (Optional)

Want to try a different implementation approach? Create your own prompt!

1. **Start a new chat session**
2. **Add task context**: `#file:docs/epic_weather_forecast/tasks/01_[task_name].md`
3. **Craft your prompt**: Try variations like:
   > "Act as a senior Java developer. Implement the attached task using Java 11+, Maven, OpenWeatherMap API, and JUnit 5 best practices. List all files you'll modify, explain your approach, and then implement it step by step. Follow the existing patterns for API clients, service classes, data models, and exception handling. Test your implementation with proper unit tests."

4. **Apply changes**: Copy code blocks and apply them to your workspace

This hands-on approach helps you understand how to guide an agent through complex Java development tasks.

#### Part 2.5: Complete the Epic

After the last task succeeds:

1. **Stay in Implementer agent** or start new session with Implementer selected
2. **Attach context:**
   - `#file:docs/epic_weather_forecast/plans/IMPLEMENTATION_PLAN.md`
   - `#file:docs/epic_weather_forecast/MANIFEST.md`
3. **Request:** "Generate a completion report for this epic."

The Implementer generates a completion report with:
- Summary of work completed
- Any deviations from plan
- Recommendations for future epics

## Exercise 2: Comprehensive Testing and QA

### Scenario: Agent-Driven Quality Assurance

The weather forecast system from Exercise 1 is feature-complete, but it hasn't been tested! Your task is to use a QA-focused agent workflow to create and implement a comprehensive test suite, ensuring the new forecast features are robust, reliable, and bug-free.

### Phase 1: Test Strategy and Planning

#### Part 1.0: Create a Custom QA Agent

Before beginning test analysis, create a specialized QA agent to focus on testing concerns.

1.  **Create the QA Agent File**
    *   In your project, create the directory `.github/agents/` if it doesn't exist
    *   Create a new file: `.github/agents/QA Specialist.agent.md`

2.  **Define the QA Agent**
    *   Define the Agent as required. Refer to the existing custom agents for structure.
    *   Focus the agent on Java testing with JUnit 5, Mockito, Maven Surefire, and API testing 

3.  **Verify Agent Availability**
    *   Open Copilot Chat
    *   Click the agent picker dropdown
    *   Verify **"QA Specialist"** appears in the list of available agents

**Deliverable:** Custom QA Specialist agent ready to use for test analysis.

#### Part 1.1: Test Analysis with QA Specialist Agent

1. **Analyze the Feature Implementation**
   - Open a new Copilot Chat session
   - Select **"QA Specialist"** from the agent picker
   - Ask: `@project Based on the recently added weather forecast system, analyze what needs testing.`
   - Follow up: `Generate a comprehensive list of test cases covering unit, integration, and edge case scenarios for forecast data retrieval, parsing, and CLI output formatting.`
   - Request: `What testing frameworks and setup do we need for this Java/Maven project with JUnit 5?`

2. **Java Testing Framework and Setup Recommendations**
   - Ask: `Given the Java project structure with Maven, what additional testing patterns would you recommend for CLI applications?`
   - Request: `Outline the steps and configuration needed to enhance the existing JUnit 5 and Maven Surefire setup for testing the new forecast features.`
   - Analyze: `How should we test CLI argument parsing and output formatting for the new forecast commands?`

**Deliverable:** Create a `docs/TEST-ANALYSIS.md` file documenting the test cases, edge cases, and setup plan.

#### Part 1.2: Test Strategy

For a more strategic approach, you can add a high-level test strategy phase:

1. **Create High-Level Test Strategy**
   - Start a fresh Copilot Chat session
   - Select **Plan** from the agent picker
   - Attach the `docs/TEST-ANALYSIS.md` file as context
   - Request: `Create a strategic test plan for the weather analytics system. Organize tests by priority (critical, high, medium) and type (unit, integration, e2e). Identify dependencies between test suites.`

2. **Review and Prioritize**
   - Review the test strategy provided
   - Identify which tests are essential for MVP vs. nice-to-have
   - Request: `What tests are absolutely critical before deploying to production?`

**Deliverable:** Document the test strategy in `docs/epic_weather_forecast_testing/TEST_STRATEGY.md`

#### Part 1.3: Manual Plan Generation (Alternative Approach)

As an alternative to using structured agents, you can experiment with generating the plan manually using the built-in Agent mode. This is a great way to understand how to craft effective prompts and compare the outputs of different models.

1. **Start a new chat session** and select **Agent** from the agent picker
2. **Provide Context**: Drag both `TEST-ANALYSIS.md` file into the chat
3. **Prompt the Agent**: Use a custom prompt to generate the plan. For example:
   > "Based on the attached `TEST-ANALYSIS.md`, create a detailed, step-by-step implementation plan for the "weather_forecast_testing" epic. Focus on Java/Maven testing with JUnit 5 and Mockito for unit tests, plus WireMock for API testing. Break the work into small, numbered, sequential task files. For each task, define a clear goal and acceptance criteria. Also generate a MANIFEST.md file listing all the files you will create."
4. **Create Files Manually**: Based on the agent's output, create the directory structure (`docs/epic_weather_forecast_testing/`) and the corresponding plan, task, and manifest files yourself.

This approach gives you more fine-grained control and is an excellent exercise in prompt engineering.

**Deliverable:** Document the test strategy in `docs/epic_weather_forecast_testing/TEST_STRATEGY.md`

#### Part 1.4: Detailed Test Plan Generation with QA Specialist Agent

1. **Create the Test Implementation Plan**
   - Start a new Copilot Chat session and select **"QA Specialist"** from the agent picker
   - Provide both `TEST-ANALYSIS.md` and `TEST_STRATEGY.md` files as context
   - Use the prompt: `/lead-plan Create a detailed, step-by-step test implementation plan based on the provided analysis and strategy. The epic name is "weather_forecast_testing".`
   
   **Note:** The `/lead-plan` prompt works with any custom agent. The QA Specialist will use its testing expertise to create test-focused tasks.

2. **Review the Generated Plan**
    - The agent creates `docs/epic_weather_forecast_testing/` containing:
       - `plans/IMPLEMENTATION_PLAN.md`: Testing strategy
       - `plans/DECISION_LOG.md`: Testing framework and approach decisions
       - `tasks/01_[name].md`, `tasks/02_[name].md`, etc.: Sequenced tasks like:
           - Setting up JUnit 5 and Mockito testing framework configuration in Maven
           - Writing unit tests for ForecastData model classes
           - Writing integration tests for weather forecast API client methods
           - Creating API reliability test cases (network failures, rate limits, etc.)
           - Implementing edge case tests for forecast parsing and validation
       - `MANIFEST.md`: Manifest of generated files
   - Verify that the tasks are logical, sequential, and appropriately sized
   - Confirm that all API reliability concerns and edge cases from earlier analysis are covered

#### Part 1.5: Experimenting with Custom Test Planning (Optional)

Try creating the test plan with your own prompt:

1. **Start a new chat session**
2. **Add context**: `#file:docs/TEST-ANALYSIS.md`
3. **Custom prompt example**:
   > "Based on the attached test analysis, create a step-by-step test implementation plan for the 'weather_forecast_testing' epic. Break into numbered task files: setup test infrastructure with JUnit 5 and Maven, unit tests for forecast data classes, integration tests for WeatherService forecast methods, API reliability tests, edge case tests, etc. Use JUnit 5, Mockito, WireMock for HTTP testing, and Java testing patterns. Generate MANIFEST.md."

4. **Create files manually** based on the output
5. **Compare** with the structured prompt approach

### Phase 2: Test Implementation and Debugging

#### Part 2.1: Implement Test Tasks

1. **Execute Tasks with the Implementer**
   - For each task file (starting with `01_...`), start a **new chat session**
   - Select **"Implementer"** from the agent picker
   - Add task: `#file:docs/epic_weather_forecast_testing/tasks/01_[task_name].md`
   - Request: "Implement this task."
   - Review the Implementer's plan and approve it by typing "yes"
   - The agent will write test files, Maven configuration, and helper code

#### Part 2.2: Experimenting with Custom Test Implementation

Try implementing tests with custom prompts:

1. **Start a new chat session**
2. **Add task**: `#file:docs/epic_weather_forecast_testing/tasks/01_[task_name].md`
3. **Custom prompt**:
   > "Based on the attached task, generate the necessary JUnit 5 test code for Java 11+. Use proper test structure, follow AAA pattern (Arrange-Act-Assert), use Mockito for mocking, and include proper test naming conventions. List all files you'll create or modify before implementing."

4. **Apply changes manually** from the agent's response

#### Part 2.3: Running Tests and Fixing Bugs

This is the core of the QA workflow.

1. **Run the Newly Created Tests**
   - After the Implementer creates a test file, run it from your terminal:
     ```bash
     # Run all tests
     mvn test
     
     # Run specific test class
     mvn test -Dtest=WeatherServiceTest
     
     # Run single test method
     mvn test -Dtest=WeatherServiceTest#testGetWeatherValidCity
     
     # Run with coverage (if configured)
     mvn test jacoco:report
     ```

2. **Run Code Quality Checks**
   ```bash
   # Compile and verify
   mvn clean verify
   
   # Check style (if configured)
   mvn checkstyle:check
   
   # Build the project
   mvn clean package
   ```

3. **If Tests Pass:**
   - Congratulations! Move to the next task in the sequence.

4. **If Tests Fail (Bug Found):**
   - Start a **new chat session**
   - Paste the full error output into the chat
   - Ask: `@project This JUnit test is failing with the error below. Analyze the relevant Java code and the test to identify the bug. Propose a fix using Java best practices and proper exception handling.`
   - Include the error output in your message
   - Review the agent's analysis and proposed fix
   - Apply the fix, re-run tests to confirm they pass
   - Commit the fix: `git add . && git commit -m "Fix: [description]"`

5. **Complete the Test Suite**
   - Repeat the implement-run-fix cycle for all tasks in `docs/epic_weather_forecast_testing/tasks/`
   - Ensure all tests pass before marking the epic complete
   - Run the full suite: `mvn test` to verify everything works together
   - Run code quality checks: `mvn clean verify` to ensure code quality

#### Part 2.4: Generate Test Completion Report

1. **Complete the Testing Epic**
   - Start a **new chat session** or continue in Implementer mode
   - Select **"Implementer"** from the agent picker (if starting new session)
   - Add context: `#file:docs/epic_weather_forecast_testing/plans/IMPLEMENTATION_PLAN.md` and `#file:docs/epic_weather_forecast_testing/MANIFEST.md`
   - Request: "Generate the completion report for the testing epic."

## Tips for Success

- **One agent, one task, one chat session** - Don't mix contexts
- **Double-check agent and model** - Every time you switch threads, verify the agent picker and model picker show the correct selections
- **Use Claude Sonnet 4/4.5 for implementation** - It's superior for code generation and detailed planning
- **Start fresh when stuck** - If an agent loses context or becomes confused, start a new chat session with clear context
- **Read everything** - The agents generate detailed documentation for a reason
- **Commit frequently** - After each successful task or epic
- **Run Java tools frequently** - Use `mvn test`, `mvn clean verify`, `mvn checkstyle:check` after each successful task or epic
- **Follow code style rules** - The project has established coding standards with checkstyle and Maven conventions
- **Trust but verify** - Agents follow patterns but can make mistakes with Java syntax and Maven configuration
- **When in doubt, escalate** - Go back to higher abstraction levels

This experimental system will evolve. When you find issues, use Copilot to improve the prompts and share your enhancements with the community.