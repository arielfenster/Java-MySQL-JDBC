package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    private static DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalTime convertStringToTimeWithDefaultFormat(String time) {
        return LocalTime.parse(time, defaultFormat);
    }

    public static LocalTime getCurrentTimeWithDefaultFormat() {
        return LocalTime.parse(LocalTime.now().format(defaultFormat));
    }
}
