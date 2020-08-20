package com.androidapp.mytjib.customer;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

/**
 *  store and manage UI-related data for user account actions
 */

public class MyAccountViewModel extends ViewModel {

    private Repository repository;

    public void createRepository() { // actions made with data from repository
        repository  = Repository.getInstance();
    }

    public LiveData<List<Order>> getOrderHistory(int userId, Context context){
        return repository.getOrderHistoryFromServer(userId, context);
    }

    public void checkout(int userId, ShippingDetails shipping, Context context) {
        repository.checkout(userId, shipping, context);
    }

    public void updateCustomer(int userId, Customer customer, View view, Context context){
        repository.updateCustomer(userId, customer, view, context); }

    public LiveData<Customer> getCustomerById(int id, Context context){
        return repository.getCustomerById(id, context);
    }

}
