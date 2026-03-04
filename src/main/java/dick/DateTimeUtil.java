package dick;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility methods for parsing, formatting, and storing dates/times used by tasks.
 */
public class DateTimeUtil {
    private static final DateTimeFormatter DISPLAY_DATE =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private static final DateTimeFormatter INPUT_DATE_TIME_HHMM =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_DATE_TIME_HH_COLON_MM =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() { }

    /**
     * Parses a date/time string into a {@code LocalDateTime}.
     * Accepts:
     * yyyy-MM-dd
     * yyyy-MM-dd HHmm
     * yyyy-MM-dd HH:mm
     * yyyy-MM-ddTHH:mm (storage format)
     *
     * @param raw Raw date/time string.
     * @return Parsed LocalDateTime.
     * @throws IllegalArgumentException If the input cannot be parsed.
     */
    public static LocalDateTime parseDateTime(String raw) {
        String s = raw.trim();

        // from storage (or user)
        try {
            if (s.contains("T")) {
                return LocalDateTime.parse(s);
            }
        } catch (DateTimeParseException ignored) { }

        // date only
        try {
            LocalDate d = LocalDate.parse(s);
            return d.atStartOfDay();
        } catch (DateTimeParseException ignored) { }

        // date + time (HHmm)
        try {
            return LocalDateTime.parse(s, INPUT_DATE_TIME_HHMM);
        } catch (DateTimeParseException ignored) { }

        // date + time (HH:mm)
        try {
            return LocalDateTime.parse(s, INPUT_DATE_TIME_HH_COLON_MM);
        } catch (DateTimeParseException ignored) { }

        throw new IllegalArgumentException("Invalid date/time. Use: yyyy-mm-dd OR yyyy-mm-dd HHmm");
    }

    /**
     * Checks if the given date-time is exactly at 00:00 (used to represent date-only input).
     *
     * @param dt Date-time to check.
     * @return True if at start of day.
     */
    public static boolean isStartOfDay(LocalDateTime dt) {
        return dt.toLocalTime().getHour() == 0 && dt.toLocalTime().getMinute() == 0;
    }

    /**
     * Converts a date-time into a stable storage format.
     *
     * @param dt Date-time to convert.
     * @return yyyy-MM-dd for date-only values, otherwise yyyy-MM-ddTHH:mm.
     */
    public static String toStorageString(LocalDateTime dt) {
        if (isStartOfDay(dt)) {
            return dt.toLocalDate().toString(); // yyyy-mm-dd
        }
        return dt.withSecond(0).withNano(0).toString(); // yyyy-mm-ddTHH:mm
    }

    /**
     * Converts a date-time into a user-friendly display string.
     *
     * @param dt Date-time to format.
     * @return Display string such as "Oct 15 2019" or "Oct 15 2019, 6:00PM".
     */
    public static String toDisplayString(LocalDateTime dt) {
        if (isStartOfDay(dt)) {
            return dt.toLocalDate().format(DISPLAY_DATE);
        }
        return dt.format(DISPLAY_DATE_TIME);
    }
}