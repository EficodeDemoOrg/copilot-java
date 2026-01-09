---
mode: agent
model: Claude Sonnet 4 (copilot)
description: 'Translate the GitHub Copilot exercises to a specific programming language and update file references accordingly.'
tools: ['edit','search','usages']
---
# Translate the GitHub Copilot exercises to a specific programming language.
# Ensure that all file references and paths are updated to match the new project's structure while maintaining the original exercise content.

## Instructions:
1. Read the original exercise file located at `github_copilot_exercises.md`.
2. Identify all file references and paths within the content.
3. Update the file references and paths to align with the new project's structure for the specified programming language.
4. Maintain the original exercise content, ensuring that the translation is accurate and contextually appropriate for the target language.
5. Output the translated content in a format suitable for the new project structure.

## Additional Instructions:
- If a referenced file does not exist in the target project, suggest a suitable alternative or note it for manual review.
- Adapt code snippets and instructions to follow the conventions and idioms of the target programming language.
- Maintain the original intent and difficulty of each exercise.
- Provide a summary of changes made (e.g., updated paths, translated code).
- If any instruction cannot be translated, flag it clearly for manual attention.

## Target Language:
- Specify the target programming language for the translation (e.g., Python, Java, etc.).

## Example Output:
- The translated content should reflect the structure and conventions of the target programming language while preserving the instructional integrity of the original exercises.