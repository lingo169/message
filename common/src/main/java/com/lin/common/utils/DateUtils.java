package com.lin.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String fullDateFormat() {
        LocalDateTime rightnow = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(rightnow);
    }

    public static String dateFormat() {
        LocalDateTime rightnow = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(rightnow);
    }

    public static String timeFormat() {
        LocalDateTime rightnow = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("HHmmss").format(rightnow);
    }

    public static String fullDateFormat(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(date);
    }

    /**
     * MM/DD/YYYY HH:mm  to yyyyMMddHHmmss
     * 09/23/20211000
     *
     * @param format
     * @return
     */
    public static String FormatConvert(String format) {

        if (format == null || format.length() == 0) {
            return format;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(format.substring(6, 10))
                .append(format.substring(0, 2))
                .append(format.substring(3, 5))
                .append(format.substring(11, 13))
                .append(format.substring(14, 16));

        return sb.toString();
    }

}
