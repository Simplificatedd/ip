import java.util.ArrayList;
import java.util.Scanner;

public class Dick {


    public static void main(String[] args) {
        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Boolean> isDone = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String numStr = "";

        System.out.println("____________________________________________________________\n" +
                " Hello! I'm Dick\n" +
                " What can I do for you?\n" +
                "____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();
            String optionsCheck = input.toLowerCase();
            if (optionsCheck.equals("bye")) {
                break;
            }
            if (optionsCheck.startsWith("add ")) {
                String task = input.substring(4);
                tasks.add(task);
                isDone.add(false);
                System.out.println("Added: " + task);
            }
            else if (optionsCheck.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    String mark = isDone.get(i) ? "[X] " : "[ ] ";
                    System.out.println((i + 1) + "." + mark + tasks.get(i));
                }
            }
            else if (optionsCheck.startsWith("mark ")) {
                numStr = input.substring(5).trim();

                try {
                    int index = Integer.parseInt(numStr) - 1;

                    if (index < 0 || index >= tasks.size()) {
                        System.out.println("Invalid Task Number");
                    } else if (isDone.get(index) == true) {
                        System.out.println("Task is already marked as done.");
                    } else {
                        isDone.set(index, true);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("[X] " + tasks.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Task Number");
                }
            }

            else if (optionsCheck.startsWith("unmark ")) {
                numStr = input.substring(7).trim();

                try {
                    int index = Integer.parseInt(numStr) - 1;

                    if (index < 0 || index >= tasks.size()) {
                        System.out.println("Invalid Task Number");
                    } else if (isDone.get(index) == false) {
                        System.out.println("Task is already unmarked");
                    } else {
                        isDone.set(index, false);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("[ ] " + tasks.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Task Number");
                }
            }
            else {
                System.out.println(input);
            }

        }

    System.out.println("____________________________________________________________\n"+
            " Bye. Hope to see you again soon!\n"+
            "____________________________________________________________\n");
    }
}