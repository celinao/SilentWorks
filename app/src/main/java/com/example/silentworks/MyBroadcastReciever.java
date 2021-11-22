package com.example.silentworks;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class MyBroadcastReciever extends BroadcastReceiver {
//    MediaPlayer mp;
    private NotificationManager mNotificationManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "onReceive!!!", Toast.LENGTH_LONG).show();
        turnOffDND(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOnDND(Context context){

        // Check if the notification policy access has been granted for the app.
        if (mNotificationManager.isNotificationPolicyAccessGranted()) {
            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOffDND(Context context){

        // Check if the notification policy access has been granted for the app.
        if (mNotificationManager.isNotificationPolicyAccessGranted()) {
            Toast.makeText(context, "Turning Off....", Toast.LENGTH_LONG).show();
            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
        }
        Toast.makeText(context, "onReceive__Ending!!!", Toast.LENGTH_LONG).show();
    }


}
