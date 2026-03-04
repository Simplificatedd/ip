package dick.task;

import dick.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = DateTimeUtil.parseDateTime(from);
        this.to = DateTimeUtil.parseDateTime(to);
    }

    public String getFrom() {
        return DateTimeUtil.toStorageString(from);
    }

    public String getTo() {
        return DateTimeUtil.toStorageString(to);
    }

    public LocalDate getFromDate() {
        return from.toLocalDate();
    }

    public LocalDate getToDate() {
        return to.toLocalDate();
    }

    @Override
    public String getType() {
        return "[E]";
    }

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + DateTimeUtil.toDisplayString(from)
                + " to: " + DateTimeUtil.toDisplayString(to) + ")";
    }
}