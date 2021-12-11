package com.example.silentworks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationPage extends OptionsMenu {

    private LinearLayout linearLayout;
    private String[] notifications = new String[NotificationList.Notifications.size()];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        int count = 0;
        int marker;
        for (int i = 0; i < NotificationList.Notifications.size(); ++i) {
            NotificationString ns = new NotificationString(NotificationList.Notifications.get(i));
            if (ns.getString() != "") {
                // check for duplicates, and ignore the same notifications
                if (count > 0) {
                    marker = 0;
                    for (int j = 0; j < count; ++j) {
                        if (ns.getString().equals(notifications[j])) {
                            marker = 1;
                        }
                    }
                    if (marker == 0) {
                        notifications[count] = ns.getString();
                        count = count + 1;
                    }
                } else {
                    notifications[count] = ns.getString();
                    count = count + 1;
                }
            }
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView newView;

        // Adds each element in the Notifications's array to the display
        for(int idx = 0; idx < count; idx++){
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