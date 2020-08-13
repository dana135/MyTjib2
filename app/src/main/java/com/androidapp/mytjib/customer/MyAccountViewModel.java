package com.androidapp.mytjib.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.events.Event;
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

    public LiveData<Customer> getCurrentCustomer(){
        return repository.getCurrentCustomer();
    }

    public LiveData<Customer> getCustomerById(int id){
        return repository.getCustomerByIdFromServer(id);
    }

}
