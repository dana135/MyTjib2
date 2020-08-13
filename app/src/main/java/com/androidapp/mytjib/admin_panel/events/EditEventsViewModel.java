package com.androidapp.mytjib.admin_panel.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class EditEventsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();
        repository.getEventsFromServer();
    }

    public LiveData<List<Event>> getEvents(){
        return repository.getEventsLive();
    }

    public LiveData<List<Venue>> getVenues(){
        return repository.getVenuesFromServer();
    }

    public LiveData<Event> addEvent(Event event){
        return repository.addEvent(event);
    }

}