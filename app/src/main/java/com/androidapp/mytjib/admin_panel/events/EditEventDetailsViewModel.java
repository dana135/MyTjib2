package com.androidapp.mytjib.admin_panel.events;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.network.Repository;
import com.androidapp.mytjib.admin_panel.venues.Venue;

import java.util.List;

public class EditEventDetailsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(int id, Context context){
        repository  = Repository.getInstance();
        repository.setEventId(id);
        repository.getEventDetailsFromServer(context);
    }

    public LiveData<Event> getEventDetails(){
        return repository.getEventDetailsLive();
    }

    public void editEvent(Event event, Context context) {
        repository.editEvent(event, context);
    }

    public void deleteEvent(View view, Context context){
        repository.deleteEvent(view, context);
    }

    public LiveData<List<Venue>> getVenues( Context context){
        return repository.getVenuesFromServer(context);
    }

    public void addEventTickets(int numOfTickets, String section, int price, Context context){
        repository.addEventTickets(numOfTickets, section, price, context);
    }

}