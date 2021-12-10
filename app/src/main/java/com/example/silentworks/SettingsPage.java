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

    public static int textResponse;
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
        textResponse = 0;
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

        CheckBox checkAutoTextResponse = (CheckBox)findViewById(R.id.checkAutoTextResponse);
        checkAutoTextResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                textResponse = 1;
            } else {
                textResponse = 0;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //3. Remove Positions Button and add it to the start of the app upon open.
        if (position > 0){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            TimePicker timePicker = new TimePicker(getApplicationContext());
            timePicker.setIs24HourView(true);
            linearLayout.addView(timePicker, 2);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            long time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));

            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            ToggleButton startTimer = new ToggleButton(getApplicationContext());
            startTimer.setText("Start");
            startTimer.setTextOff("Start");
            startTimer.setTextOn("Cancel");
            AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);

            if (position == 1){
                // Stop All Notifications
                long finalTime = time;
                startTimer.setOnClickListener(v -> {
                    if(!startTimer.isChecked()){
                        a.cancel(p1);
                    }else {
                        if (mNotificationManager.isNotificationPolicyAccessGranted()) {
                            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                        }
                        Intent intent = new Intent(SettingsPage.this, TurnOffDND.class);
                        p1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                        a.set(AlarmManager.RTC, calendar.getTimeInMillis(), p1);
                    }
                });
            }else{
                // Stop Muting Notifications
                long finalTime = time;
                startTimer.setOnClickListener(v -> {
                    if(!startTimer.isChecked()){
                        a.cancel(p1);
                    }else {
                        if (mNotificationManager.isNotificationPolicyAccessGranted()) {
                            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                        }

                        Intent intent = new Intent(SettingsPage.this, TurnOnDND.class);
                        p1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                        a.set(AlarmManager.RTC, calendar.getTimeInMillis(), p1);
                    }
                });
            }

            linearLayout.addView(startTimer, 3);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }


    public void logOut(View view){
        startActivity(new Intent(this, MainPage.class));
    }
}