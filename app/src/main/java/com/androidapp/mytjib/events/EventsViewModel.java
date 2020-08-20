package com.androidapp.mytjib.events;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

/**
 *  store and manage UI-related data for event actions
 */

public class EventsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(Context context) { // actions made with data from repository
        repository  = Repository.getInstance();
        repository.getEventsFromServer(context);
        repository.getLiveConcertsFromServer(context);
        repository.getOnlineConcertsFromServer(context);
        repository.getFanMeetingsFromServer(context);
    }

    public LiveData<List<Event>> getEvents(){
        return repository.getEventsLive();
    }

    public LiveData<List<Event>> getLiveConcerts(){
        return repository.getLiveConcertsLive();
    }

    public LiveData<List<Event>> getOnlineConcerts(){
        return repository.getOnlineConcertsLive();
    }

    public LiveData<List<Event>> getFanMeetings(){
        return repository.getFanMeetingsLive();
    }

}