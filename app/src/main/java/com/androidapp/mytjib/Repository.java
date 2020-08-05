package com.androidapp.mytjib;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidapp.mytjib.network.EventsApiService;
import com.androidapp.mytjib.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private MutableLiveData<List<Event>> eventsLive;
    private MutableLiveData<Event> currentEventLive;
    private int currentEventId;

    public Repository() {
        this.eventsLive = new MutableLiveData<>();
        this.currentEventLive = new MutableLiveData<>();
    }

    public MutableLiveData<List<Event>> getEventsLive() {
        return eventsLive;
    }

    public void getEventsFromServer(){
        EventsApiService service = RetrofitInstance.
                getRetrofitInstance().create(EventsApiService.class);

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
        EventsApiService service = RetrofitInstance.
                getRetrofitInstance().create(EventsApiService.class);

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
}
