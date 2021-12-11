package com.example.silentworks;

import static java.lang.Integer.parseInt;

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

    public String getUsername() { return username; }

    public String getDate() {
        return date;
    }

    public int getStartHour() {
        return parseInt(startHour);
    }

    public int getStartMin() {
        return parseInt(startMin);
    }

    public int getEndHour() {
        return parseInt(endHour);
    }

    public int getEndMin() {
        return parseInt(endMin);
    }

    public boolean checkSilenced(){
        return silence;
    }

    public String getTitle() {
        return title;
    }

    public int getID(){
        return(parseInt(startHour+startMin+endHour+endMin));
    }
}
