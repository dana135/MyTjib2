package com.androidapp.mytjib;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidapp.mytjib.network.ApiService;
import com.androidapp.mytjib.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private MutableLiveData<List<Event>> eventsLive;
    private MutableLiveData<Event> currentEventLive;
    private MutableLiveData<List<Venue>> venuesLive;
    private int currentEventId;

    public Repository() {
        this.eventsLive = new MutableLiveData<>();
        this.currentEventLive = new MutableLiveData<>();
        this.venuesLive = new MutableLiveData<>();
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

    public void getVenuesFromServer(){
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
    }
}
