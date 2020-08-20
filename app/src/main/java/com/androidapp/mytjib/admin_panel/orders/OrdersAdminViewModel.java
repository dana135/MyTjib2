package com.androidapp.mytjib.admin_panel.orders;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.customer.Order;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

/**
 *  store and manage UI-related data for viewing orders
 */

public class OrdersAdminViewModel extends ViewModel { // actions made with data from repository

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();
    }

    public LiveData<List<Order>> getOrders( Context context) { return repository.getOrdersFromServer(context); }

}
