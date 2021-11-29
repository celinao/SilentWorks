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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public NotificationString (StatusBarNotification statusBarNotification) {
        title = statusBarNotification.getNotification().extras.getString("android.title");
        titleBig = statusBarNotification.getNotification().extras.getString("android.title.big");
        text = statusBarNotification.getNotification().extras.getString("android.text");
        textline = statusBarNotification.getNotification().extras.getString("android.textLines");
        subtext = statusBarNotification.getNotification().extras.getString("android.subText");
        summary = statusBarNotification.getNotification().extras.getString("android.summaryText");
        infoText = statusBarNotification.getNotification().extras.getString("android.infoText");

    }

    public String getString() {
        String tbr = "";
        if (title != null) {
            tbr = tbr + title;
        }
        if (text != null) {
            tbr = tbr  + ", " + text;
        }
        if (textline != null) {
            tbr = tbr +  ", " + textline;
        }
        return tbr;
    }


}
