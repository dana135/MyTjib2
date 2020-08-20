package com.androidapp.mytjib.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

/**
 * Repository to connect between the user interface and the api service
 * Gets calls from view model and sends them to api service for a response
 */

public class Repository {

    private ApiService service; // the api service used for calls
    // fields to hold live data
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

    public static Repository getInstance() { return repository; } // get an instance of the repository

    private static Repository repository = new Repository(); // static method to create a single repository

    private Repository() { // constructor
        service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);
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

    public void resetRepository(){ // reset the repository (used when logout)
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

    // getters and setters

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

    // try to login as an admin
    public void tryAdminLogin(final String email, final String password, final Context context, final Activity activity){
        Call<Admin> call = service.getAdmin(email, password); // call service to get admin
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(@NotNull Call<Admin> call, Response<Admin> response) { // admin exists
                // start admin app
                Intent intent = new Intent(activity, AdminActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) { // admin doesn't exist
                tryCustomerLogin(email, password, context, activity);
            }
        });
    }

    // try to login as a customer
    public void tryCustomerLogin(String email, String password, final Context context, final Activity activity) {
        Call<Customer> call = service.getCustomer(email, password); // call service to get customer
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) { // customer exists
                // start customer app
                Customer customer = response.body();
                Bundle bundle = new Bundle();
                bundle.putInt("userId", customer.getId());
                Intent intent = new Intent(activity, UserActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) { // customer doesn't exist
                Toast.makeText(context, "Incorrect email or password" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<Customer> getCustomerById(int id, final Context context) { // get a customer by it's id
        Call<Customer> call = service.getCustomerById(id); // call service to get customer
        call.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) { // customer exists
                Customer customer = response.body();
                currentCustomerLive.postValue(customer); // update live data
            }

            @Override
            public void onFailure(@NotNull Call<Customer> call, Throwable t) { // customer doesn't exist
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
        return currentCustomerLive;
    }

    public void getEventsFromServer(final Context context) { // get event list
       Call<List<Event>> call = service.getEvents(); // call service to get events
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) { // success
                List<Event> events = response.body();
                eventsLive.postValue(events); // update live data
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLiveConcertsFromServer(final Context context) { // get live concert event list
        Call<List<Event>> call = service.getLiveConcerts(); // call service to get events
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) { // success
                List<Event> events = response.body();
                liveConcertsLive.postValue(events); // update live data
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getOnlineConcertsFromServer(final Context context){ // get online concert event list
        Call<List<Event>> call = service.getOnlineConcerts(); // call service to get events
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) { // success
                List<Event> events = response.body();
                onlineConcertsLive.postValue(events); // update live data
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFanMeetingsFromServer(final Context context) { // get fan meeting event list
        Call<List<Event>> call = service.getFanMeetings(); // call service to get events
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) { // success
                List<Event> events = response.body();
                fanMeetingsLive.postValue(events); // update live data
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getEventDetailsFromServer(final Context context) { // get current event details
        Call<Event> call = service.getEventDetails(currentEventId); // call service to get event details
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(@NotNull Call<Event> call, Response<Event> response) { // success
                Event event = response.body();
                currentEventLive.postValue(event); // update live data
            }
            @Override
            public void onFailure(Call<Event> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<Venue>> getVenuesFromServer(final Context context) { // get venue list
        Call<List<Venue>> call = service.getVenues(); // call service to get venue list
        call.enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) { // success
                List<Venue> venues = response.body();
                venuesLive.postValue(venues); // update live data
        }
            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
        return venuesLive;
    }

    public LiveData<List<Ticket>> getTicketsFromServer(final Context context) { // get tickets of an event
        Call<List<Ticket>> call = service.getEventTickets(currentEventId); // call service to get event tickets
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) { // success
                List<Ticket> tickets = response.body();
                ticketsLive.postValue(tickets); // update live data
            }
            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
        return ticketsLive;
    }

    public void editEvent(Event event, final Context context) { // edit details of an event
        Call<Void> call = service.editEvent(currentEventId, event); // call service to edit event
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteEvent(View v, final Context context) { // delete an event
        final View view = v;
        Call<Void> call = service.deleteEvent(currentEventId); // call service to delete event
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, Response<Void> response) { // success
                Navigation.findNavController(view).navigate(R.id.editEventsFragment); // go back to event list
                Toast.makeText(context, "Event Deleted Successfully" , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<Event> addEvent(Event event, final Context context) { // add an event
        Call<Event> call = service.addEvent(event); // call service to add event
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) { // success
                Event newEvent = response.body();
                addEventLive.postValue(newEvent); // update live data
            }
            @Override
            public void onFailure(Call<Event> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
        return addEventLive;
    }

    public void addEventTickets(int numOfTickets, String section, int price, final Context context) { // add tickets to an event
        Call<Void> call = service.addEventTickets(currentEventId, numOfTickets, section, price); // call service to add tickets
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addVenue(Venue venue, final Context context) { // add a venue
        Call<Void> call = service.addVenue(venue); // call service to add venue
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<Customer> customerSignUp(final Customer customer, final Context context, final View view){ // sign up as a customer
        ApiService service = RetrofitInstance.
                getRetrofitInstance().create(ApiService.class);
        Call<Customer> call = service.findCustomerByEmail(customer.getEmail()); // call service to check if email already exists
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(@Nullable Call<Customer> call, @Nullable Response<Customer> response) { // found a customer with this email
               Toast.makeText(context, "Account with this email already exists" , Toast.LENGTH_LONG).show(); // email already exists
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) { // account doesn't already exist
                createAccount(customer, context, view);
            }
        });
        return currentCustomerLive;
    }

    public void createAccount(Customer customer, final Context context, final View view) { // create an account
            Call<Void> call = service.customerSignUp(customer); // call service to sign up
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) { // success
                    // go back to login fragment
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
                    Toast.makeText(context, "Registered successfully" , Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) { // failure
                    Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void updateCustomer(int id, Customer customer, final View view, final Context context) { // update account details
        final int userId = id;
        Call<Void> call = service.updateCustomer(userId, customer); // call service to update customer details
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.myAccountFragment, bundle); // go back to my account fragment
                Toast.makeText(context, "Account updated successfully" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkout(int userId, ShippingDetails shipping, final Context context) { // checkout with a new order
        Call<Void> call = service.checkout(userId, shipping); // call service to proceed to checkout
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<Order>> getOrderHistoryFromServer(int userId, final Context context) { // get order history of a customer
        Call<List<Order>> call = service.getOrderHistory(userId); // call service to get order history of the user
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) { // success
                List<Order> orders = response.body();
                orderHistoryLive.postValue(orders); // update live data
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
        return orderHistoryLive;
    }

    public LiveData<List<Order>> getOrdersFromServer(final Context context) { // get all orders
        Call<List<Order>> call = service.getOrders(); // call service to get orders
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) { // success
                List<Order> orders = response.body();
                ordersLive.postValue(orders); // update live data
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
        return ordersLive;
    }

    // first step to initialize the database, there are four levels to keep logic order of database creation
    public void initDbLevelOne(List<Venue> venues, final List<Event> events, final List<String[]> seats, List<Admin> admins,
                               final List<Customer> customers, final List<ShippingDetails> shipping, final Context context) {
        Call<Void> adminsCall = service.addAdmins(admins); // call service to add admins
        Call<Void> venuesCall = service.addVenues(venues); // call service to add venues
        Call<Void> eventsCall = service.addEvents(events); // call service to add events

        adminsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });

        venuesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });

        eventsCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
                initDbLevelTwo(seats, customers, shipping, context); // go to second step
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    // second step to initialize the database
    public void initDbLevelTwo(final List<String[]> seats, final List<Customer> customers, final List<ShippingDetails> shipping, final Context context){
        Call<Void> call = service.addTicketsList(seats); // call service to add tickets
        call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) { // success
                   initDbLevelThree(customers, shipping, context); // go to third step
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) { // failure
                    Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
                }
        });
    }

    // third step to initialize the database
    public void initDbLevelThree(List<Customer> customers,  final List<ShippingDetails> shipping, final Context context){
        Call<Void> call = service.addCustomers(customers); // call service to add customers
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { // success
                initDbLevelFour(shipping, context); // go to fourth step
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) { // failure
                Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initDbLevelFour(List<ShippingDetails> shipping, final Context context) { // fourth step to initialize the database
        int customerId;
        for(int i=1; i<shipping.size(); i++) { // loop through all customers to add orders
            if(i<3) customerId = 1;
            else if(i<5) customerId = 2;
            else customerId = 3;

            Call<Void> call = service.checkout(customerId, shipping.get(i-1)); // call service to make an order
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) { // success
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) { // failure
                    Toast.makeText(context, "Something went wrong, try again" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
