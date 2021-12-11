package com.example.silentworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class TextResponse extends BroadcastReceiver {

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
                smsManager.sendTextMessage(phoneNumber, null, "This is an automated reply: Sorry, I'm busy, I will see your text later", null, null);
            }
        }
    }

}