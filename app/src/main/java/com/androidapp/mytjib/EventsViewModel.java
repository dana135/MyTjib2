package com.androidapp.mytjib;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class EventsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Repository repository;

    public void createRepository(){
        repository  = new Repository();

        repository.getEventsFromServer();
    }

    public LiveData<Movie> getEvents(){
        return repository.getEventsLive();
    }
}