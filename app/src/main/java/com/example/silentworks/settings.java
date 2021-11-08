package com.example.silentworks;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;

public class settings extends OptionsMenu {


//    private NumberPicker hourPicker;
//    private NumberPicker minPicker;
//    private String[] hourVals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        hourPicker = findViewById(R.id.TimerHour);
//        hourPicker.setMaxValue(5);
//        hourPicker.setMinValue(0);
//        hourVals = new String[] {"A", "1", "2", "3", "4", "5"};
//        hourPicker.setDisplayedValues(hourVals);
//
//        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                int valuePicker1 = hourPicker.getValue();
//                Log.d("picker value", hourVals[valuePicker1]);
//            }
//        });
//
//        minPicker = findViewById(R.id.TimerMin);
//        minPicker.setMinValue(0);
//        minPicker.setMaxValue(59);

        setContentView(R.layout.activity_settings);
    }

}