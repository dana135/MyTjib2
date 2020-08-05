package com.androidapp.mytjib;

import java.util.List;

public class Venue {


    /**
     * id : 1
     * venueName : SK Olympic Handball Gymnasium
     * seats : [["GROUND","2503"],["FLOOR1","1000"],["FLOOR2","1500"]]
     * location : Seoul
     * capacity : 5003
     */

    private int id;
    private String venueName;
    private String location;
    private int capacity;
    private List<List<String>> seats;

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

    public List<List<String>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<String>> seats) {
        this.seats = seats;
    }
}
