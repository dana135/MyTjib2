package com.androidapp.mytjib.admin_panel.venues;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class VenuesViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    private Repository repository;

    public void createRepository(){
        repository  = new Repository();
        repository.getVenuesFromServer();
    }

    public LiveData<List<Venue>> getVenues(){
        return repository.getVenuesLive();
    }

}
