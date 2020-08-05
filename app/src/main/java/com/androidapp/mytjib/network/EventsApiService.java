package com.androidapp.mytjib.network;

import com.androidapp.mytjib.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsApiService {

    @GET("events")
    Call<List<Event>> getEvents();

    @GET("events/{id}")
    Call<Event> getEventDetails(@Path("id") int id);
}
