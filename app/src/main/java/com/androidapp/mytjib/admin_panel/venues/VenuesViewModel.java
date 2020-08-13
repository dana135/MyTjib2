package com.androidapp.mytjib.admin_panel.venues;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class VenuesViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();
        repository.getVenuesFromServer();
    }

    public LiveData<List<Venue>> getVenues(){
        return repository.getVenuesLive();
    }

    public void addVenue(Venue venue){
        repository.addVenue(venue);
    }

}
