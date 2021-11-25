package com.example.silentworks;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends OptionsMenu implements Serializable {
    GoogleSignInAccount account;
    CalendarView calendarView;
    private static final int PERMISSIONS_REQUEST_READ_AND_WRITE_CALENDAR = 12;
    private static final int PERMISSIONS_REQUEST_WRITE_CALENDAR = 13;
    private long calendarId = 3;
    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] CALENDAR_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT
    };

    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
    };

    // The indices for the projection arrays above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final int PROJECTION_TITLE_INDEX = 0;
    private static final int PROJECTION_DESCRIPTION_NAME_INDEX = 1;
    private static final int PROJECTION_START_INDEX = 2;
    private static final int PROJECTION_END_INDEX = 3;
    private LinearLayout linearLayout;
    private ArrayList<Event> calendarEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();
        account = intent.getParcelableExtra("account");
        calendarEvents = new ArrayList<Event>();
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // write code to change the displayed events based on this.
            }
        });

        searchCalendarTable();
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
                                    + CalendarContract.Calendars.ACCOUNT_TYPE + " = ? ))";
            String[] selectionArgs = new String[] {account.getEmail(), "com.google"};
            try {
                // Submit the query and get a Cursor object back
                cur = cr.query(uri, CALENDAR_PROJECTION, selection, selectionArgs, null);
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

                if(displayName.equals(accountName)){
                    calendarId = calID;
                }

                Log.v("Calendar values", String.valueOf(calID) + String.valueOf(displayName) +
                        String.valueOf(accountName) + String.valueOf(ownerName));
            }

            // Get new uri to query the events table
            Uri eventsUri = CalendarContract.Events.CONTENT_URI;
            cur = cr.query(eventsUri, EVENT_PROJECTION, "", null, null);

            // Storing the login info to local and start SQLite
            String username = account.getEmail();
            LoginStorage loginStorage = new LoginStorage(getApplicationContext());
            loginStorage.setUsername(username);
            EventsStorage eventsStorage = new EventsStorage(getApplicationContext());

            while(cur.moveToNext()) {
                String title = null;
                String description = null;
                long startTime;
                long endTime;

                title = cur.getString(PROJECTION_TITLE_INDEX);
                description = cur.getString(PROJECTION_DESCRIPTION_NAME_INDEX);
                startTime = cur.getLong(PROJECTION_START_INDEX);
                endTime = cur.getLong(PROJECTION_END_INDEX);

                Date startDate = new Date(startTime);
                Date endDate = new Date(endTime);

                Log.v("Events", String.valueOf(title) + String.valueOf(description)
                        + String.valueOf(startTime) + String.valueOf(endTime));

                // Format date for UI
                SimpleDateFormat formatterHour = new SimpleDateFormat("HH");
                SimpleDateFormat formatterMin = new SimpleDateFormat("mm");

                // Create an event to be added to the ArrayList
                Event tempEvent = new Event(account.getEmail(), startDate.toString(), formatterHour.format(startDate),
                        formatterMin.format(startDate), formatterHour.format(endDate), formatterMin.format(endDate), title, description, "false");

                calendarEvents.add(tempEvent);

                // store the events into SQLite for off-line
                eventsStorage.saveEvent(account.getEmail(), startDate.toString(), formatterHour.format(startDate),
                        formatterMin.format(startDate), formatterHour.format(endDate), formatterMin.format(endDate), title, description, "false");

            }
            displayCalendarEvents();
        }
    }

    public void displayCalendarEvents() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView newView;

        // Adds each element in the CalendarEvent's array to the display
        for(int idx = 0; idx < calendarEvents.size(); idx++){
            newView = new TextView(this);
            newView.setText(calendarEvents.get(idx).getCalendarText());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            searchCalendarTable();
        }
    }
}
