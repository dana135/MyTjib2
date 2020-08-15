package com.androidapp.mytjib.login;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.admin_panel.Admin;
import com.androidapp.mytjib.network.Repository;

public class LoginViewModel extends ViewModel  {

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();;
    }

    public LiveData<Admin> getAdmin(String email, String password) { return repository.getAdminFromServer(email, password); }
    public LiveData<Customer> getCustomer(String email, String password, Context context) { return repository.getCustomerFromServer(email, password, context); }
    public void customerSignUp(Customer customer, Context context, View view) { repository.customerSignUp(customer, context, view); }

}
