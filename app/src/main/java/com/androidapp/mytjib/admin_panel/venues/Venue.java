package com.androidapp.mytjib.admin_panel.venues;

/**
 * Represents a venue where an event takes place
 */

public class Venue {

    private int id;
    private String venueName;
    private String location;
    private int capacity;

    public Venue(String venueName, String location, int capacity) { //constructor
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
