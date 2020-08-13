package com.androidapp.mytjib.event_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;
import com.androidapp.mytjib.buy_tickets.Ticket;

import java.util.List;

public class EventDetailsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(int id){
        repository  = Repository.getInstance();
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