package com.androidapp.mytjib.admin_panel.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.admin_panel.venues.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for adding event screen
 * Admin can add a new event
 */

public class AddEventFragment extends Fragment {

    private EditEventsViewModel mViewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.add_event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EditEventsViewModel.class);
        mViewModel.createRepository(getContext());

        // get venue list from view model via repository
        mViewModel.getVenues(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Venue>>() {
            @Override
            public void onChanged(List<Venue> venues) {
                updateUi(venues);
            }
        });

        // set spinner for event type
        String[] eventTypes = {"Live Concert", "Online Concert", "Fan Meeting"};
        Spinner spinner = view.findViewById(R.id.add_event_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, eventTypes);
        spinner.setAdapter(adapter);

        // set button for adding a new event
        Button btnAdd = view.findViewById(R.id.add_event_next);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addNewEvent() != null) {
                    mViewModel.addEvent(addNewEvent(), getContext()).observe(getViewLifecycleOwner(), new Observer<Event>() {
                        @Override
                        public void onChanged(Event event) {
                            setEvent(event);
                        }
                    });
                }
            }
        });
    }

    private void updateUi(List<Venue> venues) { // update ui with updated venues
        ArrayList<String> venueView = new ArrayList<>(); // hold venue names
        for (Venue v : venues) { // add venues lo list
            venueView.add(String.format("%s", v.getVenueName()));
        }
        // set venue spinner
        Spinner spinner = getView().findViewById(R.id.add_event_venue);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, venueView.toArray(new String[0]));
        spinner.setAdapter(adapter);
    }

    private Event addNewEvent() { // add a new event
        // declare views
        EditText eventName = view.findViewById(R.id.add_event_name);
        Spinner eventType = view.findViewById(R.id.add_event_type);
        EditText eventTime = view.findViewById(R.id.add_event_time);
        Spinner eventVenue = view.findViewById(R.id.add_event_venue);
        EditText eventImage = view.findViewById(R.id.add_event_image);

        // get data filled by admin
        String name = eventName.getText().toString();
        String type = eventType.getSelectedItem().toString();
        String time = eventTime.getText().toString();
        String venue = eventVenue.getSelectedItem().toString();
        String image = eventImage.getText().toString();

        if(name.isEmpty() | time.isEmpty() | image.isEmpty()) return null;  // invalid data
        return new Event(name, type, image, venue, time); // create and add a new event
    }

    private void setEvent(Event event) { // prepare for next step
        Bundle bundle = new Bundle();
        bundle.putInt("id", event.getId());
        bundle.putBoolean("standing", false);
        bundle.putBoolean("sitting", false);
        bundle.putBoolean("vip", false);
        // go to add tickets fragment
        Navigation.findNavController(view).navigate(R.id.addTicketsFragment, bundle);
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