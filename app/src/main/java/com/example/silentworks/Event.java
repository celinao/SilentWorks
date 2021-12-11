package com.example.silentworks;

/**
 * This object stores all the needed information for each event, which include date, time, title,
 * and description
 */
public class Event {

    private String username;
    private String date;
    private String startHour;
    private String startMin;
    private String endHour;
    private String endMin;
    private String title;
    private String description;
    private boolean silence;

    public Event (String username, String date, String startHour, String startMin, String endHour,
                  String endMin, String title, String description, String silence) {
        this.username = username;
        this.date = date;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.title = title;
        this.description = description;
        if (silence == "T") {
            this.silence = true;
        } else {
            this.silence = false;
        }
    }

    public String getCalendarText(){
        return (title + "\n" + startHour + ":" + startMin + "-" + endHour + ":" + endMin);
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getStartMin() {
        return startMin;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getEndMin() {
        return endMin;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
