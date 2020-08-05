package com.androidapp.mytjib.venues;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.Repository;
import com.androidapp.mytjib.Venue;

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
