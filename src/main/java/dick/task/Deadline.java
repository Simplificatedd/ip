package dick.task;

import dick.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        this.by = DateTimeUtil.parseDateTime(by);
    }

    public String getBy() {
        return DateTimeUtil.toStorageString(by);
    }

    public LocalDate getByDate() {
        return by.toLocalDate();
    }

    @Override
    public String getType() {
        return "[D]";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.toDisplayString(by) + ")";
    }
}