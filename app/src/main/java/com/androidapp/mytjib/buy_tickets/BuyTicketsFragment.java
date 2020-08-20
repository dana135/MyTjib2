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

/**
 * Fragment for ticket buying screen
 * Customer can choose and purchase desired tickets (if available)
 */

public class BuyTicketsFragment extends Fragment {

    private int userId;
    private EventDetailsViewModel mViewModel;
    private GridView seatsGrid;
    private SeatAdapter adapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.buy_tickets_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel.class);

        final int eventId = getArguments().getInt("eventId");
        final int userId = getArguments().getInt("userId");
        this.userId = userId;

        adapter = new SeatAdapter(getContext());
        mViewModel.createRepository(eventId, getContext());

        // get event tickets from view model via repository
        mViewModel.getTickets(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                updateSpinners(tickets);
                // set adapter for grid view of tickets
                adapter.setTickets(tickets);
                adapter.setTextViews(view.findViewById(R.id.sitting_text), view.findViewById(R.id.standing_text), view.findViewById(R.id.vip_text));
                int numOfSitting = 0; // tickets for 'sitting' section
                for(Ticket t : tickets){
                    if(t.getSection().equals("SITTING")) numOfSitting++;
                }
                seatsGrid.setNumColumns((int)Math.sqrt(numOfSitting));
                seatsGrid.setAdapter(adapter);
                // click listener for clicking a certain seat
                seatsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapter.selectTicket(i);
                    }
                });
            }
        });

        // set button for buying selected tickets
        Button buyTickets = view.findViewById(R.id.buy_tickets_gridbtn);
        buyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected number of standing and vip tickets
                Spinner stand = getView().findViewById(R.id.standing_spinner);
                Spinner vip = getView().findViewById(R.id.vip_spinner);
                int standingTickets = Integer.valueOf(stand.getSelectedItem().toString());
                int vipTickets = Integer.valueOf(vip.getSelectedItem().toString());
                adapter.selectStandingTickets(standingTickets);
                adapter.selectVipTickets(vipTickets);
                ArrayList<Integer> ids = adapter.getTicketIds(); // ids of selected tickets

                if(ids.size()>0 | standingTickets!=0 | vipTickets!=0) { // user selected at least one ticket
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", userId);
                    bundle.putIntegerArrayList("ticketIds", ids);
                    bundle.putInt("price", adapter.getTotalPrice());
                    // go to shipping details fragment
                    Navigation.findNavController(view).navigate(R.id.action_buyTicketsFragment2_to_shippingDetailsFragment, bundle);
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // initialize views
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        seatsGrid = view.findViewById(R.id.tickets_grid);
    }

    private void updateSpinners(List<Ticket> tickets){ // update spinners with standing and vip tickets
        ArrayList<String> standing_view = new ArrayList<>(); // hold standing tickets number
        ArrayList<String> vip_view = new ArrayList<>(); // hold vip tickets number
        ArrayList<Ticket> standingTickets = new ArrayList<>(); // actual standing tickets
        ArrayList<Ticket> vipTickets = new ArrayList<>(); // actual vip tickets

        Integer availableStanding = 1;
        Integer availableVip = 1;

        standing_view.add("0");
        vip_view.add("0");

        for (Ticket t : tickets) { // add available tickets to the suitable list
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

        // set spinner for standing tickets
        Spinner standingSpinner = getView().findViewById(R.id.standing_spinner);
        ArrayAdapter<String> standingAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, standing_view.toArray(new String[0]));
        standingSpinner.setAdapter(standingAdapter);

        // set spinner for vip tickets
        Spinner vipSpinner = getView().findViewById(R.id.vip_spinner);
        ArrayAdapter<String> vipAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, vip_view.toArray(new String[0]));
        vipSpinner.setAdapter(vipAdapter);

        // send data to adapter
        this.adapter.setStandingTickets(standingTickets);
        this.adapter.setVipTickets(vipTickets);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        switch (item.getItemId()) {
            case R.id.menu_myaccount: // go to my account fragment
                Navigation.findNavController(view).navigate(R.id.myAccountFragment, bundle);
                break;
            case R.id.menu_live: // go to live concerts fragment
                Navigation.findNavController(view).navigate(R.id.liveConcertsFragment, bundle);
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
