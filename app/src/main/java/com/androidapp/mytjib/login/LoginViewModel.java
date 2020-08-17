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

public class LoginViewModel extends ViewModel  {

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();;
    }

    public void login(String email, String password, View view, Context context, Activity activity) {
         repository.tryAdminLogin(email, password, view, context, activity);
    }
    public void customerSignUp(Customer customer, Context context, View view) { repository.customerSignUp(customer, context, view); }
    public void initDatabase(List<Venue> venues, List<Event> events, List<String[]> seats, List<Admin> admins, List<Customer> customers, List<ShippingDetails> shipping) {
        repository.initDbLevelOne(venues, events, seats, admins, customers, shipping); }

}
