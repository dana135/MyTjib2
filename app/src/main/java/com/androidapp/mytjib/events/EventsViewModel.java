package com.androidapp.mytjib.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class EventsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Repository repository;

    public void createRepository(){
        repository  = new Repository();
        repository.getEventsFromServer();
    }

    public LiveData<List<Event>> getEvents(){
        return repository.getEventsLive();
    }
}