package com.androidapp.mytjib.events;

/**
 * Represents an event with tickets for sale
 */

public class Event {

    private int id;
    private String name;
    private String eventType;
    private String image;
    private String venueName;
    private String dateAndTime;

    public Event(String name, String eventType, String image, String venueName, String dateAndTime) { //constructor
        this.id = 0;
        this.name = name;
        this.eventType = eventType;
        this.image = image;
        this.venueName = venueName;
        this.dateAndTime = dateAndTime;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

}





