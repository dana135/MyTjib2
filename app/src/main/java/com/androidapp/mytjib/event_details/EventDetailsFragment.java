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
import com.bumptech.glide.Glide;

/**
 * Fragment for event details screen
 * Contains an image and information of a specific event
 */

public class EventDetailsFragment extends Fragment {

    private int userId;
    private EventDetailsViewModel mViewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.event_details_fragment, container, false);
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

        // get event details from view model
        mViewModel.createRepository(eventId, getContext());
        mViewModel.getEventDetails().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) { // send updated event to update ui
                updateUi(event);
            }
        });

        // set button for buying tickets to this event
        Button button = view.findViewById(R.id.buy_tickets);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", eventId);
                bundle.putInt("userId", userId);
                // go to buy tickets fragment
                Navigation.findNavController(view).navigate(R.id.action_eventDetailsFragment_to_buyTicketsFragment2, bundle);
            }
        });
    }

    private void updateUi(Event event) { // update ui with current event details
        // declare views
        ImageView imageView = view.findViewById(R.id.event_details_image);
        TextView nameView = view.findViewById(R.id.event_details_name);
        TextView detailsView = view.findViewById(R.id.event_details_fields);

        // set views

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