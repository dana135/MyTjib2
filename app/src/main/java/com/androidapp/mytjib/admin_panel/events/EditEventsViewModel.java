package com.androidapp.mytjib.admin_panel.events;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

/**
 *  store and manage UI-related data for event actions by admin
 */

public class EditEventsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(Context context) { // actions made with data from repository
        repository  = Repository.getInstance();
        repository.getEventsFromServer(context);
    }

    public LiveData<List<Event>> getEvents(){
        return repository.getEventsLive();
    }

    public LiveData<List<Venue>> getVenues(Context context){
        return repository.getVenuesFromServer(context);
    }

    public LiveData<Event> addEvent(Event event, Context context){
        return repository.addEvent(event, context);
    }

}