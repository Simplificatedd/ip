# Dick - User Guide

Dick is a **desktop chatbot for managing your tasks**, optimized for use via a Command Line Interface (CLI). If you can type fast, Dick can help you track todos, deadlines, and events faster than most GUI apps.

---

## Quick Start

1. Ensure you have **Java 17** installed.
2. Download the latest `dick.jar` from the [releases page](../../releases).
3. Open a terminal in the folder containing the jar and run:
```
   java -jar dick.jar
```
4. Type a command and press **Enter** to execute it.

---

## Features

### 1. Add a Todo
Adds a simple task with no date attached.

**Format:** `todo DESCRIPTION`

**Example:**
```
todo Read a book
```
```
Got it. I've added this task:
  [T][ ] Read a book
Now you have 1 task(s) in the list.
```

---

### 2. Add a Deadline
Adds a task with a specific due date (and optional time).

**Format:** `deadline DESCRIPTION /by DATE` or `deadline DESCRIPTION /by DATE TIME`
- `DATE` must be in `yyyy-mm-dd` format (e.g. `2025-12-31`)
- `TIME` (optional) must be in `HHmm` format (e.g. `2359`)

**Examples:**
```
deadline Submit assignment /by 2025-04-10
deadline Submit assignment /by 2025-04-10 2359
```

---

### 3. Add an Event
Adds a task with a start and end date.

**Format:** `event DESCRIPTION /from START /to END`

**Example:**
```
event Team meeting /from 2025-04-10 /to 2025-04-11
```

---

### 4. List All Tasks
Shows all tasks currently in your list.

**Format:** `list`

**Example output:**
```
Here are the tasks in your list:
1. [T][ ] Read a book
2. [D][ ] Submit assignment (by: Apr 10 2025)
3. [E][ ] Team meeting (from: Apr 10 2025 to: Apr 11 2025)
```

> **Task type icons:** `[T]` = Todo, `[D]` = Deadline, `[E]` = Event  
> **Status icons:** `[X]` = done, `[ ]` = not done

---

### 5. Mark a Task as Done

**Format:** `mark TASK_NUMBER`

**Example:** `mark 2`

---

### 6. Unmark a Task

**Format:** `unmark TASK_NUMBER`

**Example:** `unmark 2`

---

### 7. Delete a Task

**Format:** `delete TASK_NUMBER`

**Example:** `delete 3`

---

### 8. Find Tasks by Keyword
Searches task descriptions for a keyword (case-sensitive).

**Format:** `find KEYWORD`

**Example:**
```
find book
```
```
Here are the matching tasks in your list:
1. [T][ ] Read a book
```

---

### 9. Filter Tasks by Date
Lists all deadlines due on a given date, and all events occurring on that date.

**Format:** `on DATE` (where `DATE` is `yyyy-mm-dd`)

**Example:**
```
on 2025-04-10
```

---

### 10. Exit

**Format:** `bye`

Your tasks are automatically saved when you exit.

---

## Command Summary

| Command    | Format                                         |
|------------|------------------------------------------------|
| Todo       | `todo DESCRIPTION`                             |
| Deadline   | `deadline DESCRIPTION /by DATE [TIME]`         |
| Event      | `event DESCRIPTION /from START /to END`        |
| List       | `list`                                         |
| Mark       | `mark TASK_NUMBER`                             |
| Unmark     | `unmark TASK_NUMBER`                           |
| Delete     | `delete TASK_NUMBER`                           |
| Find       | `find KEYWORD`                                 |
| On date    | `on DATE`                                      |
| Exit       | `bye`                                          |

---

## Data Storage

Tasks are saved automatically to `data/tasks.txt` after every change. The file is created on first run — no setup needed.