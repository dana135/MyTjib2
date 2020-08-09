package com.androidapp.mytjib;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.network.ApiService;
import com.androidapp.mytjib.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private MutableLiveData<List<Event>> eventsLive;
    private MutableLiveData<List<Ticket>> ticketsLive;
    private MutableLiveData<Event> currentEventLive;
    private MutableLiveData<Event> addEventLive;
    private MutableLiveData<List<Venue>> venuesLive;
    private int currentEventId;

    public Repository() {
        this.eventsLive = new MutableLiveData<>();
        this.currentEventLive = new MutableLiveData<>();
        this.addEventLive = new MutableLiveData<>();
        this.venuesLive = new MutableLiveData<>();
        this.ticketsLive = new MutableLiveData<>();
    }

    public MutableLiveData<List<Event>> getEventsLive() {
        return eventsLive;
    }

    public MutableLiveData<List<Venue>> getVenuesLive() {
        return venuesLive;
    }

    public void getEventsFromServer(){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

       Call<List<Event>> call = service.getEvents();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                eventsLive.postValue(events);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    public void getEventDetailsFromServer() {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Event> call = service.getEventDetails(currentEventId);

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event event = response.body();
                currentEventLive.postValue(event);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });
    }

    public LiveData<Event> getEventDetailsLive() {
        return currentEventLive;
    }

    public void setEventId(int id) {
        currentEventId = id;
    }

    public LiveData<List<Venue>> getVenuesFromServer(){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Venue>> call = service.getVenues();

        call.enqueue(new Callback<List<Venue>>() {

            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                List<Venue> venues = response.body();
                venuesLive.postValue(venues);
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {

            }
        });
        return venuesLive;
    }

    public LiveData<List<Ticket>> getTicketsFromServer() {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Ticket>> call = service.getEventTickets(currentEventId);

        call.enqueue(new Callback<List<Ticket>>() {

            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> tickets = response.body();
                ticketsLive.postValue(tickets);
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {

            }
        });
        return ticketsLive;
    }

    public void editEvent(Event event) {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Void> call = service.editEvent(currentEventId, event);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("STATE", response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("STATE", "failure");
            }
        });
    }

    public void deleteEvent() {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Void> call = service.deleteEvent(currentEventId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("STATE", response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("STATE", "failure");
            }
        });
    }

    public LiveData<Event> addEvent(Event event) {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Event> call = service.addEvent(event);

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Log.d("STATE", response.message());
                Event newEvent = response.body();
                addEventLive.postValue(newEvent);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.d("STATE", "failure");
            }
        });
        return addEventLive;
    }

    public void addVenue(Venue venue) {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Void> call = service.addVenue(venue);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("STATE", response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("STATE", "failure");
            }
        });
    }

}
