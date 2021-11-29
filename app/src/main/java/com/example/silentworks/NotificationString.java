package com.example.silentworks;

import android.os.Build;
import android.service.notification.StatusBarNotification;

import androidx.annotation.RequiresApi;


public class NotificationString {

    String title;
    String titleBig;
    String text;
    String textline;
    String subtext;
    String summary;
    String infoText;
    String category;

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NotificationString (StatusBarNotification statusBarNotification) {
        title = statusBarNotification.getNotification().extras.getString("android.title");
        titleBig = statusBarNotification.getNotification().extras.getString("android.title.big");
        text = statusBarNotification.getNotification().extras.getString("android.text");
        textline = statusBarNotification.getNotification().extras.getString("android.textLines");
        subtext = statusBarNotification.getNotification().extras.getString("android.subText");
        summary = statusBarNotification.getNotification().extras.getString("android.summaryText");
        infoText = statusBarNotification.getNotification().extras.getString("android.infoText");
        category = statusBarNotification.getNotification().category;

    }

    public String getString() {
        String tbr = "";
        if (category != null) {
            tbr = tbr + category + ": ";
        }
        if (title != null) {
            tbr = tbr + title;
        }
        if (text != null) {
            if (!tbr.equals("")) {
                tbr = tbr  + ", ";
            }
            tbr = tbr + text;
        }
        if (textline != null) {
            if (!tbr.equals("")) {
                tbr = tbr  + ", ";
            }
            tbr = tbr + textline;
        }
        return tbr;
    }


}
