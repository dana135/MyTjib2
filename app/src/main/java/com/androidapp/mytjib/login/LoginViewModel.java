package com.androidapp.mytjib.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.Customer;
import com.androidapp.mytjib.admin_panel.Admin;
import com.androidapp.mytjib.network.Repository;

public class LoginViewModel extends ViewModel  {

    private Repository repository;

    public void createRepository(){
        repository  = new Repository();
    }

    public LiveData<Admin> getAdmin(String email, String password) { return repository.getAdminFromServer(email, password); }
    public LiveData<Customer> getCustomer(String email, String password) { return repository.getCustomerFromServer(email, password); }
    public void customerSignUp(Customer customer) { repository.customerSignUp(customer); }

}
