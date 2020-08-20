package com.androidapp.mytjib.customer;

import com.androidapp.mytjib.buy_tickets.Ticket;

import java.util.Date;
import java.util.List;

/**
 * Represents a single order that a specific customer placed
 * May contain multiple tickets
 */

public class Order {

    private int orderNum;
    private Customer customer;
    private ShippingDetails shippingDetails;
    private int price;
    private Date orderTime;
    private List<Ticket> tickets;

    public Order(Customer customer, ShippingDetails shippingDetails, int price, Date orderTime, List<Ticket> tickets) { //customer
        this.customer = customer;
        this.shippingDetails = shippingDetails;
        this.price = price;
        this.orderTime = orderTime;
        this.tickets = tickets;
    }

    //getters and setters

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
