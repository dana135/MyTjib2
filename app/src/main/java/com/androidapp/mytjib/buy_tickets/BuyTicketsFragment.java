package com.androidapp.mytjib.buy_tickets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.Ticket;
import com.androidapp.mytjib.event_details.EventDetailsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuyTicketsFragment extends Fragment {

    private View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();

        EventDetailsViewModel mViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel.class);
        final int id = getArguments().getInt("id");
        mViewModel.createRepository(id);

        mViewModel.getTickets().observe(getViewLifecycleOwner(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                updateUi(tickets);
            }
        });

        CheckBox checkbox = getView().findViewById(R.id.checkbox_marked_tickets);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Spinner spinner = view.findViewById(R.id.spinner);
                spinner.setEnabled(b);
            }
        });

    }

    private void updateUi(List<Ticket> tickets) {
        ArrayList<String> tickets_view = new ArrayList<>();
        for (Ticket t : tickets) {
            if (!t.isMarked()) continue;
            tickets_view.add(String.format(Locale.KOREA, "%d - %d₩", t.getPosition(), t.getPrice()));
        }
        Spinner spinner = getView().findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, tickets_view.toArray(new String[0]));
        spinner.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_buy_tickets, container, false);
    }
}