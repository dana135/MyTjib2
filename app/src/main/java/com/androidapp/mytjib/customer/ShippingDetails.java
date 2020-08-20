package com.androidapp.mytjib.customer;

import java.util.List;

/**
 * Represents payment details of a single order
 * Tickets related to this shipping will be sent via email
 */

public class ShippingDetails {

    private int id;
    private String firstName;
    private String lastName;
    private String creditCard;
    private int creditExpiration;
    private List<Integer> ticketIds;

    public ShippingDetails(String firstName, String lastName, String creditCard, int creditExpiration,
                           List<Integer> ticketIds) { //constructor
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCard = creditCard;
        this.creditExpiration = creditExpiration;
        this.ticketIds = ticketIds;
    }

    public ShippingDetails(ShippingDetails details, List<Integer> ticketIds){ //copy constructor
        this.firstName = details.getFirstName();
        this.lastName = details.getLastName();
        this.creditCard = details.getCreditCard();
        this.creditExpiration = details.getCreditExpiration();
        this.ticketIds = ticketIds;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public int getCreditExpiration() {
        return creditExpiration;
    }

    public void setCreditExpiration(int creditExpiration) {
        this.creditExpiration = creditExpiration;
    }

    public List<Integer> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Integer> ticketIds) {
        this.ticketIds = ticketIds;
    }

    @Override
    public String toString() {
     String string = "";
     string += "First Name: " + firstName + "\n";
     string += "Last Name: " + lastName + "\n";
     string += "Credit Card Number: " + creditCard + "\n";
     string += "Credit Card Expiration: " + creditExpiration;
     return string;
    }

}
