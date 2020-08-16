package com.androidapp.mytjib.event_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.mytjib.events.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.events.EventsViewModel;
import com.bumptech.glide.Glide;

public class EventDetailsFragment extends Fragment {

    private int userId;
    private EventDetailsViewModel mViewModel;
    private View view;

    public static EventDetailsFragment newInstance() {
        return new EventDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.event_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel.class);

        final int eventId = getArguments().getInt("eventId");
        final int userId = getArguments().getInt("userId");
        this.userId = userId;

        mViewModel.createRepository(eventId);
        mViewModel.getEventDetails().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                updateUi(event);
            }
        });

        Button button = view.findViewById(R.id.buy_tickets);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", eventId);
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_eventDetailsFragment_to_buyTicketsFragment2, bundle);
            }
        });
    }

    private void updateUi(Event event) {
        ImageView imageView = view.findViewById(R.id.event_details_image);
        TextView nameView = view.findViewById(R.id.event_details_name);
        TextView detailsView = view.findViewById(R.id.event_details_fields);

        Glide.with(imageView)
                .load(event.getImage()) // image url
                .into(imageView);  // imageview object

        nameView.setText(event.getName());

        String details = "";
        details += "Event Type: " + event.getEventType() + "\n";
        details += "Location: " + event.getVenueName() + "\n";
        details += "Date and time: " + event.getDateAndTime() + "\n";
        detailsView.setText(details);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        switch (item.getItemId()) {
            case R.id.menu_myaccount:
                Navigation.findNavController(view).navigate(R.id.myAccountFragment, bundle);
                break;
            case R.id.menu_live:
                Navigation.findNavController(view).navigate(R.id.liveConcertsFragment, bundle);
                break;
            case R.id.menu_online:
                Navigation.findNavController(view).navigate(R.id.onlineConcertsFragment, bundle);
                break;
            case R.id.menu_fan:
                Navigation.findNavController(view).navigate(R.id.fanMeetingsFragment, bundle);
                break;
        }
        return true;
    }

}