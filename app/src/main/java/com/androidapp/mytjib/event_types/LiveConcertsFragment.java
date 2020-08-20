package com.androidapp.mytjib.event_types;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.events.EventsAdapter;
import com.androidapp.mytjib.events.EventsViewModel;

import java.util.List;

/**
 * Fragment for live concerts screen
 * List of events of type 'live concerts'
 */

public class LiveConcertsFragment extends Fragment {

    private int userId;
    private EventsViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.events_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        userId = getArguments().getInt("userId");
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        mViewModel.createRepository(getContext());

        // set adapter and click listener for each event
        final EventsAdapter adapter = new EventsAdapter(getContext(), new EventsAdapter.ClickListener() {
            @Override
            public void onEventClicked(int id) {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", id);
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.eventDetailsFragment, bundle);
            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // get event list from view model
        mViewModel.getLiveConcerts().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                adapter.setEvents(events);
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
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        switch (item.getItemId()) {
            case R.id.menu_myaccount: // go to my account fragment
                Navigation.findNavController(view).navigate(R.id.myAccountFragment, bundle);
                break;
            case R.id.menu_online: // go to online concerts fragment
                Navigation.findNavController(view).navigate(R.id.onlineConcertsFragment, bundle);
                break;
            case R.id.menu_fan: // go to fan meetings fragment
                Navigation.findNavController(view).navigate(R.id.fanMeetingsFragment, bundle);
                break;
        }
        return true;
    }

}
