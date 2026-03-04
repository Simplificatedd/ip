package dick.task;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    protected final String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks the task as done. */
    public void mark() {
        this.isDone = true;
    }

    /** Marks the task as done. */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns whether the task is done.
     *
     * @return True if done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return "[X] " if done, otherwise "[ ] ".
     */
    public String getStatus() {
        return isDone ? "[X] " : "[ ] ";
    }

    /**
     * Returns the task description.
     *
     * @return Description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type tag used in {@link #toString()}.
     *
     * @return Type tag, e.g., "[T]".
     */
    public String getType() {
        return "[?]";
    }

    /**
     * Returns the string representation shown to the user.
     *
     * @return User-facing string.
     */
    @Override
    public String toString() {
        return getType() + getStatus() + description;
    }
}
