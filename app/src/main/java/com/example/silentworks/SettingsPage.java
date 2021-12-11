package com.example.silentworks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Calendar;

public class SettingsPage extends OptionsMenu implements AdapterView.OnItemSelectedListener{

    private NotificationManager mNotificationManager;
    private PendingIntent p1;
    String[] items;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //Creates dropdown menu
        Spinner dropdown = findViewById(R.id.modeSelectionSpinner);
        items = new String[]{"Standard", "Stop All Notifications", "Stop Muting Notifications"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        CheckBox checkGetNotifications = (CheckBox)findViewById(R.id.checkGetNotifications);
        checkGetNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

            }
        });

        TextResponse autoTextResponse = new TextResponse();
        CheckBox checkAutoTextResponse = (CheckBox)findViewById(R.id.checkAutoTextResponse);
        checkAutoTextResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    autoTextResponse.setTextResponse(1);
                } else {
                    autoTextResponse.setTextResponse(0);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        ToggleButton startTimer;
        TimePicker timePicker;

        if (position > 0){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            if(linearLayout.getChildCount() < 9){

                timePicker = new TimePicker(getApplicationContext());

                // Create a notification manager and a toggle button
                startTimer = new ToggleButton(getApplicationContext());
                startTimer.setText("Start");
                startTimer.setTextOff("Start");
                startTimer.setTextOn("Cancel");

                // Adding timePicker and toggle button to linear layout
                linearLayout.addView(timePicker, 2);
                linearLayout.addView(startTimer, 3);
            }else{
                // Getting previously added timePicker and toggle button
                timePicker = (TimePicker) linearLayout.getChildAt(2);
                startTimer = (ToggleButton) linearLayout.getChildAt(3);
            }

            Calendar calendar = Calendar.getInstance();
            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);

            startTimer.setOnClickListener(v -> {
                if(!startTimer.isChecked()){
                    a.cancel(p1);
                }else {
                    // getting time from TimePicker
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    //Creating intents based on settings type
                    Intent intent;
                    if (position == 1){
                        if (mNotificationManager.isNotificationPolicyAccessGranted()) {
                            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                        }
                        intent = new Intent(SettingsPage.this, TurnOffDND.class);
                    }else{
                        if (mNotificationManager.isNotificationPolicyAccessGranted()) {
                            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                        }
                        intent = new Intent(SettingsPage.this, TurnOnDND.class);
                    }

                    p1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    a.set(AlarmManager.RTC, calendar.getTimeInMillis(), p1);
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    public void logOut(View view){
        startActivity(new Intent(this, MainPage.class));
    }
}