package com.androidapp.mytjib.customer;

public class ShippingDetails {

    /**
     * id : 102
     * firstName : Jimin
     * lastName : Park
     * creditCard : 654783905687
     * creditExpiration : 1025
     */

    private int id;
    private String firstName;
    private String lastName;
    private String creditCard;
    private int creditExpiration;

    public ShippingDetails(String firstName, String lastName, String creditCard, int creditExpiration) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCard = creditCard;
        this.creditExpiration = creditExpiration;
    }

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
}
