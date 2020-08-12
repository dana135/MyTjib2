package com.androidapp.mytjib.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class MyAccountViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = new Repository();
    }

    public LiveData<List<Order>> getOrderHistory(int userId){
        return repository.getOrderHistoryFromServer(userId);
    }

    public void checkout(int userId, List<Integer> ticketIds, ShippingDetails shipping) {
        repository.checkout(userId, ticketIds, shipping);
    }

}
