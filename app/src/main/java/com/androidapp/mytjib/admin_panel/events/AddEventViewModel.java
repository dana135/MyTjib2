package com.androidapp.mytjib.admin_panel.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.network.Repository;
import com.androidapp.mytjib.admin_panel.venues.Venue;

import java.util.List;

public class AddEventViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = new Repository();
    }

    public LiveData<List<Venue>> getVenues(){
        return repository.getVenuesFromServer();
    }

    public LiveData<Event> addEvent(Event event){
        return repository.addEvent(event);
    }

}
