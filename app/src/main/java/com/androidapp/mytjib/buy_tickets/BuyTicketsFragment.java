package com.androidapp.mytjib.buy_tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

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

public class BuyTicketsFragment extends Fragment {

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
        return inflater.inflate(R.layout.buy_tickets_fragment, container, false);
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
                updateSpinners(tickets);
                adapter.setTickets(tickets);
                adapter.setTextViews(view.findViewById(R.id.sitting_text), view.findViewById(R.id.standing_text), view.findViewById(R.id.vip_text));
                int numOfSitting = 0;
                for(Ticket t : tickets){
                    if(t.getSection().equals("SITTING")) numOfSitting++;
                }
                seatsGrid.setNumColumns((int)Math.sqrt(numOfSitting));
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
        buyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner stand = getView().findViewById(R.id.standing_spinner);
                Spinner vip = getView().findViewById(R.id.vip_spinner);
                int standingTickets = Integer.valueOf(stand.getSelectedItem().toString());
                int vipTickets = Integer.valueOf(vip.getSelectedItem().toString());
                adapter.selectStandingTickets(standingTickets);
                adapter.selectVipTickets(vipTickets);
                ArrayList<Integer> ids = adapter.getTicketIds();

                if(ids.size()>0 | standingTickets!=0 | vipTickets!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", userId);
                    bundle.putIntegerArrayList("ticketIds", ids);
                    bundle.putInt("price", adapter.getTotalPrice());
                    Navigation.findNavController(view).navigate(R.id.action_buyTicketsFragment2_to_shippingDetailsFragment, bundle);
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        seatsGrid = view.findViewById(R.id.tickets_grid);
    }

    private void updateSpinners(List<Ticket> tickets){
        ArrayList<String> standing_view = new ArrayList<>();
        ArrayList<Ticket> standingTickets = new ArrayList<>();
        ArrayList<String> vip_view = new ArrayList<>();
        ArrayList<Ticket> vipTickets = new ArrayList<>();

        Integer availableStanding = 1;
        Integer availableVip = 1;

        standing_view.add("0");
        vip_view.add("0");

        for (Ticket t : tickets) {
            if (t.getSection().equals("STANDING") & !t.getStatus().equals("unavailable")) {
                standingTickets.add(t);
                standing_view.add(availableStanding.toString());
                availableStanding++;
            }
            if (t.getSection().equals("VIP") & !t.getStatus().equals("unavailable")) {
                vipTickets.add(t);
                vip_view.add(availableVip.toString());
                availableVip++;
            }
        }

        Spinner standingSpinner = getView().findViewById(R.id.standing_spinner);
        ArrayAdapter<String> standingAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, standing_view.toArray(new String[0]));
        standingSpinner.setAdapter(standingAdapter);

        Spinner vipSpinner = getView().findViewById(R.id.vip_spinner);
        ArrayAdapter<String> vipAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, vip_view.toArray(new String[0]));
        vipSpinner.setAdapter(vipAdapter);

        this.adapter.setStandingTickets(standingTickets);
        this.adapter.setVipTickets(vipTickets);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myaccount:
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_buyTicketsFragment2_to_myAccountFragment, bundle);
        }
        return true;
    }

}
