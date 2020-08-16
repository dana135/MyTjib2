package com.androidapp.mytjib.customer;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class MyAccountViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(int userId){
        repository  = Repository.getInstance();
        repository.setCurrentCustomer(userId);
    }

    public LiveData<List<Order>> getOrderHistory(int userId){
        return repository.getOrderHistoryFromServer(userId);
    }

    public void checkout(int userId, ShippingDetails shipping) {
        repository.checkout(userId, shipping);
    }

    public void updateCustomer(int userId, Customer customer, View view, Context context){
        repository.updateCustomer(userId, customer, view, context); }

    public LiveData<Customer> getCustomerById(int id){
        return repository.getCustomerById(id);
    }

}
