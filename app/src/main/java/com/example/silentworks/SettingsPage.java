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

    private NotificationManager mNotificationManager;
    private Button b1;
    private Button b2;
    String[] items;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

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

        CheckBox checkGetDarkMode = (CheckBox)findViewById(R.id.checkGetDarkMode);
        checkGetDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        // STILL NEED TO DO:
        //1. Get time from Time Picker (not a default 5 seconds)
        //2. Turn on/off DND before the timer finishes (maybe once selected and not upon button?)
        //3. Remove Positions Button and add it to the start of the app upon open.
        if (position > 0){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            TimePicker timePicker = new TimePicker(getApplicationContext());
            linearLayout.addView(timePicker, 2);

            Button startTimer = new Button(getApplicationContext());
            startTimer.setText("Start");
            if (position == 1){
                // Stop All Notifications
                startTimer.setOnClickListener(v -> {
                    int time = 5;
                    Intent intent = new Intent(SettingsPage.this, TurnOnDND.class);
                    PendingIntent p1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);
                    a.set(AlarmManager.RTC, System.currentTimeMillis() + time * 1000, p1);
                });
            }else{
                // Stop Muting Notifications
                startTimer.setOnClickListener(v -> {
                    int time = 5;
                    Toast.makeText(this, "set", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SettingsPage.this, TurnOnDND.class);
                    PendingIntent p1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);
                    a.set(AlarmManager.RTC, System.currentTimeMillis() + time * 1000, p1);
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