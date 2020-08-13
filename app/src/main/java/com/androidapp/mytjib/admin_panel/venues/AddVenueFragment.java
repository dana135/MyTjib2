package com.androidapp.mytjib.admin_panel.venues;

import android.os.Bundle;
import android.view.LayoutInflater;
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

public class AddVenueFragment extends Fragment {

    private VenuesViewModel mViewModel;
    private View view;

    public static AddVenueFragment newInstance() {
        return new AddVenueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_venue_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(VenuesViewModel.class);

        mViewModel.createRepository();

        Button btnAdd = view.findViewById(R.id.add_venue_btn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewVenue();
                Navigation.findNavController(view).navigate(R.id.editEventsFragment);
                Toast.makeText(getContext(), "Venue Added Successfully" , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addNewVenue() {
        EditText venueName = view.findViewById(R.id.add_venue_name);
        EditText venueLocation = view.findViewById(R.id.add_venue_location);
        EditText venueCapacity = view.findViewById(R.id.add_venue_capacity);

        String name = venueName.getText().toString();
        String location = venueLocation.getText().toString();
        int capacity = Integer.valueOf(venueCapacity.getText().toString());

        Venue toAdd = new Venue(name, location, capacity);
        mViewModel.addVenue(toAdd);
    }

}
