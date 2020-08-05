package com.androidapp.mytjib;

import java.util.List;

public class Event {


    /**
     * id : 1
     * name : MAMAMOO 4 Seasons 4 Colors
     * eventType : Live Concert
     * dateAndTime : [2020,10,21,19,21]
     * image : https://ibb.co/fDRHkrS
     * venueName : SK Olympic Handball Gymnasium
     */

    private int id;
    private String name;
    private String eventType;
    private String image;
    private String venueName;
    private List<Integer> dateAndTime;

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

    public List<Integer> getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(List<Integer> dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}





