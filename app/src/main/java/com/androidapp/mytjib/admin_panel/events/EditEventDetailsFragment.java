package com.androidapp.mytjib.admin_panel.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * Fragment for editing event details screen
 * Admin can edit details of an existing event
 */

public class EditEventDetailsFragment extends Fragment {

    private EditEventDetailsViewModel mViewModel;
    private View view;
    private Event currentEvent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.edit_event_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EditEventDetailsViewModel.class);
        final int id = getArguments().getInt("id");
        mViewModel.createRepository(id, getContext());

        // get event details from view model via repository
        mViewModel.getEventDetails().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                updateUiEvent(event);
                currentEvent = event;
            }
        });
        // get venue list from view model via repository
        mViewModel.getVenues(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Venue>>() {
            @Override
            public void onChanged(List<Venue> venues) {
                updateUiVenue(venues);
            }
        });

        // declare buttons
        Button btnEdit = view.findViewById(R.id.event_edit);
        Button btnDelete = view.findViewById(R.id.event_delete);

        //  set button for completing edit of this event
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Event e = getUpdatedEvent(); // get the updated event
                if(e != null) {
                    mViewModel.editEvent(e, getContext()); // send event to view model
                    // go back to event list
                    Navigation.findNavController(view).navigate(R.id.editEventsFragment);
                    Toast.makeText(getContext(), "Event Updated Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        // set button for deleting this event
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteEvent(view, getContext());
            }
        });

        // set option to change venue if checkbox is checked
        CheckBox checkbox = getView().findViewById(R.id.checkbox_edit_venue);
        final Spinner spinner = view.findViewById(R.id.edit_venue_spinner);
        spinner.setEnabled(false);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                spinner.setEnabled(b);
            }
        });

        // set clickable text to go to venue details list
        TextView venueDetails = view.findViewById(R.id.venue_details_edit);
        venueDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to venues fragment
                Navigation.findNavController(view).navigate(R.id.venuesFragment);
            }
        });
    }

    private void updateUiEvent(Event event) { // update ui with updated event
        // declare views
        EditText eventName = view.findViewById(R.id.edit_eventName);
        Spinner eventType = view.findViewById(R.id.spinner_type);
        EditText eventTime = view.findViewById(R.id.edit_eventTime);
        EditText eventImage = view.findViewById(R.id.edit_eventImage);

        // set fields with current data of the event
        eventName.setText(event.getName(), TextView.BufferType.EDITABLE);
        eventTime.setText(event.getDateAndTime(), TextView.BufferType.EDITABLE);
        eventImage.setText(event.getImage(), TextView.BufferType.EDITABLE);
        // set event type spinner
        String[] eventTypes = {"Live Concert", "Online Concert", "Fan Meeting"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, eventTypes);
        eventType.setAdapter(adapter);
    }

    private void updateUiVenue(List<Venue> venues) { // update ui with updated venues
        ArrayList<String> venueView = new ArrayList<>(); // hold venue names
        for (Venue v : venues) { // add venues lo list
            venueView.add(String.format("%s", v.getVenueName()));
        }
        // set venue spinner
        Spinner spinner = getView().findViewById(R.id.edit_venue_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, venueView.toArray(new String[0]));
        spinner.setAdapter(adapter);
    }

    private Event getUpdatedEvent() { // get new details of the event
        // declare views
        EditText eventName = view.findViewById(R.id.edit_eventName);
        Spinner eventType = view.findViewById(R.id.spinner_type);
        EditText eventTime = view.findViewById(R.id.edit_eventTime);
        EditText eventImage = view.findViewById(R.id.edit_eventImage);
        Spinner venueName = view.findViewById(R.id.edit_venue_spinner);

        // get data filled by admin (or existing data if no change)
        String name = eventName.getText().toString();
        String type = eventType.getSelectedItem().toString();
        String venue;
        if(venueName.isEnabled()) venue = venueName.getSelectedItem().toString();
        else venue = currentEvent.getVenueName();
        String time = eventTime.getText().toString();
        String image = eventImage.getText().toString();

        if(name.isEmpty() | time.isEmpty() | image.isEmpty()) return null; // invalid data
        return new Event(name, type, image, venue, time); // create and return new updated event
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