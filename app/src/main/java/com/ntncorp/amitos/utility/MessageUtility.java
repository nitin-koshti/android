package com.ntncorp.amitos.utility;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ntncorp.amitos.model.Sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageUtility {

    public static final String INBOX = "content://sms/inbox";
    public static final String SMS = "content://sms/";

    static String[] payTmAddressArray = new String[]{"VK-iPAYTM", "VM-iPAYTM", "AX-iPAYTM"};
    static List<String> payTmAddressList = Arrays.asList(payTmAddressArray);

    Context mActivity;
    Activity activity;

    public MessageUtility(Context context, Activity activity) {
        mActivity = context;
        this.activity = activity;
    }

    public List<Sms> getInboxSms() {
        List<Sms> lstSms = new ArrayList<>();
        Sms objSms = null;
        Uri message = Uri.parse(SMS);
        ContentResolver cr = mActivity.getContentResolver();
        Log.i("info","in MESSAGE READ");
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i("info","Ask for permission");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, 1);
        }
        Cursor c = cr.query(message, null, null, null, null);
        activity.startManagingCursor(c);
        int totalSMS = c.getCount();
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
//                Log.i("Info","inside getAllSms: messages IFF i: "+i);
                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }
                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        c.close();
        return lstSms;
    }


    public ArrayList<Sms> readPaytmMessages() {
        List<Sms> lst = this.getInboxSms();
        Sms sms;
        ArrayList<Sms> payTmPaymentReceiveList = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            sms = lst.get(i);
            /*if (payTmAddressList.contains(sms.getAddress())) {
                payTmPaymentReceiveList.add(sms);
            }*/
            if(sms.getMsg().contains("Count#")){
                payTmPaymentReceiveList.add(sms);
            }
        }
        return payTmPaymentReceiveList;
    }
}
