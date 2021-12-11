package com.example.silentworks;

import android.app.Notification;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * This class turns a notification into a string, with the information stored in the notifictaion that
 * may be useful
 */
public class NotificationString {

    String title;
    String titleBig;
    String text;
    String textline;
    String subtext;
    String summary;
    String infoText;
    String category;
    String bigText;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NotificationString (StatusBarNotification statusBarNotification) {
        // collects all the information that may be useful
        title = statusBarNotification.getNotification().extras.getString("android.title");
        titleBig = statusBarNotification.getNotification().extras.getString("android.title.big");
        text = statusBarNotification.getNotification().extras.getString("android.text");
        textline = statusBarNotification.getNotification().extras.getString("android.textLines");
        subtext = statusBarNotification.getNotification().extras.getString("android.subText");
        summary = statusBarNotification.getNotification().extras.getString("android.summaryText");
        infoText = statusBarNotification.getNotification().extras.getString("android.infoText");
        category = statusBarNotification.getNotification().category;
        if (statusBarNotification.getNotification().extras.get(Notification.EXTRA_BIG_TEXT) != null) {
            bigText = statusBarNotification.getNotification().extras.get(Notification.EXTRA_BIG_TEXT).toString();
        }
    }

    public String getString() {
        // arrange the information
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
        if (bigText != null && !bigText.equals(text) && !bigText.equals(textline) && !bigText.equals(title)) {
            if (!tbr.equals("")) {
                tbr = tbr  + ", ";
            }
            if(bigText.length() >= 40) {
                bigText = bigText.substring(0,40) + "...";
            }
            tbr = tbr + bigText;
        }
        return tbr;
    }


}
