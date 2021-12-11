package com.example.silentworks;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListener extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if ((sbn.getNotification().flags & sbn.getNotification().FLAG_GROUP_SUMMARY) != 0) {

        } else {
            NotificationList.Notifications.add(sbn);
        }
        super.onNotificationPosted(sbn);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        //NotificationList.Notifications.removeAll(NotificationList.Notifications);
        int len = NotificationList.Notifications.size();
        for (int i = 0; i < len; ++i) {
            NotificationString notif = new NotificationString(NotificationList.Notifications.get(i));
            NotificationString notifRemove = new NotificationString(sbn);
            if (notif.getString().equals(notifRemove.getString())) {
                NotificationList.Notifications.remove(i);
                --i;
                --len;
            }
        }
        super.onNotificationRemoved(sbn);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onListenerConnected() {
        StatusBarNotification[] sb = getActiveNotifications();
        for (StatusBarNotification d : sb) {
            if ((d.getNotification().flags & d.getNotification().FLAG_GROUP_SUMMARY) != 0) {

            } else {
                NotificationList.Notifications.add(d);
            }
        }
        super.onListenerConnected();

    }

}
