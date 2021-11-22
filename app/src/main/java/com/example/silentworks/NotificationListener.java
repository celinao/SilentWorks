package com.example.silentworks;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


public class NotificationListener extends NotificationListenerService {

    private String TAG = "NotificationListener";
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        NotificationList.Notifications.add(sbn);

        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        NotificationList.Notifications.remove(sbn);

        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onListenerConnected() {
        StatusBarNotification[] sb = getActiveNotifications();
        for (StatusBarNotification d : sb) {
            Log.d(TAG, "" + d.toString());
            NotificationList.Notifications.add(d);
        }
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("notifications");
//        broadcastIntent.putExtra("toastMessage", sb[0].toString());
//        sendBroadcast(broadcastIntent);
        super.onListenerConnected();
    }
}
