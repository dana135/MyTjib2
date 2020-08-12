package com.androidapp.mytjib.network;

import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.customer.Order;
import com.androidapp.mytjib.customer.ShippingDetails;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.admin_panel.Admin;
import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.buy_tickets.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("events")
    Call<List<Event>> getEvents();

    @GET("events/{id}")
    Call<Event> getEventDetails(@Path("id") int id);

    @GET("events/{id}/tickets")
    Call<List<Ticket>> getEventTickets(@Path("id") int id);

    @GET("venues")
    Call<List<Venue>> getVenues();

    @GET("customers/login")
    Call<Customer> getCustomer(@Query("email") String email, @Query("password") String password);

    @GET("/customers/{id}/orders")
    Call<List<Order>> getOrderHistory(@Path("id") int id);

    @GET("admins/login")
    Call<Admin> getAdmin(@Query("email") String email, @Query("password") String password);

    @DELETE("events/{id}")
    Call<Void> deleteEvent(@Path("id") int id);

    @PUT("events/{id}/")
    Call<Void> editEvent(@Path("id") int id, @Body Event event);

    @FormUrlEncoded
    @PUT("events/{id}/addtickets")
    Call<Void> addEventTickets(@Path("id") int id, @Field("numOfTickets") int numOfTickets, @Field("section") String section,
                               @Field("price") int price, @Field("marked") boolean marked);

    @PUT("customers/{id}/checkout")
    Call<Void> checkout(@Path("id") int id, @Body List<Integer> ticketIds, @Body ShippingDetails shipping);

    @POST("events")
    Call<Event> addEvent(@Body Event event);

    @POST("venues")
    Call<Void> addVenue(@Body Venue venue);

    @POST("customers")
    Call<Void> customerSignUp(@Body Customer customer);

}