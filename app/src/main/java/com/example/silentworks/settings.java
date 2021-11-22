package com.example.silentworks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

public class settings extends OptionsMenu{

//    private NumberPicker hourPicker;
//    private NumberPicker minPicker;
//    private String[] pickerVals;
    private NotificationManager mNotificationManager;
    private Button b1;
    private Button b2;
//    private TimePicker alarmTimePicker;
//    private AlarmManager alarmManager;
//    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.i("tag", "SETTINGS ACTIVITY");

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

//        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);




        b1.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v){

                // Check if the notification policy access has been granted for the app.
                if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }else {
                    b1.setText("ACCESS GRANTED");
                    mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                }
            }
        });
//
        b2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                // Check if the notification policy access has been granted for the app.
                if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }else {
                    b1.setText("ACCESS GRANTED");
                    mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                }
//                startAlert();
            }
        });


//        pickerVals = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//        hourPicker = findViewById(R.id.TimerHour);
//        hourPicker.setMaxValue(10);
//        hourPicker.setMinValue(0);
//        hourPicker.setDisplayedValues(pickerVals);
//
//        minPicker = findViewById(R.id.TimerMin);
//        minPicker.setMaxValue(5);
//        minPicker.setMinValue(0);
//        minPicker.setDisplayedValues(pickerVals);

//        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                int valuePicker1 = hourPicker.getValue();
//                Log.d("picker value", pickerVals[valuePicker1]);
//            }
//        });
//
//        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                int valuePicker1 = minPicker.getValue();
//                Log.d("picker value", pickerVals[valuePicker1]);
//            }
//        });
    }

    public void startAlert() {
//        EditText text = (EditText) findViewById(R.id.time);
//        int i = Integer.parseInt(text.getText().toString());
//        Intent intent = new Intent(this, MyBroadcastReciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                this.getApplicationContext(), 234324243, intent, 0);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +
//                (i * 1000), pendingIntent);
//        Toast.makeText(this, "Alarm set in " + i + " minutes", Toast.LENGTH_LONG).show();
    }

    // This function sends you back to the main page but, doesn't log out the user.
    public void logOut(View view){
        startActivity(new Intent(this, MainPage.class));
    }
}