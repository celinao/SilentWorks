package com.example.silentworks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Arrays;

public class SettingsPage extends OptionsMenu{

    private NumberPicker hourPicker;
    private NumberPicker minPicker;
    private String[] pickerVals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);

        Spinner dropdown = findViewById(R.id.modeSelectionSpinner);
        String[] items = new String[]{"Standard", "Stop All Notifications", "Stop Muting Notifications"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        pickerVals = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        hourPicker = findViewById(R.id.TimerHour);
        hourPicker.setMaxValue(10);
        hourPicker.setMinValue(0);
        hourPicker.setDisplayedValues(pickerVals);

        minPicker = findViewById(R.id.TimerMin);
        minPicker.setMaxValue(5);
        minPicker.setMinValue(0);
        minPicker.setDisplayedValues(pickerVals);

        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = hourPicker.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }
        });

        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = minPicker.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }
        });

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
        TextResponse autoTextResponse = new TextResponse;
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
    // This function sends you back to the main page but, doesn't log out the user.
    public void logOut(View view){
        startActivity(new Intent(this, MainPage.class));
    }
}