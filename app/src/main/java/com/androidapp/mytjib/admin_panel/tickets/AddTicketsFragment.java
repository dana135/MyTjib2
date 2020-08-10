package com.androidapp.mytjib.admin_panel.tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;

public class AddTicketsFragment extends Fragment {

    private AddTicketsViewModel mViewModel;
    private View view;

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
        mViewModel = ViewModelProviders.of(this).get(AddTicketsViewModel.class);

        final int id = getArguments().getInt("id");
        mViewModel.createRepository(id);

        String[] eventTypes = {"GROUND", "FLOOR1", "FLOOR2", "FLOOR3", "VIP"};
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
        int price = Integer.valueOf(ticketPrice.getText().toString());
        boolean marked = markedTickets.isChecked();

        mViewModel.addEventTickets(numOfTickets, section, price, marked);

    }

}
