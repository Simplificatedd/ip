package dick;

import dick.storage.Storage;
import dick.task.Deadline;
import dick.task.Event;
import dick.task.Task;
import dick.task.Todo;

import java.util.List;
/**
 * Entry point of the Dick task management chatbot.
 * Coordinates user interaction, parsing, task operations, and persistence.
 */
public class Dick {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new chatbot instance that loads tasks from the given file path.
     *
     * @param filePath Path to the save file.
     */
    public Dick(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        List<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
    }

    /**
     * Runs the main command loop until the user exits.
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            Parser.ParsedCommand pc = Parser.parse(input);

            switch (pc.commandWord) {
                case "bye":
                    isExit = true;
                    break;
                case "list":
                    ui.showTasks(tasks.asUnmodifiableList());
                    break;
                case "todo":
                    handleTodo(pc.args);
                    break;
                case "deadline":
                    handleDeadline(pc.args);
                    break;
                case "event":
                    handleEvent(pc.args);
                    break;
                case "mark":
                    handleMark(pc.args, true);
                    break;
                case "unmark":
                    handleMark(pc.args, false);
                    break;
                case "delete":
                    handleDelete(pc.args);
                    break;
                case "on":
                    handleOnDate(pc.args);
                    break;
                case "find":
                    handleFind(pc.args);
                    break;
                default:
                    ui.showMessage("Command not recognized");
                    break;
            }
        }

        ui.showGoodbye();
    }

    /** Handles the {@code todo} command. */
    private void handleTodo(String args) {
        if (args.isBlank()) {
            ui.showMessage("Invalid format. Use: todo <desc>");
            return;
        }
        tasks.add(new Todo(args));
        storage.save(tasks.asUnmodifiableList());
        ui.showAdded(tasks.get(tasks.size() - 1));
    }

    /** Handles the {@code deadline} command. */
    private void handleDeadline(String args) {
        String[] parts = args.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            ui.showMessage("Invalid format. Use: deadline <desc> /by <yyyy-mm-dd> or deadline <desc> /by <yyyy-mm-dd HHmm>");
            return;
        }

        try {
            tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        } catch (IllegalArgumentException e) {
            ui.showMessage(e.getMessage());
            return;
        }

        storage.save(tasks.asUnmodifiableList());
        ui.showAdded(tasks.get(tasks.size() - 1));
    }

    /** Handles the {@code event} command. */
    private void handleEvent(String args) {
        String[] firstSplit = args.split(" /from ", 2);
        if (firstSplit.length < 2 || firstSplit[0].isBlank()) {
            ui.showMessage("Invalid format. Use: event <desc> /from <start> /to <end>");
            return;
        }

        String description = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        if (secondSplit.length < 2 || secondSplit[0].isBlank() || secondSplit[1].isBlank()) {
            ui.showMessage("Invalid format. Use: event <desc> /from <start> /to <end>");
            return;
        }

        tasks.add(new Event(description, secondSplit[0].trim(), secondSplit[1].trim()));
        storage.save(tasks.asUnmodifiableList());
        ui.showAdded(tasks.get(tasks.size() - 1));
    }

    /**
     * Handles the {@code mark} and {@code unmark} commands.
     *
     * @param args Task number string.
     * @param isDone True to mark done, false to unmark.
     */
    private void handleMark(String args, boolean isDone) {
        int index = parseTaskIndex(args, tasks.size());
        if (index == -1) {
            ui.showMessage("Invalid Task Number");
            return;
        }

        Task task = tasks.get(index);
        if (task.isDone() == isDone) {
            ui.showMessage(isDone ? "Task is already marked as done." : "Task is already unmarked.");
            return;
        }

        if (isDone) {
            task.mark();
            storage.save(tasks.asUnmodifiableList());
            ui.showMarked(task);
        } else {
            task.unmark();
            storage.save(tasks.asUnmodifiableList());
            ui.showUnmarked(task);
        }
    }

    /** Handles the {@code delete} command. */
    private void handleDelete(String args) {
        int index = parseTaskIndex(args, tasks.size());
        if (index == -1) {
            ui.showMessage("Invalid Task Number");
            return;
        }

        Task removed = tasks.remove(index);
        storage.save(tasks.asUnmodifiableList());
        ui.showDeleted(removed, tasks.size());
    }

    /** Handles the stretch {@code on <date>} command for filtering by date. */
    private void handleOnDate(String args) {
        if (args.isBlank()) {
            ui.showMessage("Invalid format. Use: on <yyyy-mm-dd>");
            return;
        }

        java.time.LocalDate target;
        try {
            target = java.time.LocalDate.parse(args.trim());
        } catch (java.time.format.DateTimeParseException e) {
            ui.showMessage("Invalid date. Use: yyyy-mm-dd");
            return;
        }

        java.util.List<dick.task.Task> matches = new java.util.ArrayList<>();
        for (dick.task.Task t : tasks.asUnmodifiableList()) {
            if (t instanceof dick.task.Deadline d) {
                if (d.getByDate().equals(target)) {
                    matches.add(t);
                }
            } else if (t instanceof dick.task.Event ev) {
                // “occurring on date” → if date is within [fromDate, toDate]
                java.time.LocalDate start = ev.getFromDate();
                java.time.LocalDate end = ev.getToDate();
                if ((target.equals(start) || target.isAfter(start)) &&
                        (target.equals(end) || target.isBefore(end))) {
                    matches.add(t);
                }
            }
        }

        if (matches.isEmpty()) {
            ui.showMessage("No deadlines/events on " + target);
            return;
        }

        ui.showMessage("Here are the matching tasks in your list:");
        ui.showTasks(matches);
    }

    /** Handles the {@code find} command. */
    private void handleFind(String args) {
        if (args.isBlank()) {
            ui.showMessage("Invalid format. Use: find <keyword>");
            return;
        }
        ui.showFindResults(tasks.find(args.trim()));
    }

    /**
     * Parses a 1-based task number into a 0-based index.
     *
     * @param numberText User input for task number.
     * @param taskCount Current number of tasks.
     * @return 0-based index, or {@code -1} if invalid.
     */
    private int parseTaskIndex(String numberText, int taskCount) {
        if (numberText.isBlank()) {
            return -1;
        }
        try {
            int index = Integer.parseInt(numberText) - 1;
            return (index >= 0 && index < taskCount) ? index : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Application entry point.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new Dick("data/tasks.txt").run();
    }
}