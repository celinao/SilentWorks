package com.example.silentworks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationPage extends OptionsMenu {

    private LinearLayout linearLayout;
    private String[] notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        notifications = new String[]{
                "Email: Hi there person",
                "Text: Hi there again",
                "GroupMe: When are we meeting again?"
        };

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView newView;

        // Adds each element in the Notifications's array to the display
        for(int idx = 0; idx < notifications.length; idx++){
            newView = new TextView(this);
            newView.setText(notifications[idx]);
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