package com.example.silentworks;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends OptionsMenu implements Serializable {
    GoogleSignInAccount account;
    CalendarView calendarView;
    ArrayList<View> viewList;
    private static final int PERMISSIONS_REQUEST_READ_AND_WRITE_CALENDAR = 12;
    private long calendarId = 3;

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] CALENDAR_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT
    };

    public static final String[] EVENT_PROJECTION = new String[]{
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
    private ArrayList<Event> calendarEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();

        // Checks if account is already saved from the last login
        GoogleSignInAccount checkAccount = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this);
        if (checkAccount != null) {
            // Sets account to the last logged in account
            account = checkAccount;
        } else {
            // Sets account to the account passed in from the previous intent
            account = intent.getParcelableExtra("account");
        }

        calendarEvents = new ArrayList<Event>();
        viewList = new ArrayList<View>();
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                displayDayEvents(view, year, month, dayOfMonth);
            }
        });

        searchCalendarTable();
    }

    private void displayDayEvents(CalendarView view, int year, int month, int dayOfMonth) {
        ArrayList<Event> dayEvents = new ArrayList<Event>();

        // Loops through all of the events in the calendar, and if the date of the event
        // matches the date that was clicked on by the user, add it to the dayEvents ArrayList
        for (int i = 0; i < calendarEvents.size(); i++) {
            int eventDay = Integer.parseInt(calendarEvents.get(i).getDate().substring(8, 10));
            String eventMonth = calendarEvents.get(i).getDate().substring(4, 7);
            int eventMonthAsInt = 100;
            int eventYear = Integer.parseInt(calendarEvents.get(i).getDate().substring(24, 28));

            switch (eventMonth) {
                case "Jan":
                    eventMonthAsInt = 0;
                    break;
                case "Feb":
                    eventMonthAsInt = 1;
                    break;
                case "Mar":
                    eventMonthAsInt = 2;
                    break;
                case "Apr":
                    eventMonthAsInt = 3;
                    break;
                case "May":
                    eventMonthAsInt = 4;
                    break;
                case "Jun":
                    eventMonthAsInt = 5;
                    break;
                case "Jul":
                    eventMonthAsInt = 6;
                    break;
                case "Aug":
                    eventMonthAsInt = 7;
                    break;
                case "Sep":
                    eventMonthAsInt = 8;
                    break;
                case "Oct":
                    eventMonthAsInt = 9;
                    break;
                case "Nov":
                    eventMonthAsInt = 10;
                    break;
                case "Dec":
                    eventMonthAsInt = 11;
                    break;
            }

            if (eventDay == dayOfMonth && eventMonthAsInt == month && eventYear == year) {
                dayEvents.add(calendarEvents.get(i));
                setAlarms(calendarEvents.get(i));
            }
        }
        displayCalendarEvents(dayEvents);
    }

    private void setAlarms(Event event) {
        // Call Alarms on Start & End Times
        // Check if event has been turned on/off?
        // Turn on/off based on Settings Page Standard/All/None.
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchCalendarTable();
        Date selectedDate = new Date(calendarView.getDate());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String yearStr = yearFormat.format(selectedDate);
        String monthStr = monthFormat.format(selectedDate);
        String dayStr = dayFormat.format(selectedDate);
        displayDayEvents(calendarView, Integer.parseInt(yearStr), Integer.parseInt(monthStr), Integer.parseInt(dayStr));
    }

    private void searchCalendarTable() {

        // Off-line mode getting the events from the storage if any
        if (isNetworkAvailable()) {
            LoginStorage loginStorage = new LoginStorage(getApplicationContext());
            String username = loginStorage.getUsername();
            if (!username.equals("")) {
                EventsStorage eventsStorage = new EventsStorage(getApplicationContext());
                calendarEvents = eventsStorage.readNote(username);
            }
        }

        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_CALENDAR);
        int permissionTwo = ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.WRITE_CALENDAR);

        // Requesting permission from the user to read/write to/from their calendar if not
        // already granted
        if (permission == PackageManager.PERMISSION_DENIED && permissionTwo == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    PERMISSIONS_REQUEST_READ_AND_WRITE_CALENDAR);
        } else {
            // Querying the user's Google calendars to find their personal calendar
            Cursor cur = null;
            ContentResolver cr = getContentResolver();
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                    + CalendarContract.Calendars.ACCOUNT_TYPE + " = ? ))";
            String[] selectionArgs = new String[]{account.getEmail(), "com.google"};
            try {
                // Submit the query and get a Cursor object back
                cur = cr.query(uri, CALENDAR_PROJECTION, selection, selectionArgs, null);
            } catch (Exception e) {
                Log.e("Query failed", e.getLocalizedMessage());
            }
            // Use the cursor to step through the returned calendars
            while (cur.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values of each calendar
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

                if (displayName.equals(accountName)) {
                    // Saving the user's personal calendar ID to use later
                    calendarId = calID;
                }

                // Get new uri to query the events table
                Uri eventsUri = CalendarContract.Events.CONTENT_URI;
                cur = cr.query(eventsUri, EVENT_PROJECTION, "", null, null);

                // Storing the login info to local and start SQLite
                String username = account.getEmail();
                LoginStorage loginStorage = new LoginStorage(getApplicationContext());
                loginStorage.setUsername(username);
                EventsStorage eventsStorage = new EventsStorage(getApplicationContext());
            }

                calendarEvents.clear();
                while (cur.moveToNext()) {
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
                    eventsStorage.deleteEvent(username);
                    String titlePass = title;
                    if (title.contains("'")) {
                        titlePass = title.replace("'", "");
                    }
                    String descriptionPass = description;
                    if (description.contains("'")) {
                        descriptionPass = description.replace("'", "");
                    }
                    eventsStorage.saveEvent(username, startDate.toString(), formatterHour.format(startDate),
                            formatterMin.format(startDate), formatterHour.format(endDate), formatterMin.format(endDate), titlePass, descriptionPass, "false");

                }
                eventsStorage.closeDatabase();
                //displayCalendarEvents(calendarEvents);
            // Storing the login info to local and start SQLite
            String username = account.getEmail();
            LoginStorage loginStorage = new LoginStorage(getApplicationContext());
            loginStorage.setUsername(username);
            EventsStorage eventsStorage = new EventsStorage(getApplicationContext());

            calendarEvents.clear();

            // Querying all of the events in the user's personal calendar
            while (cur.moveToNext()) {
                String title = null;
                String description = null;
                long startTime;
                long endTime;

                // Setting event details
                title = cur.getString(PROJECTION_TITLE_INDEX);
                description = cur.getString(PROJECTION_DESCRIPTION_NAME_INDEX);
                startTime = cur.getLong(PROJECTION_START_INDEX);
                endTime = cur.getLong(PROJECTION_END_INDEX);

                Date startDate = new Date(startTime);
                Date endDate = new Date(endTime);

                // Formatting date for UI
                SimpleDateFormat formatterHour = new SimpleDateFormat("HH");
                SimpleDateFormat formatterMin = new SimpleDateFormat("mm");

                // Creating a temporary event to be added to the ArrayList
                Event tempEvent = new Event(account.getEmail(), startDate.toString(), formatterHour.format(startDate),
                        formatterMin.format(startDate), formatterHour.format(endDate), formatterMin.format(endDate), title, description, "false");

                calendarEvents.add(tempEvent);

                // store the events into SQLite for off-line
                eventsStorage.deleteEvent(username);
                String titlePass = title;
                if (title.contains("'")) {
                    titlePass = title.replace("'", "");
                }
                String descriptionPass = description;
                if (description.contains("'")) {
                    descriptionPass = description.replace("'", "");
                }
                eventsStorage.saveEvent(username, startDate.toString(), formatterHour.format(startDate),
                        formatterMin.format(startDate), formatterHour.format(endDate), formatterMin.format(endDate), titlePass, descriptionPass, "false");

            }
        }
    }

    public void displayCalendarEvents(ArrayList<Event> events) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView newView;
        for (int i = 0; i < viewList.size(); i++) {
            linearLayout.removeView(viewList.get(i));
        }
        viewList.clear();

        // Adds each element in the CalendarEvent's array to the display
        for (int idx = 0; idx < events.size(); idx++) {
            newView = new TextView(this);
            newView.setText(events.get(idx).getCalendarText());
            newView.setTextSize(20);

            // Switches background color for every other event
            if (idx % 2 == 0) {
                newView.setBackgroundResource(R.drawable.calendar_color1);
            } else {
                newView.setBackgroundResource(R.drawable.calendar_color2);
            }
            linearLayout.addView(newView);
            viewList.add(newView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            searchCalendarTable();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void addEvent(View v) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2021, 10, 24, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2021, 10, 24, 8, 30);
        // This intent automatically takes the user to Google to add an event to their
        // Calendar. The values that are set on the intent are the default event values
        // when Google loads
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
    }
}