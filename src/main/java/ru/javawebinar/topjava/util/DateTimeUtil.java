package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T local, T startDate, T endDate) {
        return local.compareTo(startDate) >= 0 && local.compareTo(endDate) < 0;
    }

    public static LocalDate parseDate(String localDateStr){
        return LocalDate.parse(localDateStr, DATE_FORMATTER);
    }

    public static LocalTime parseTime(String localTimeStr){
        return LocalTime.parse(localTimeStr, TIME_FORMATTER);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

