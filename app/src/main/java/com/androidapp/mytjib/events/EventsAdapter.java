package com.androidapp.mytjib.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for event data to display in recycler view
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final Context context;
    private final ClickListener listener;
    private List<Event> events;

    public EventsAdapter(Context context, ClickListener listener) { // constructor
        this.context = context;
        events = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // create and inflate view holder
        View view = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { // update ui with data of current event
        final Event event = events.get(position);
        holder.nameTextView.setText(event.getName()); // set title

        Glide.with(context) // set image
                .load(event.getImage()) // image url
                .into(holder.image);  // image view object

        holder.parent.setOnClickListener(new View.OnClickListener() { // set event click listener
            @Override
            public void onClick(View view) {
                listener.onEventClicked(event.getId());
            }
        });
    }

    // getters and setters

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged(); // refresh the UI
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // view holder for title and image of an event

        private final TextView nameTextView;
        private final ImageView image;
        private final View parent;

        public ViewHolder(@NonNull View itemView) { // constructor
            super(itemView);
            nameTextView = itemView.findViewById(R.id.row_name);
            image = itemView.findViewById(R.id.row_image);
            parent = itemView.findViewById(R.id.row_parent);
        }
    }

    public interface ClickListener {
        void onEventClicked(int id);
    }

}
