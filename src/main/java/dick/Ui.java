package dick;

import dick.task.Task;

import java.util.List;
import java.util.Scanner;

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

    public void showWelcome() {
        System.out.println(GREETING);
    }

    public void showGoodbye() {
        System.out.println(GOODBYE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void showAdded(Task task) {
        System.out.println("Added: " + task);
    }

    public void showDeleted(Task task, int remaining) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remaining + " tasks in the list.");
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }
}