package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil {

    public final static DateTimeFormatter formatter;
    public final static DateTimeFormatter formatterHtml5;

    static {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
        formatterHtml5 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String getFormattedDateTime(LocalDateTime dateTime, DateTimeFormatter fmt) {
        return dateTime.format(fmt);
    }

    public static LocalDateTime getLocalDateTime(String dateTime, DateTimeFormatter fmt) {
        return LocalDateTime.parse(dateTime, fmt);
    }
}
