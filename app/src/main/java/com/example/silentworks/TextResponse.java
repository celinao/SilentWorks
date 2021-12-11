package com.example.silentworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class TextResponse extends BroadcastReceiver {

    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") && SettingsPage.textResponse==1) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            messages[0]=SmsMessage.createFromPdu((byte[]) pdus[0]);
            Toast.makeText(context, messages[0].toString(),
                    Toast.LENGTH_SHORT).show();
            String phoneNumber = messages[0].getOriginatingAddress();
            if (bundle != null) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, "Sorry, I'm busy", null, null);
            }
        }
    }

}