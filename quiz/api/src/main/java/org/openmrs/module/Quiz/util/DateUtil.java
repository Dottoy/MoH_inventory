package org.openmrs.module.Quiz.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtil {
    public static LocalDateTime formatStringToDate(String dateString) {
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime tarehe = null;
        tarehe = LocalDateTime.parse(dateString, formatter);
        return tarehe;
    }
}
