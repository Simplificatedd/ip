package dick.task;

/**
 * Represents a to-do task (a task without any associated date/time).
 */
public class Todo extends Task {
    /**
     * Creates a to-do task.
     *
     * @param description Task description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the task type tag used in the string representation.
     *
     * @return "[T]" for todo tasks.
     */
    @Override
    public String getType() {
        return "[T]";
    }
}
