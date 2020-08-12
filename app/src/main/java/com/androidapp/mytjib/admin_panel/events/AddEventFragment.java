package com.androidapp.mytjib.admin_panel.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.admin_panel.venues.Venue;

import java.util.ArrayList;
import java.util.List;

public class AddEventFragment extends Fragment {

    private AddEventViewModel mViewModel;
    private View view;

    public static AddEventFragment newInstance() {
        return new AddEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(AddEventViewModel.class);

        mViewModel.createRepository();
        mViewModel.getVenues().observe(getViewLifecycleOwner(), new Observer<List<Venue>>() {
            @Override
            public void onChanged(List<Venue> venues) {
                updateUi(venues);
            }
        });

        String[] eventTypes = {"Live Concert", "Online Concert", "Fan Meeting"};
        Spinner spinner = getView().findViewById(R.id.add_event_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, eventTypes);
        spinner.setAdapter(adapter);

        Button btnAdd = view.findViewById(R.id.add_event_next);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.addEvent(addNewEvent()).observe(getViewLifecycleOwner(), new Observer<Event>() {
                    @Override
                    public void onChanged(Event event) {
                        setEvent(event);
                    }
                });
            }
        });

    }

    private void updateUi(List<Venue> venues) {
        ArrayList<String> venueView = new ArrayList<>();
        for (Venue v : venues) {
            venueView.add(String.format("%s", v.getVenueName()));
        }
        Spinner spinner = getView().findViewById(R.id.add_event_venue);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, venueView.toArray(new String[0]));
        spinner.setAdapter(adapter);
    }

    private Event addNewEvent() {
        EditText eventName = view.findViewById(R.id.add_event_name);
        Spinner eventType = view.findViewById(R.id.add_event_type);
        EditText eventTime = view.findViewById(R.id.add_event_time);
        Spinner eventVenue = view.findViewById(R.id.add_event_venue);
        EditText eventImage = view.findViewById(R.id.add_event_image);

        String name = eventName.getText().toString();
        String type = eventType.getSelectedItem().toString();
        String time = eventTime.getText().toString();
        String venue = eventVenue.getSelectedItem().toString();
        String image = eventImage.getText().toString();

        return new Event(name, type, image, venue, time);
    }

    private void setEvent(Event event) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", event.getId());
        Navigation.findNavController(view).navigate(R.id.addTicketsFragment, bundle);
    }

}
