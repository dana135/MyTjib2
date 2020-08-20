package com.androidapp.mytjib.admin_panel.venues;

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

import java.util.List;

/**
 * Fragment for venues screen
 * Admin can view all the venues and their details
 */

public class VenuesFragment extends Fragment {

    private VenuesViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.venues_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        mViewModel = ViewModelProviders.of(this).get(VenuesViewModel.class);
        mViewModel.createRepository(getContext());

        // set adapter for venues
        final VenuesAdapter adapter = new VenuesAdapter(getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // get venue list from view model
        mViewModel.getVenues().observe(getViewLifecycleOwner(), new Observer<List<Venue>>() {
            @Override
            public void onChanged(List<Venue> venues) {
                adapter.setVenues(venues);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // initialize views
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        recycler = view.findViewById(R.id.venue_recycler);
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
