package com.androidapp.mytjib.buy_tickets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.event_details.EventDetailsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuyTicketsFragment2 extends Fragment {

    private int userId;
    private EventDetailsViewModel mViewModel;
    private GridView seatsGrid;
    private SeatAdapter adapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.buy_tickets_fragment2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel.class);

        final int eventId = getArguments().getInt("eventId");
        final int userId = getArguments().getInt("userId");
        this.userId = userId;

        adapter = new SeatAdapter(getContext());
        mViewModel.createRepository(eventId);

        mViewModel.getTickets().observe(getViewLifecycleOwner(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                adapter.setTickets(tickets);
                seatsGrid.setNumColumns((int)Math.sqrt(tickets.size()));
                seatsGrid.setAdapter(adapter);
                seatsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapter.selectTicket(i);
                    }
                });
            }
        });

        Button buyTickets = view.findViewById(R.id.buy_tickets_gridbtn);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        seatsGrid = view.findViewById(R.id.tickets_grid);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myaccount:
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
         //fixnav       Navigation.findNavController(view).navigate(R.id.action_event_buy_tickets_to_myAccountFragment, bundle);
        }
        return true;
    }

}
