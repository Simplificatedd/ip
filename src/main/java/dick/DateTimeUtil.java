package dick;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     * Accepts:
     *  - yyyy-MM-dd
     *  - yyyy-MM-dd HHmm
     *  - yyyy-MM-dd HH:mm
     *  - yyyy-MM-ddTHH:mm (from storage)
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

    public static boolean isStartOfDay(LocalDateTime dt) {
        return dt.toLocalTime().getHour() == 0 && dt.toLocalTime().getMinute() == 0;
    }

    /** For saving to storage. */
    public static String toStorageString(LocalDateTime dt) {
        if (isStartOfDay(dt)) {
            return dt.toLocalDate().toString(); // yyyy-mm-dd
        }
        return dt.withSecond(0).withNano(0).toString(); // yyyy-mm-ddTHH:mm
    }

    /** For display to user. */
    public static String toDisplayString(LocalDateTime dt) {
        if (isStartOfDay(dt)) {
            return dt.toLocalDate().format(DISPLAY_DATE);
        }
        return dt.format(DISPLAY_DATE_TIME);
    }
}