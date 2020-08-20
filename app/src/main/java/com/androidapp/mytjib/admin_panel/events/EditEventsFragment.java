package com.androidapp.mytjib.admin_panel.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.events.EventsAdapter;

import java.util.List;

/**
 * Fragment for event list screen
 * Home page of the admin app with a list of all events
 */

public class EditEventsFragment extends Fragment {

    private EditEventsViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.edit_events_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        mViewModel = ViewModelProviders.of(this).get(EditEventsViewModel.class);
        mViewModel.createRepository(getContext());

        // set adapter and click listener for each event
        final EventsAdapter adapter = new EventsAdapter(getContext(), new EventsAdapter.ClickListener() {
            @Override
            public void onEventClicked(int id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                // go to event details fragment
                Navigation.findNavController(view).navigate(R.id.action_editEventsFragment_to_editEventDetailsFragment, bundle);
            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // get event list from view model
        mViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                adapter.setEvents(events);
            }
        });

        // set button for adding a new event
        Button btnAddEvent = view.findViewById(R.id.btn_add_event);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 // go to add event fragment
                Navigation.findNavController(view).navigate(R.id.addEventFragment);
            }
        });

        // set button for adding a new venue
        Button btnAddVenue = view.findViewById(R.id.btn_add_venue);
        btnAddVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to add venue fragment
                Navigation.findNavController(view).navigate(R.id.addVenueFragment);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // initialize views
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        recycler = view.findViewById(R.id.edit_events_recycler);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        switch (item.getItemId()) {
            case R.id.orders_admin: // go to orders fragment
                Navigation.findNavController(view).navigate(R.id.ordersAdminFragment);
        }
        return true;
    }
}