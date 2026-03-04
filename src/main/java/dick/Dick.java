package dick;

import dick.storage.Storage;
import dick.task.Deadline;
import dick.task.Event;
import dick.task.Task;
import dick.task.Todo;

import java.util.List;

public class Dick {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Dick(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        List<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
    }

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
                default:
                    ui.showMessage("Command not recognized");
                    break;
            }
        }

        ui.showGoodbye();
    }

    private void handleTodo(String args) {
        if (args.isBlank()) {
            ui.showMessage("Invalid format. Use: todo <desc>");
            return;
        }
        tasks.add(new Todo(args));
        storage.save(tasks.asUnmodifiableList());
        ui.showAdded(tasks.get(tasks.size() - 1));
    }

    private void handleDeadline(String args) {
        String[] parts = args.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            ui.showMessage("Invalid format. Use: deadline <desc> /by <time>");
            return;
        }
        tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        storage.save(tasks.asUnmodifiableList());
        ui.showAdded(tasks.get(tasks.size() - 1));
    }

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

    public static void main(String[] args) {
        new Dick("data/tasks.txt").run();
    }
}