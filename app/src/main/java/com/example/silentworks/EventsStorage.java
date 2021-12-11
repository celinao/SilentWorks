package com.example.silentworks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EventsStorage {

    SQLiteDatabase sqLiteDatabase;

    public EventsStorage(Context context) {
        sqLiteDatabase = context.openOrCreateDatabase("SWE", Context.MODE_PRIVATE, null);

    }

    public void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS events" +
                "(id INTEGER PRIMARY KEY, username TEXT, date TEXT, startHour TEXT, startMin TEXT, endHour TEXT, endMin TEXT, " +
                "title TEXT, description TEXT, silence TEXT)");
    }

    public ArrayList<Event> readNote(String username) {

        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from events where username like '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int sHourIndex = c.getColumnIndex("startHour");
        int sMinIndex = c.getColumnIndex("startMin");
        int eHourIndex = c.getColumnIndex("endHour");
        int eMinIndex = c.getColumnIndex("endMin");
        int titleIndex = c.getColumnIndex("title");
        int descriptionIndex = c.getColumnIndex("description");
        int silenceIndex = c.getColumnIndex("silence");

        c.moveToFirst();

        ArrayList<Event> eventsList = new ArrayList<>();

        while (!c.isAfterLast()) {

            String sHour = c.getString(sHourIndex);
            String sMin = c.getString(sMinIndex);
            String eHour = c.getString(eHourIndex);
            String eMin = c.getString(eMinIndex);
            String date = c.getString(dateIndex);
            String title = c.getString(titleIndex);
            String description = c.getString(descriptionIndex);
            String silence = c.getString(silenceIndex);

            Event event = new Event(username, date, sHour, sMin, eHour, eMin, title, description, silence);

            eventsList.add(event);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return eventsList;

    }

    public void saveEvent(String username, String date, String startHour, String startMin, String endHour,
                          String endMin, String title, String description, String silence) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO events (username, date, startHour, startMin, endHour, endMin, title, description, silence) " +
                        "VALUES ('%s', '%s', '%s', '%s','%s', '%s', '%s', '%s','%s')",
                username, date, startHour, startMin, endHour, endMin, title, description, silence));
    }


    public void updateEvent(String username, String title, String silence) {
        createTable();
        sqLiteDatabase.execSQL(String.format("UPDATE events set silence = '%s' where title = '%s' and username = '%s'",
                silence, title, username));
    }

    public void deleteEvent(String username) {
        createTable();
        sqLiteDatabase.execSQL(String.format("DELETE FROM events WHERE username = '%s'", username));
        //sqLiteDatabase.delete("SWE", null, null);
    }

    public void closeDatabase() {
        sqLiteDatabase.close();
    }



}
