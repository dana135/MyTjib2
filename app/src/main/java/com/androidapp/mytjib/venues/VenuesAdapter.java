package com.androidapp.mytjib.venues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.Venue;
import com.androidapp.mytjib.events.EventsAdapter;

import java.util.ArrayList;
import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    private final Context context;
    private List<Venue> venues;

    public VenuesAdapter(Context context) {
        this.context = context;
        venues = new ArrayList<>();
    }

    @NonNull
    @Override
    public VenuesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.venue_row, parent, false);
        return new VenuesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenuesAdapter.ViewHolder holder, int position) {
        final Venue venue = venues.get(position);

        String venueDetails = "";
        venueDetails += "ID: " + venue.getId() + "\n";
        venueDetails += "Name: " + venue.getVenueName() + "\n";
        venueDetails += "Seats: " + venue.getSeats() + "\n";
        venueDetails += "Location: " + venue.getLocation() + "\n";
        venueDetails += "Capacity: " + venue.getCapacity() + "\n";

        holder.nameTextView.setText(venueDetails);

    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public void setVenues(List<Venue> venues) {
        this.venues  = venues;
        notifyDataSetChanged(); // refresh the UI
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.venue_details);
        }
    }

}
