package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Helper {

    public static LocalTime convertStringToTimeWithDefaultFormat(String time) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, format);
    }
}
