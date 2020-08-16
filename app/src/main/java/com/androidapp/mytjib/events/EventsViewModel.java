package com.androidapp.mytjib.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.buy_tickets.Ticket;
import com.androidapp.mytjib.network.Repository;

import java.util.List;

public class EventsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(){
        repository  = Repository.getInstance();
        repository.getEventsFromServer();
        repository.getLiveConcertsFromServer();
        repository.getOnlineConcertsFromServer();
        repository.getFanMeetingsFromServer();
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
    public LiveData<Event> getEventDetails(){
        return repository.getEventDetailsLive();
    }
    public void setEventId(int id) {
        repository.setEventId(id);
    }
    public LiveData<List<Ticket>> getTickets() {
        return repository.getTicketsFromServer();
    }

}