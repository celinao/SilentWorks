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

    private int text_response;
    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Yee",
                Toast.LENGTH_SHORT).show();
        Log.i("Recieved something", "yee");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            String phoneNumber = messages[0].getOriginatingAddress();
            if (bundle != null) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, "Can't text now bro", null, null);
            }
        }
        this.context = context;
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        int events = PhoneStateListener.LISTEN_CALL_STATE;
        telephonyManager.listen(phoneStateListener, events);

    }

    int previousState=TelephonyManager.CALL_STATE_OFFHOOK;
    private final PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            int Call_State_Ringing = TelephonyManager.CALL_STATE_RINGING;
            int Call_State_OffHook = TelephonyManager.CALL_STATE_OFFHOOK;
            if (Call_State_Ringing == state && Call_State_OffHook == previousState){
                if(state == TelephonyManager.CALL_STATE_RINGING) {
                    SmsManager smsManager =     SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, "Can't talk now bro", null, null);
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
                }
            }
            previousState = state;
        }
    };

    public void setTextResponse(int text_response) {
        this.text_response = text_response;
    }
}