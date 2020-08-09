package com.androidapp.mytjib.admin_panel.venues;

import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.Repository;

import java.util.List;

public class AddVenueViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = new Repository();
    }

    public void addVenue(Venue venue){
        repository.addVenue(venue);
    }

}
