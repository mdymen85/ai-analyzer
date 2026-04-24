---
name: data-analyzer
description: Use this skill when the user asks to visualize, summarize, or detect anomalies in application logs or database exports.
---

# Instructions
1. First, call the `list_available_logs` tool to see what files exist.
2. If the user wants a summary, use the `read_log_file` tool.
3. **Format Rule:** Always output a Markdown table for any numerical data found.
4. If an error is found, suggest a fix based on the stack trace.

# Examples
User: "Check the logs for any errors in the last hour."
Assistant: "I'll check the available logs now... [Calls list_available_logs]"