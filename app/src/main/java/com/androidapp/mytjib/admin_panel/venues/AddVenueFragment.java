package com.androidapp.mytjib.admin_panel.venues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;

/**
 * Fragment for adding venue screen
 * Admin can add a new venue
 */

public class AddVenueFragment extends Fragment {

    private VenuesViewModel mViewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.add_venue_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(VenuesViewModel.class);
        mViewModel.createRepository(getContext());

        // set button for adding a new venue
        Button btnAdd = view.findViewById(R.id.add_venue_btn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewVenue();
            }
        });
    }

    public void addNewVenue() { // add a new venue
        // declare views
        EditText venueName = view.findViewById(R.id.add_venue_name);
        EditText venueLocation = view.findViewById(R.id.add_venue_location);
        EditText venueCapacity = view.findViewById(R.id.add_venue_capacity);

        // get data filled by admin
        int capacity;
        String name = venueName.getText().toString();
        String location = venueLocation.getText().toString();
        try {
            capacity = Integer.valueOf(venueCapacity.getText().toString());
        } catch(Exception e) { // capacity is not a number
            Toast.makeText(getContext(), "Invalid capacity", Toast.LENGTH_LONG).show();
            return;
        }

        if(name.isEmpty() | location.isEmpty()) return; // invalid data

        // create and add a new venue
        Venue toAdd = new Venue(name, location, capacity);
        mViewModel.addVenue(toAdd, getContext());

        // go back to event list
        Navigation.findNavController(view).navigate(R.id.editEventsFragment);
        Toast.makeText(getContext(), "Venue Added Successfully" , Toast.LENGTH_LONG).show();
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
