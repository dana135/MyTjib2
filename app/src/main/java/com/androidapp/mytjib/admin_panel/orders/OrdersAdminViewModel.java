package com.androidapp.mytjib.admin_panel.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.customer.Order;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class OrdersAdminViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();
    }

    public LiveData<List<Order>> getOrders() { return repository.getOrdersFromServer(); }

}
