import java.util.Scanner;

public class Dick {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n" +
                            " Hello! I'm Dick\n" +
                            " What can I do for you?\n" +
                            "____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(input);

        System.out.println("____________________________________________________________\n" +
                            " Bye. Hope to see you again soon!\n" +
                            "____________________________________________________________\n");


    }
}
