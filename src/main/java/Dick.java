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
        List<Task> tasks = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(GREETING);

            while (true) {
                String input = scanner.nextLine().trim();
                if (shouldExit(input)) {
                    break;
                }

                handleCommand(input, tasks);
            }

            System.out.println(GOODBYE);
        }
    }

    private static boolean shouldExit(String input) {
        return input.equalsIgnoreCase("bye");
    }

    private static void handleCommand(String input, List<Task> tasks) {
        String lowerInput = input.toLowerCase();

        if (lowerInput.startsWith("add ")) {
            addTask(input.substring(4).trim(), tasks);
            return;
        }

        if (lowerInput.equals("list")) {
            printTasks(tasks);
            return;
        }

        if (lowerInput.startsWith("mark ")) {
            markTask(input.substring(5).trim(), tasks);
            return;
        }

        if (lowerInput.startsWith("unmark ")) {
            unmarkTask(input.substring(7).trim(), tasks);
            return;
        }
        if (lowerInput.startsWith("todo ")) {
            String description = input.substring(5).trim();
            tasks.add(new Todo(description));
            System.out.println("Added: " + tasks.get(tasks.size() - 1));
            return;
        }

        if (lowerInput.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            String[] parts = rest.split(" /by ", 2);
            if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
                System.out.println("Invalid format. Use: deadline <desc> /by <time>");
                return;
            }

            tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
            System.out.println("Added: " + tasks.get(tasks.size() - 1));
            return;
        }

        if (lowerInput.startsWith("event ")) {
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
            System.out.println("Added: " + tasks.get(tasks.size() - 1));
            return;
        }

        System.out.println(input);
    }

    private static void addTask(String description, List<Task> tasks) {
        tasks.add(new Task(description));
        System.out.println("Added: " + description);
    }

    private static void printTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void markTask(String numberText, List<Task> tasks) {
        int index = parseTaskIndex(numberText, tasks.size());
        if (index == -1) {
            System.out.println("Invalid Task Number");
            return;
        }

        Task task = tasks.get(index);
        if (task.isDone()) { // boolean naming style: isXxx :contentReference[oaicite:1]{index=1}
            System.out.println("Task is already marked as done.");
            return;
        }

        task.mark();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    private static void unmarkTask(String numberText, List<Task> tasks) {
        int index = parseTaskIndex(numberText, tasks.size());
        if (index == -1) {
            System.out.println("Invalid Task Number");
            return;
        }

        Task task = tasks.get(index);
        if (!task.isDone()) {
            System.out.println("Task is already unmarked.");
            return;
        }

        task.unmark();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    private static int parseTaskIndex(String numberText, int taskCount) {
        try {
            int index = Integer.parseInt(numberText) - 1;
            if (index < 0 || index >= taskCount) {
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
