package com.androidapp.mytjib.network;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventsApiService {
    @GET("events")
    Call<List<Event>> getEvents();

    @GET("top_rated?api_key=a921476d861fb36a167704c00cb03bfb&language=en-US&page=1")
    Call<Movie> getMovies();
}
