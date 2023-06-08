package com.nhanph.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final String DATE_FORMAT = "yyyyMMdd";

    public static LocalDate format(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }

    private DateUtil(){}
}
