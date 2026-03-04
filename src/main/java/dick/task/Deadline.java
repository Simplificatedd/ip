package dick.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate by;

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy");

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by); // expects yyyy-mm-dd
    }

    // Returns the date in ISO format for saving to file.
    public String getBy() {
        return by.toString(); // yyyy-mm-dd
    }

    @Override
    public String getType() {
        return "[D]";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }
}