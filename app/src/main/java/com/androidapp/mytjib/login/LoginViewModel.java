package com.androidapp.mytjib.login;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.admin_panel.Admin;
import com.androidapp.mytjib.customer.ShippingDetails;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

/**
 *  store and manage UI-related data for login and register actions
 */

public class LoginViewModel extends ViewModel  {

    private Repository repository;

    public void createRepository() { // actions made with data from repository
        repository  = Repository.getInstance();;
    }

    public void login(String email, String password, Context context, Activity activity) { // login as admin or user(customer)
         repository.tryAdminLogin(email, password, context, activity);
    }

    public void customerSignUp(Customer customer, Context context, View view) { // sign up as a customer
        repository.customerSignUp(customer, context, view); }

    public void initDatabase(List<Venue> venues, List<Event> events, List<String[]> seats, List<Admin> admins, List<Customer> customers,
                             List<ShippingDetails> shipping, Context context) { // admin initialization of the database before first use
        repository.initDbLevelOne(venues, events, seats, admins, customers, shipping, context); }

}
