package com.androidapp.mytjib.event_details;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;
import com.androidapp.mytjib.buy_tickets.Ticket;

import java.util.List;

/**
 *  store and manage UI-related data for event details and ticket purchase actions
 */

public class EventDetailsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(int id, Context context) { // actions made with data from repository
        repository  = Repository.getInstance();
        repository.setEventId(id);
        repository.getEventDetailsFromServer(context);
    }

    public LiveData<Event> getEventDetails(){
        return repository.getEventDetailsLive();
    }

    public LiveData<List<Ticket>> getTickets(Context context) {
        return repository.getTicketsFromServer(context);
    }
}