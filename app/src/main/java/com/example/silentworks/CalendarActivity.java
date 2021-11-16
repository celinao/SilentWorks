package com.example.silentworks;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.graphics.Color;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.w3c.dom.Text;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.io.Serializable;

public class CalendarActivity extends OptionsMenu implements Serializable {
    GoogleSignInAccount account;
    private static final int PERMISSIONS_REQUEST_READ_AND_WRITE_CALENDAR = 12;
    private static final int PERMISSIONS_REQUEST_WRITE_CALENDAR = 13;
    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private LinearLayout linearLayout;
    private Event[] calendarEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();
        account = intent.getParcelableExtra("account");
        Log.v("from Calendar Activity", String.valueOf(account));

        searchCalendarTable();

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

    private void searchCalendarTable() {
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_CALENDAR);
        int permissionTwo = ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.WRITE_CALENDAR);
        if(permission == PackageManager.PERMISSION_DENIED && permissionTwo == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    PERMISSIONS_REQUEST_READ_AND_WRITE_CALENDAR);
        } else {
            // Run query
            Cursor cur = null;
            ContentResolver cr = getContentResolver();
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                                    + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                                    + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
            String[] selectionArgs = new String[] {account.getEmail(), "com.gmail", account.getEmail()};
            try {
                // Submit the query and get a Cursor object back.
                cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
            } catch (Exception e) {
                Log.e("Query failed", e.getLocalizedMessage());
            }
            // Use the cursor to step through the returned records
            while (cur.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

                Log.v("Calendar values", String.valueOf(calID) + String.valueOf(displayName) +
                        String.valueOf(accountName) + String.valueOf(ownerName));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            searchCalendarTable();
        }
    }
}
