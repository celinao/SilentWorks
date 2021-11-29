package com.example.silentworks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationPage extends OptionsMenu {

    private LinearLayout linearLayout;
    private String[] notifications = new String[NotificationList.Notifications.size()];

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        // notifications = new String[]{
        //        "Email: Hi there person",
        //        "Text: Hi there again",
        //        "GroupMe: When are we meeting again?"
        //};

        int count = 0;
        for (int i = 0; i < NotificationList.Notifications.size(); ++i) {
            NotificationString ns = new NotificationString(NotificationList.Notifications.get(i));
            if (ns.getString() != "") {
                notifications[count] = ns.getString();
                count = count + 1;
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