package com.androidapp.mytjib;

import java.util.List;

public class Event {


    /**
     * id : 202
     * name : MAMAMOO 4 Seasons 4 Colors
     * eventType : Live Concert
     * dateAndTime : [2020,10,21,19,21]
     * venue : {"id":1,"venueName":"SK Olympic Handball Gymnasium","seats":[["GROUND","2503"],["FLOOR1","1000"],["FLOOR2","1500"]],"location":"Seoul","capacity":5003}
     * image : https://ibb.co/fDRHkrS
     * availableTickets : [{"id":201,"section":"GROUND","position":1,"price":30000,"status":"available","marked":true},{"id":202,"section":"GROUND","position":2,"price":30000,"status":"available","marked":true},{"id":203,"section":"GROUND","position":3,"price":30000,"status":"available","marked":true}]
     */

    private int id;
    private String name;
    private String eventType;
    private VenueBean venue;
    private String image;
    private List<Integer> dateAndTime;
    private List<AvailableTicketsBean> availableTickets;

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

    public VenueBean getVenue() {
        return venue;
    }

    public void setVenue(VenueBean venue) {
        this.venue = venue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Integer> getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(List<Integer> dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public List<AvailableTicketsBean> getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(List<AvailableTicketsBean> availableTickets) {
        this.availableTickets = availableTickets;
    }

    public static class VenueBean {
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

    public static class AvailableTicketsBean {
        /**
         * id : 201
         * section : GROUND
         * position : 1
         * price : 30000
         * status : available
         * marked : true
         */

        private int id;
        private String section;
        private int position;
        private int price;
        private String status;
        private boolean marked;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isMarked() {
            return marked;
        }

        public void setMarked(boolean marked) {
            this.marked = marked;
        }
    }
}





