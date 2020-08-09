package com.androidapp.mytjib.admin_panel.venues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.R;


import java.util.ArrayList;
import java.util.List;

public class VenuesFragment extends Fragment {

    private VenuesViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    public static VenuesFragment newInstance() {
        return new VenuesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.venues_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VenuesViewModel.class);

        List<Venue> venues = new ArrayList<>();
        final VenuesAdapter adapter = new VenuesAdapter(getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.createRepository();
        mViewModel.getVenues().observe(getViewLifecycleOwner(), new Observer<List<Venue>>() {
            @Override
            public void onChanged(List<Venue> venues) {
                adapter.setVenues(venues);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        recycler = view.findViewById(R.id.venue_recycler);
    }

}
