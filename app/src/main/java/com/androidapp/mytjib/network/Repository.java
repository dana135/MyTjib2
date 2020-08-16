package com.androidapp.mytjib.network;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.customer.Order;
import com.androidapp.mytjib.customer.ShippingDetails;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.buy_tickets.Ticket;
import com.androidapp.mytjib.admin_panel.Admin;
import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.login.AdminActivity;
import com.androidapp.mytjib.login.UserActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Path;


public class Repository {

    private MutableLiveData<Admin> currentAdminLive;
    private MutableLiveData<Customer> currentCustomerLive;
    private MutableLiveData<List<Event>> eventsLive;
    private MutableLiveData<List<Event>> liveConcertsLive;
    private MutableLiveData<List<Event>> onlineConcertsLive;
    private MutableLiveData<List<Event>> fanMeetingsLive;
    private MutableLiveData<List<Ticket>> ticketsLive;
    private MutableLiveData<Event> currentEventLive;
    private MutableLiveData<Event> addEventLive;
    private MutableLiveData<List<Venue>> venuesLive;
    private MutableLiveData<List<Order>> orderHistoryLive;
    private MutableLiveData<List<Order>> ordersLive;
    private int currentEventId;

    static Repository repository = new Repository();

    private Repository() {
        this.currentAdminLive = new MutableLiveData<>();
        this.currentCustomerLive = new MutableLiveData<>();
        this.eventsLive = new MutableLiveData<>();
        this.liveConcertsLive = new MutableLiveData<>();
        this.onlineConcertsLive = new MutableLiveData<>();
        this.fanMeetingsLive = new MutableLiveData<>();
        this.ticketsLive = new MutableLiveData<>();
        this.currentEventLive = new MutableLiveData<>();
        this.addEventLive = new MutableLiveData<>();
        this.venuesLive = new MutableLiveData<>();
        this.orderHistoryLive = new MutableLiveData<>();
        this.ordersLive = new MutableLiveData<>();
    }

    public static Repository getInstance() { return repository; }

    public void resetRepository(){
        this.currentAdminLive = new MutableLiveData<>();
        this.currentCustomerLive = new MutableLiveData<>();
        this.eventsLive = new MutableLiveData<>();
        this.liveConcertsLive = new MutableLiveData<>();
        this.onlineConcertsLive = new MutableLiveData<>();
        this.fanMeetingsLive = new MutableLiveData<>();
        this.ticketsLive = new MutableLiveData<>();
        this.currentEventLive = new MutableLiveData<>();
        this.addEventLive = new MutableLiveData<>();
        this.venuesLive = new MutableLiveData<>();
        this.orderHistoryLive = new MutableLiveData<>();
        this.ordersLive = new MutableLiveData<>();
    }

    public void tryAdminLogin(final String email, final String password, final View view, final Context context, final Activity activity){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Admin> call = service.getAdmin(email, password);

        call.enqueue(new Callback<Admin>() {

            @Override
            public void onResponse(@NotNull Call<Admin> call, Response<Admin> response) {
                Intent intent = new Intent(activity, AdminActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                tryCustomerLogin(email, password, view, context, activity);
            }
        });
    }

    public void tryCustomerLogin(String email, String password, final View view, final Context context, final Activity activity){

        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Customer> call = service.getCustomer(email, password);

        call.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer = response.body();
                Bundle bundle = new Bundle();
                bundle.putInt("userId", customer.getId());

                Intent intent = new Intent(activity, UserActivity.class);
                intent.putExtras(bundle);

                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(context, "Incorrect email or password" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<Customer> getCustomerById(int id){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Customer> call = service.getCustomerById(id);

        call.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer = response.body();
                currentCustomerLive.postValue(customer);
            }

            @Override
            public void onFailure(@NotNull Call<Customer> call, Throwable t) {
            }
        });
        return currentCustomerLive;
    }

    public MutableLiveData<List<Event>> getEventsLive() {
        return eventsLive;
    }

    public MutableLiveData<List<Event>> getLiveConcertsLive() {
        return liveConcertsLive;
    }

    public MutableLiveData<List<Event>> getOnlineConcertsLive() {
        return onlineConcertsLive;
    }

    public MutableLiveData<List<Event>> getFanMeetingsLive() {
        return fanMeetingsLive;
    }

    public MutableLiveData<List<Venue>> getVenuesLive() {
        return venuesLive;
    }

    public LiveData<Event> getEventDetailsLive() {
        return currentEventLive;
    }

    public void setEventId(int id) {
        currentEventId = id;
    }

    public void setCurrentCustomer(int id) {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Customer> call = service.getCustomerById(id);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer = response.body();
                currentCustomerLive.postValue(customer);
            }

            @Override
            public void onFailure(@NotNull Call<Customer> call, Throwable t) {
                Log.d("STATE", "failure");
            }
        });
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

    public void getLiveConcertsFromServer(){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Event>> call = service.getLiveConcerts();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                liveConcertsLive.postValue(events);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    public void getOnlineConcertsFromServer(){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Event>> call = service.getOnlineConcerts();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                onlineConcertsLive.postValue(events);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    public void getFanMeetingsFromServer(){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Event>> call = service.getFanMeetings();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                fanMeetingsLive.postValue(events);
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

    public void deleteEvent(View v, final Context context) {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        final View view = v;

        Call<Void> call = service.deleteEvent(currentEventId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Navigation.findNavController(view).navigate(R.id.editEventsFragment);
                Toast.makeText(context, "Event Deleted Successfully" , Toast.LENGTH_LONG).show();
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

    public void addEventTickets(int numOfTickets, String section, int price){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Void> call = service.addEventTickets(currentEventId, numOfTickets, section, price);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
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

    public LiveData<Customer> customerSignUp(final Customer customer, final Context context, final View view){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Customer> call = service.findCustomerByEmail(customer.getEmail());

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer c = response.body();
                currentCustomerLive.postValue(c);
                if(c == null) createAccount(customer, context, view);
                else Toast.makeText(context, "Account with this email already exists" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
            }
        });
        return currentCustomerLive;
    }


    public void createAccount(Customer customer, final Context context, final View view) {
            ApiService service = RetrofitInstance.
                    getRetrofitInstance().create(ApiService.class);

            Call<Void> call = service.customerSignUp(customer);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
                    Toast.makeText(context, "Registered successfully" , Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("STATE", "failure");
                }
            });
    }

    public void updateCustomer(int id, Customer customer, final View view, final Context context){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        final int userId = id;

        Call<Void> call = service.updateCustomer(userId, customer);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.myAccountFragment, bundle);
                Toast.makeText(context, "Account updated successfully" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("STATE", "failure");
            }
        });

    }

    public void checkout(int userId, ShippingDetails shipping) {
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<Void> call = service.checkout(userId, shipping);

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

    public LiveData<List<Order>> getOrderHistoryFromServer(int userId){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Order>> call = service.getOrderHistory(userId);

        call.enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> orders = response.body();
                orderHistoryLive.postValue(orders);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
        return orderHistoryLive;
    }

    public LiveData<List<Order>> getOrdersFromServer(){
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);

        Call<List<Order>> call = service.getOrders();

        call.enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> orders = response.body();
                ordersLive.postValue(orders);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
        return ordersLive;
    }

}
