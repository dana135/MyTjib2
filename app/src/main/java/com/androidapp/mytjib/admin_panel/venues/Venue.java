package com.androidapp.mytjib.admin_panel.venues;

public class Venue {


    /**
     * id : 1
     * venueName : SK Olympic Handball Gymnasium
     * location : Seoul
     * capacity : 5003
     */

    private int id;
    private String venueName;
    private String location;
    private int capacity;

    public Venue(String venueName, String location, int capacity) {
    //    this.id = 0;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }

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
