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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;

/**
 * Fragment for adding event tickets screen
 * Admin can add tickets for sale for the new event
 */

public class AddTicketsFragment extends Fragment {

    private EditEventDetailsViewModel mViewModel;
    private View view;
    private boolean standing; // are standing tickets already added
    private boolean sitting; // are sitting tickets already added
    private boolean vip; // are vip tickets already added
    private Toast currentToast;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.add_tickets_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EditEventDetailsViewModel.class);

        // get data from bundle
        final int id = getArguments().getInt("id");
        standing = getArguments().getBoolean("standing");
        sitting = getArguments().getBoolean("sitting");
        vip = getArguments().getBoolean("vip");
        currentToast = null;
        mViewModel.createRepository(id, getContext());

        // set spinner for tickets section
        String[] eventTypes = {"STANDING", "SITTING", "VIP"};
        Spinner sectionSpinner = getView().findViewById(R.id.section_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, eventTypes);
        sectionSpinner.setAdapter(adapter);

        // set button for adding more tickets to a different section
        Button btnAddMore = view.findViewById(R.id.add_more_tickets);
        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTickets();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putBoolean("standing", standing);
                bundle.putBoolean("sitting", sitting);
                bundle.putBoolean("vip", vip);
                Navigation.findNavController(view).navigate(R.id.action_addTicketsFragment_self, bundle);
            }
        });

        // set button to finish adding
        Button btnFinish = view.findViewById(R.id.finish_add_tickets);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTickets();
                if(currentToast != null) currentToast.cancel();
                currentToast = Toast.makeText(getContext(), "Event added successfully", Toast.LENGTH_LONG);
                currentToast.show();
                // go back to event list
                Navigation.findNavController(view).navigate(R.id.action_addTicketsFragment_to_editEventsFragment);
            }
        });

    }

    private void addTickets() { // add tickets to the event
        // declare views
        EditText num = view.findViewById(R.id.add_num_of_tickets);
        Spinner sectionSpinner = getView().findViewById(R.id.section_spinner);
        EditText ticketPrice = view.findViewById(R.id.add_tickets_price);
        int numOfTickets, price;

        // get data filled by admin

        try {
            numOfTickets = Integer.valueOf(num.getText().toString());
        } catch (Exception e) { // number of tickets is not a number
            currentToast = Toast.makeText(getContext(), "Invalid number of tickets", Toast.LENGTH_SHORT);
            currentToast.show();
            return;
        }

        String section = sectionSpinner.getSelectedItem().toString();
        if((standing & section == "STANDING") | (sitting & section == "SITTING") | (vip & section == "VIP")) { // duplicate section tickets
            Toast.makeText(getContext(), "Already added tickets for this section", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            price = Integer.valueOf(ticketPrice.getText().toString());
        }  catch (Exception e) { // price is not a number
            Toast.makeText(getContext(), "Invalid price", Toast.LENGTH_LONG).show();
            return;
        }

        // update section boolean
        if(section == "STANDING") standing = true;
        if(section == "SITTING") standing = true;
        if(section == "VIP") standing = true;

        mViewModel.addEventTickets(numOfTickets, section, price, getContext()); // tell view model to add tickets via repository

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
