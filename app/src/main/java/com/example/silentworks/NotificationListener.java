package com.example.silentworks;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListener extends NotificationListenerService {

    private String TAG = "SuperUnique";
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onListenerConnected() {
        StatusBarNotification[] sb = getActiveNotifications();
        for (StatusBarNotification d : sb) {
            NotificationList.Notifications.add(d);
        }
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("notifications");
//        broadcastIntent.putExtra("toastMessage", sb[0].toString());
//        sendBroadcast(broadcastIntent);
        super.onListenerConnected();
    }
}