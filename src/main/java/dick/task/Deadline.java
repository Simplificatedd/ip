package dick.task;

import dick.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task that must be completed by a specific date/time.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Creates a deadline task.
     *
     * @param description Task description.
     * @param by Date/time string provided by the user or storage.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = DateTimeUtil.parseDateTime(by);
    }

    /**
     * Returns the deadline date/time in storage format.
     *
     * @return Storage-formatted deadline.
     */
    public String getBy() {
        return DateTimeUtil.toStorageString(by);
    }

    /**
     * Returns the date portion of the deadline (used for filtering).
     *
     * @return Deadline date.
     */
    public LocalDate getByDate() {
        return by.toLocalDate();
    }

    /**
     * Returns the task type tag used in the string representation.
     *
     * @return "[D]" for deadline tasks.
     */
    @Override
    public String getType() {
        return "[D]";
    }

    /**
     * Returns the formatted string representation shown to the user.
     *
     * @return Display string including the deadline date/time.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.toDisplayString(by) + ")";
    }
}