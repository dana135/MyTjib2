package com.androidapp.mytjib.network;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("events")
    Call<List<Event>> getEvents();

    @GET("events/{id}")
    Call<Event> getEventDetails(@Path("id") int id);

    @GET("venues")
    Call<List<Venue>> getVenues();
}
