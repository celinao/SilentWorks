package com.example.silentworks;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalendarActivity extends OptionsMenu {

    private LinearLayout linearLayout;
    private Event[] calendarEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        // Sample Events
        calendarEvents = new Event[]{
                new Event("username", "date", "5", "30",
                        "7", "00", "First Event", "description", "silence"),
                new Event("username", "date", "6", "00",
                    "7", "00", "Second Event", "description", "silence"),
                new Event("username", "date", "9", "15",
                    "10", "00", "Third Event", "description", "silence"),
                new Event("username", "date", "startHour", "startMin",
                    "endHour", "endMin", "title", "description", "silence")};

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView newView;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // write code to change the displayed events based on this.
            }
        });

        // Adds each element in the CalendarEvent's array to the display
        for(int idx = 0; idx < calendarEvents.length; idx++){
            newView = new TextView(this);
            newView.setText(calendarEvents[idx].getCalendarText());
            newView.setTextSize(20);

            // Switches background color so every other event
            if(idx %2 == 0){
                newView.setBackgroundResource(R.drawable.calendar_color1);
            }else{
                newView.setBackgroundResource(R.drawable.calendar_color2);
            }
            linearLayout.addView(newView);
        }
    }
}
