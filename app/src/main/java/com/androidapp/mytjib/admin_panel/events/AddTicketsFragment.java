package com.androidapp.mytjib.admin_panel.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;

public class AddTicketsFragment extends Fragment {

    private EditEventDetailsViewModel mViewModel;
    private View view;
    private boolean standing;
    private boolean sitting;
    private boolean vip;

    public static AddTicketsFragment newInstance() {
        return new AddTicketsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_tickets_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EditEventDetailsViewModel.class);

        final int id = getArguments().getInt("id");
        standing = getArguments().getBoolean("standing");
        sitting = getArguments().getBoolean("sitting");
        vip = getArguments().getBoolean("vip");
        mViewModel.createRepository(id);

        String[] eventTypes = {"STANDING", "SITTING", "VIP"};
        Spinner sectionSpinner = getView().findViewById(R.id.section_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, eventTypes);
        sectionSpinner.setAdapter(adapter);

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

        Button btnFinish = view.findViewById(R.id.finish_add_tickets);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTickets();
                Navigation.findNavController(view).navigate(R.id.action_addTicketsFragment_to_editEventsFragment);
            }
        });

    }

    private void addTickets() {

        EditText num = view.findViewById(R.id.add_num_of_tickets);
        Spinner sectionSpinner = getView().findViewById(R.id.section_spinner);
        EditText ticketPrice = view.findViewById(R.id.add_tickets_price);
        CheckBox markedTickets = getView().findViewById(R.id.add_tickets_marked);

        int numOfTickets = Integer.valueOf(num.getText().toString());

        String section = sectionSpinner.getSelectedItem().toString();
        if((standing & section == "STANDING") | (sitting & section == "SITTING") | (vip & section == "VIP")) {
            Toast.makeText(getContext(), "Already added tickets for this section", Toast.LENGTH_LONG).show();
            return;
        }
        if(section == "STANDING") standing = true;
        if(section == "SITTING") standing = true;
        if(section == "VIP") standing = true;

        int price = Integer.valueOf(ticketPrice.getText().toString());
        boolean marked = markedTickets.isChecked();

        mViewModel.addEventTickets(numOfTickets, section, price, marked);

    }

}
