package com.androidapp.mytjib;


import androidx.lifecycle.MutableLiveData;

import com.androidapp.mytjib.network.EventsApiService;
import com.androidapp.mytjib.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private MutableLiveData<Movie> eventsLive;

    public Repository() {
        this.eventsLive = new MutableLiveData<>();
    }

    public MutableLiveData<Movie> getEventsLive() {
        return eventsLive;
    }

    public void getEventsFromServer(){
        EventsApiService service = RetrofitInstance.
                getRetrofitInstance().create(EventsApiService.class);

    //    Call<List<Movie>> call = service.getEvents();
        Call<Movie> call = service.getMovies();

        call.enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie events = response.body();
                eventsLive.postValue(events);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }
}
