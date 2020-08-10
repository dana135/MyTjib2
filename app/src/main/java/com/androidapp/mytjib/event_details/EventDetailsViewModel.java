package com.androidapp.mytjib.event_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.network.Repository;
import com.androidapp.mytjib.Ticket;

import java.util.List;

public class EventDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Repository repository;

    public void createRepository(int id){
        repository  = new Repository();
        repository.setEventId(id);
        repository.getEventDetailsFromServer();
    }

    public LiveData<Event> getEventDetails(){
        return repository.getEventDetailsLive();
    }

    public LiveData<List<Ticket>> getTickets() {
        return repository.getTicketsFromServer();
    }
}