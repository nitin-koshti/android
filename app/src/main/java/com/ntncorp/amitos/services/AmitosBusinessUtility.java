package com.ntncorp.amitos.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ntncorp.amitos.model.Sms;
import com.ntncorp.amitos.utility.MessageUtility;
import com.ntncorp.amitos.utility.NewDateUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AmitosBusinessUtility {

    MessageUtility messageUtility;
//    private static final String PATTERN = "MMM d, yyyy";


    static ArrayList<Sms> smsList = new ArrayList<>();

    public AmitosBusinessUtility(Context context, Activity activity) {
        messageUtility = new MessageUtility(context, activity);
        smsList = messageUtility.readPaytmMessages();
    }

    public int totalAmountByDate(Date date) {
        int amount = 0;
        if (smsList != null && !smsList.isEmpty()) {
            String message = null;
            String tempAmount = null;
            for (Sms sms : smsList) {
                message = sms.getMsg();
//                Log.i("Message", message);

                String dateArray[] = message.substring(message.indexOf("at"), message.indexOf(".")).split("at ")[1].trim().split(" ", 4);

                String finalDate = buildFinalDate(dateArray);

//                Log.i("finalDate", finalDate); // Jun 13,2020 5:50 PM
                if(NewDateUtility.isDateAllowed(finalDate)
                        && NewDateUtility.compareDate(
                                NewDateUtility.convertDateToString_YYYYmmDD(date),
                                NewDateUtility.convertFinalString_MMMdYYYY_To_YYYYmmDD(finalDate)) ==0){

                    tempAmount = message.split("Rs")[1].split(" ")[1].trim();
                    amount = amount + Integer.parseInt(tempAmount);
                }

                /*if (isValidDate(finalDate)
                        && DateUtility.compareDate(DateUtility.convertDateToLocalDate(date), DateUtility.convertDateStringToLocalDate(finalDate, PATTERN)) == 0) {


                    tempAmount = message.split("Rs")[1].split(" ")[1].trim();
                    amount = amount + Integer.parseInt(tempAmount);
                }*/
            }
        }
//        Log.i("Today's Total Amount", amount + "");
        return amount;
    }

    public int currentMonthTotal(Date date) {
        int amount = 0;
//        String currentMonth = DateUtility.convertDateToLocalDate(date).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String currentMonth = NewDateUtility.getMonthInText(NewDateUtility.convertDateToString_YYYYmmDD(date));
        if (smsList != null && !smsList.isEmpty()) {
            String message = null;
            String tempAmount = null;
            for (Sms sms : smsList) {
                message = sms.getMsg();
//                Log.i("Message", message);

                String dateArray[] = message.substring(message.indexOf("at"), message.indexOf(".")).split("at ")[1].trim().split(" ", 4);

                String monthFromMessage = buildMonth(dateArray);
//                Log.i("monthFromMessage", monthFromMessage); // Jun 13,2020 5:50 PM
                String finalDate = buildFinalDate(dateArray);
//                Log.i("finalDate", finalDate); // Jun 13,2020 5:50 PM

                if(NewDateUtility.isDateAllowed(finalDate)
                        && monthFromMessage.equals(currentMonth)){
                    tempAmount = message.split("Rs")[1].split(" ")[1].trim();
                    amount = amount + Integer.parseInt(tempAmount);
                }
                /*if (isValidDate(finalDate)
                        && monthFromMessage.equals(currentMonth)) {
                    tempAmount = message.split("Rs")[1].split(" ")[1].trim();
                    amount = amount + Integer.parseInt(tempAmount);
                }*/
            }
        }
        Log.i("Current Month Total", amount + "");
        return amount;
    }


    public Map<String, Integer> currentMonthSummary(Date date) {
        int amount = 0;
//        int currentMonth = DateUtility.convertDateToLocalDate(date).getMonthValue();
//        List<String> allDates = DateUtility.getAllDatesOfMonth(currentMonth-1);
        List<String> allDates = NewDateUtility.getAllDatesOfMonth(NewDateUtility.convertDateToString_YYYYmmDD(date));
        Map<String, Integer> dateStringMap = new LinkedHashMap<>();
        for (String tempDate : allDates) {
//            amount = totalAmountByDate(DateUtility.convertStringToDate(tempDate));
            amount = totalAmountByDate(NewDateUtility.convertStringToDate(tempDate));
            dateStringMap.put(tempDate, amount);
        }
        return dateStringMap;
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String buildFinalDate(String[] dateArray) {
        String finalDate = null;
        if (isNumeric(dateArray[0])) {
            finalDate = dateArray[1].replace(",", "") + " " + dateArray[0] + ", " + dateArray[2];
        } else {
            finalDate = dateArray[0] + " " + dateArray[1] + " " + dateArray[2];
        }
        return finalDate;
    }

    public static String buildMonth(String[] dateArray) {
        String finalMonth = null;
        if (isNumeric(dateArray[0])) {
            finalMonth = dateArray[1].replace(",", "");
        } else {
            finalMonth = dateArray[0];
        }
        return finalMonth;
    }
}
