package dick.task;

/**
 * Represents a to-do task (a task without any associated date/time).
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a to-do task.
     *
     * @param description Task description.
     */
    @Override
    public String getType() {
        return "[T]";
    }
}
