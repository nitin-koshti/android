package com.ntncorp.amitos.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewDateUtility {

    private static final SimpleDateFormat sdf_YYYYmmDD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf_MMMdYYYY = new SimpleDateFormat("MMM d, yyyy");
    private static final SimpleDateFormat sdf_EEEE = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat sdf_MMMM = new SimpleDateFormat("MMMM");
    //    private static final SimpleDateFormat sdf_YYYY = new SimpleDateFormat("YYYY");
    private static final String BUSINESS_START_DATE = "Jun 8, 2020";
    private static final List<String> WEENENDS = Arrays.asList("SATURDAY", "SUNDAY");

    public static Date convertStringToDate(String dateString) {
        Date date = null;
        try {
            date = sdf_MMMdYYYY.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString_YYYYmmDD(Date date) {
        return sdf_YYYYmmDD.format(date);
    }

    public static String convertFinalString_MMMdYYYY_To_YYYYmmDD(String finalString) {
        return convertDateToString_YYYYmmDD(convertStringToDate(finalString));
    }

    public static int compareDate(String date1, String date2) {
        Date d1 = convertStringToDate(date1);
        Date d2 = convertStringToDate(date2);
        return d1.compareTo(d2);
    }

    public static boolean isDateAllowed(String currentDateString) {
        Date currentDate = convertStringToDate(currentDateString);
        Date businessStartDate = convertStringToDate(BUSINESS_START_DATE);

        return currentDate.compareTo(businessStartDate) >= 0 ? true : false;
    }

    public static int getMonthInNumber(String strDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(convertStringToDate(strDate));
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }

    public static String getMonthInText(String strDate) {
        return sdf_MMMM.format(convertStringToDate(strDate)).toUpperCase();
    }

    public static String getDayInText(String strDate) {
        return sdf_EEEE.format(convertStringToDate(strDate)).toUpperCase();
    }

    /*public static String getYearInText(String strDate) {
        return sdf_YYYY.format(convertStringToDate(strDate)).toUpperCase();
    }*/

    public static List<String> getAllDatesOfMonth(String strDate) {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, getMonthInNumber(strDate) - 1);  // index 0 for Jan
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        dates.add(sdf_YYYYmmDD.format(cal.getTime()));
        for (int i = 1; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            dates.add(sdf_YYYYmmDD.format(cal.getTime()));
        }
        return dates;
    }

    public static boolean isWeekend(String day) {
        return WEENENDS.contains(day);
    }
}
