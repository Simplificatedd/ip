package dick.task;

import dick.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents an event task with a start and end date/time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates an event task.
     *
     * @param description Task description.
     * @param from Start date/time string.
     * @param to End date/time string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = DateTimeUtil.parseDateTime(from);
        this.to = DateTimeUtil.parseDateTime(to);
    }

    /** @return Start date/time in storage format. */
    public String getFrom() {
        return DateTimeUtil.toStorageString(from);
    }

    /** @return End date/time in storage format. */
    public String getTo() {
        return DateTimeUtil.toStorageString(to);
    }

    /** @return End date/time in storage format. */
    public LocalDate getFromDate() {
        return from.toLocalDate();
    }

    /** @return End date of the event. */
    public LocalDate getToDate() {
        return to.toLocalDate();
    }

    /**
     * Returns the task type tag used in the string representation.
     *
     * @return "[E]" for event tasks.
     */
    @Override
    public String getType() {
        return "[E]";
    }

    /**
     * Returns the formatted string representation shown to the user.
     *
     * @return Display string including the event date/time.
     */
    @Override
    public String toString() {
        return super.toString()
                + " (from: " + DateTimeUtil.toDisplayString(from)
                + " to: " + DateTimeUtil.toDisplayString(to) + ")";
    }
}