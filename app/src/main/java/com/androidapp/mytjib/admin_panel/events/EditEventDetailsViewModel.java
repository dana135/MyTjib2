package com.androidapp.mytjib.admin_panel.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;
import com.androidapp.mytjib.admin_panel.venues.Venue;

import java.util.List;

public class EditEventDetailsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(int id){
        repository  = new Repository();
        repository.setEventId(id);
        repository.getEventDetailsFromServer();
    }

    public LiveData<Event> getEventDetails(){
        return repository.getEventDetailsLive();
    }

    public void editEvent(Event event) {
        repository.editEvent(event);
    }

    public void deleteEvent(){
        repository.deleteEvent();
    }

    public LiveData<List<Venue>> getVenues(){
        return repository.getVenuesFromServer();
    }

}