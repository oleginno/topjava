package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil {

    private static String formatter = "dd-MM-yyyy HH:mm:ss";

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String getFormattedDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(formatter));
    }
}
