package dick;

import dick.task.Task;
import dick.task.Todo;
import dick.task.Deadline;
import dick.task.Event;
import dick.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Dick {
    private static final String LINE =
            "____________________________________________________________";

    private static final String GREETING =
            LINE + System.lineSeparator()
                    + " Hello! I'm Dick" + System.lineSeparator()
                    + " What can I do for you?" + System.lineSeparator()
                    + LINE;

    private static final String GOODBYE =
            LINE + System.lineSeparator()
                    + " Bye. Hope to see you again soon!" + System.lineSeparator()
                    + LINE;

    public static void main(String[] args) {
        Storage storage = new Storage("data/tasks.txt");
        List<Task> tasks = new ArrayList<>(storage.load());


        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(GREETING);

            while (true) {
                String input = scanner.nextLine().trim();
                if (shouldExit(input)) {
                    break;
                }

                handleCommand(input, tasks, storage);
            }

            System.out.println(GOODBYE);
        }
    }

    private static boolean shouldExit(String input) {
        return input.equalsIgnoreCase("bye");
    }

    private static void handleCommand(String input, List<Task> tasks, Storage storage) {
        String lowerInput = input.toLowerCase();

        if (lowerInput.equals("add")) {
            System.out.println("Please provide a task description.");
            return;
        }

        if (lowerInput.equals("mark")) {
            System.out.println("Please specify a task number to mark.");
            return;
        }

        if (lowerInput.equals("unmark")) {
            System.out.println("Please specify a task number to unmark.");
            return;
        }

        if (lowerInput.equals("todo")) {
            System.out.println("Invalid format. Use: todo <desc>");
            return;
        }

        if (lowerInput.equals("deadline")) {
            System.out.println("Invalid format. Use: deadline <desc> /by <time>");
            return;
        }

        if (lowerInput.equals("event")) {
            System.out.println("Invalid format. Use: event <desc> /from <start> /to <end>");
            return;
        }

        if (lowerInput.startsWith("add ")) {
            addTask(input.substring(4).trim(), tasks, storage);
            return;
        }

        if (lowerInput.equals("list")) {
            printTasks(tasks);
            return;
        }

        if (lowerInput.startsWith("mark ")) {
            setStatus(input.substring(5).trim(), tasks, true, storage);
            return;
        }

        if (lowerInput.startsWith("unmark ")) {
            setStatus(input.substring(7).trim(), tasks, false, storage);
            return;
        }

        if (lowerInput.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isBlank()) {
                System.out.println("Invalid format. Use: todo <desc>");
                return;
            }
            tasks.add(new Todo(description));
            storage.save(tasks);
            System.out.println("Added: " + tasks.get(tasks.size() - 1));
            return;
        }


        if (lowerInput.startsWith("deadline ")) {
            handleAddDeadline(input, tasks, storage);
            return;
        }

        if (lowerInput.startsWith("event ")) {
            handleAddEvent(input, tasks, storage);
            return;
        }

        if (lowerInput.startsWith("delete ")) {
            handleDelete(input.substring(7).trim(), tasks, storage);
            return;
        }

        if (lowerInput.equals("delete")) {
            System.out.println("Please specify a task number to delete.");
            return;
        }


        System.out.println("Command not recognized");
    }

    private static void handleAddDeadline(String input, List<Task> tasks, Storage storage) {
        String rest = input.substring(9).trim();
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            System.out.println("Invalid format. Use: deadline <desc> /by <time>");
            return;
        }

        tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        storage.save(tasks);
        System.out.println("Added: " + tasks.get(tasks.size() - 1));
    }

    private static void handleAddEvent(String input, List<Task> tasks, Storage storage) {
        String rest = input.substring(6).trim();
        String[] firstSplit = rest.split(" /from ", 2);
        if (firstSplit.length < 2 || firstSplit[0].isBlank()) {
            System.out.println("Invalid format. Use: event <desc> /from <start> /to <end>");
            return;
        }

        String description = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        if (secondSplit.length < 2 || secondSplit[0].isBlank() || secondSplit[1].isBlank()) {
            System.out.println("Invalid format. Use: event <desc> /from <start> /to <end>");
            return;
        }

        tasks.add(new Event(description, secondSplit[0].trim(), secondSplit[1].trim()));
        storage.save(tasks);
        System.out.println("Added: " + tasks.get(tasks.size() - 1));
    }

    private static void handleDelete(String numberText, List<Task> tasks, Storage storage) {
        int index = parseTaskIndex(numberText, tasks.size());
        if (index == -1) {
            System.out.println("Invalid Task Number");
            return;
        }

        Task removed = tasks.remove(index);
        storage.save(tasks);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }


    private static void addTask(String description, List<Task> tasks, Storage storage) {
        if (description.isBlank()) {
            System.out.println("Please provide a task description.");
            return;
        }
        tasks.add(new Task(description));
        storage.save(tasks);
        System.out.println("Added: " + description);
    }


    private static void printTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void setStatus(String numberText, List<Task> tasks, boolean isDone, Storage storage) {
        int index = parseTaskIndex(numberText, tasks.size());
        if (index == -1) {
            System.out.println("Invalid Task Number");
            return;
        }

        Task task = tasks.get(index);
        if (task.isDone() == isDone) {
            if (isDone) {
                System.out.println("Task is already marked as done.");
            } else {
                System.out.println("Task is already unmarked.");
            }
            return;
        }

        if (isDone) {
            task.mark();
            storage.save(tasks);
            System.out.println("Nice! I've marked this task as done:");
        } else {
            task.unmark();
            storage.save(tasks);
            System.out.println("OK, I've marked this task as not done yet:");
        }

        System.out.println(task);
    }

    private static int parseTaskIndex(String numberText, int taskCount) {
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

}
