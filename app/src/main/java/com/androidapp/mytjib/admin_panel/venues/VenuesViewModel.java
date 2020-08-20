package com.androidapp.mytjib.admin_panel.venues;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

/**
 *  store and manage UI-related data for venue actions
 */

public class VenuesViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(Context context) { // actions made with data from repository
        repository  = Repository.getInstance();
        repository.getVenuesFromServer(context);
    }

    public LiveData<List<Venue>> getVenues(){
        return repository.getVenuesLive();
    }

    public void addVenue(Venue venue, Context context){
        repository.addVenue(venue, context);
    }

}
