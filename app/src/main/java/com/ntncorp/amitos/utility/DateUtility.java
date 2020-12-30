package com.ntncorp.amitos.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtility {

    private static final String BUSINESS_START_DATE = "Jun 8, 2020";
    private static final String PATTERN = "MMM d, yyyy";


    public static int compareDate(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2);
    }

    public static boolean isDateAllowed(LocalDate currentDate) {
        return currentDate.compareTo(convertDateStringToLocalDate(BUSINESS_START_DATE, PATTERN)) >= 0 ? true : false;
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate localDate = LocalDate.parse(modifiedDate);
        return localDate;
    }


    public static LocalDate convertDateStringToLocalDate(String strDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        return localDate;
    }

    public static int getMonthInNumber(LocalDate date) {
        return date.getMonthValue();
    }

    public static String getMonthInShort(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    public static String getMonthInFull(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static int getYear(LocalDate date) {
        return date.getYear();
    }

    public static List<String> getAllDatesOfMonth(int month) {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);  // index 0 for Jan
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        dates.add(df.format(cal.getTime()));
        for (int i = 1; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            dates.add(df.format(cal.getTime()));
        }
        return dates;
    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}


