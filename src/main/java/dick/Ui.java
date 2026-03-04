package dick;

import dick.task.Task;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all interactions with the user (input and output).
 */
public class Ui {
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

    private final Scanner scanner = new Scanner(System.in);

    /** Displays the welcome message. */
    public void showWelcome() {
        System.out.println(GREETING);
    }

    /** Displays the goodbye message. */
    public void showGoodbye() {
        System.out.println(GOODBYE);
    }

    /** Prints a horizontal divider line. */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Reads a full command line from the user.
     *
     * @return The trimmed command string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints a general message to the user.
     *
     * @param message Message to print.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints a list of tasks in numbered form.
     *
     * @param tasks Tasks to print.
     */
    public void showTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Prints confirmation that a task was added.
     *
     * @param task The task that was added.
     */
    public void showAdded(Task task) {
        System.out.println("Added: " + task);
    }

    /**
     * Prints confirmation that a task was deleted.
     *
     * @param task Deleted task.
     * @param remaining Remaining number of tasks.
     */
    public void showDeleted(Task task, int remaining) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remaining + " tasks in the list.");
    }

    /**
     * Prints confirmation that a task was marked as done.
     *
     * @param task Task that was marked.
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Prints confirmation that a task was unmarked.
     *
     * @param task Task that was unmarked.
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * Prints the result of a {@code find} command.
     *
     * @param matches Matching tasks.
     */
    public void showFindResults(List<Task> matches) {
        if (matches.isEmpty()) {
            System.out.println("No matching tasks found.");
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }
    }
}