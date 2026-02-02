import java.util.ArrayList;
import java.util.Scanner;

public class Dick {


    public static void main(String[] args) {
        ArrayList<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________\n" +
                            " Hello! I'm Dick\n" +
                            " What can I do for you?\n" +
                            "____________________________________________________________");

        while(true) {
            String input = scanner.nextLine();
            String optionsCheck = input.toLowerCase();
            if (optionsCheck.equals("bye")) {
                break;
            }
            if (optionsCheck.startsWith("add ")) {
                String task = input.substring(4);
                tasks.add(task);
                System.out.println("Added: " + task);
            }
            else if (optionsCheck.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(i + 1 + ". " + tasks.get(i));
                }
            }
            else {
                System.out.println(input);
            }
        }

        System.out.println("____________________________________________________________\n" +
                            " Bye. Hope to see you again soon!\n" +
                            "____________________________________________________________\n");
    }


}
