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

/**
 * API service to dispatch network calls
 * Request method and URL specified in the annotation
 */

public interface ApiService {

    @GET("events")
    Call<List<Event>> getEvents();

    @GET("events/liveconcerts")
    Call<List<Event>> getLiveConcerts();

    @GET("events/onlineconcerts")
    Call<List<Event>> getOnlineConcerts();

    @GET("events/fanmeetings")
    Call<List<Event>> getFanMeetings();

    @GET("events/{id}")
    Call<Event> getEventDetails(@Path("id") int id);

    @GET("events/{id}/tickets")
    Call<List<Ticket>> getEventTickets(@Path("id") int id);

    @GET("venues")
    Call<List<Venue>> getVenues();

    @GET("customers/findemail")
    Call<Customer> findCustomerByEmail(@Query("email") String email);

    @GET("customers/login")
    Call<Customer> getCustomer(@Query("email") String email, @Query("password") String password);

    @GET("customers/{id}")
    Call<Customer> getCustomerById(@Path("id") int id);

    @GET("/customers/{id}/orders")
    Call<List<Order>> getOrderHistory(@Path("id") int id);

    @GET("admins/login")
    Call<Admin> getAdmin(@Query("email") String email, @Query("password") String password);

    @GET("orders")
    Call<List<Order>> getOrders();

    @DELETE("events/{id}")
    Call<Void> deleteEvent(@Path("id") int id);

    @PUT("events/{id}/")
    Call<Void> editEvent(@Path("id") int id, @Body Event event);

    @FormUrlEncoded
    @PUT("events/{id}/addtickets")
    Call<Void> addEventTickets(@Path("id") int id, @Field("numOfTickets") int numOfTickets, @Field("section") String section,
                               @Field("price") int price);

    @PUT("events/addticketslist")
    Call<Void> addTicketsList(@Body List<String[]> details);

    @PUT("customers/{id}/checkout")
    Call<Void> checkout(@Path("id") int userId, @Body ShippingDetails shipping);

    @PUT("customers/{id}")
    Call<Void> updateCustomer(@Path("id") int userId, @Body Customer customer);

    @POST("events")
    Call<Event> addEvent(@Body Event event);

    @POST("eventslist")
    Call<Void> addEvents(@Body List<Event> events);

    @POST("venues")
    Call<Void> addVenue(@Body Venue venue);

    @POST("venueslist")
    Call<Void> addVenues(@Body List<Venue> venues);

    @POST("customers")
    Call<Void> customerSignUp(@Body Customer customer);

    @POST("customerslist")
    Call<Void> addCustomers(@Body List<Customer> customers);

    @POST("adminslist")
    Call<Void> addAdmins(@Body List<Admin> admins);

}