package com.androidapp.mytjib.customer;

public class Customer {

    /**
     * id : 6
     * username : dushi
     * password : badukachi
     * email : spell@bnd.com
     * shippingDetails : null
     */

    private int id;
    private String username;
    private String password;
    private String email;

    public Customer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String string = "";
        string += "Username: " + username + "\n";
        string += "Email: " + email;
        return string;
    }

}
