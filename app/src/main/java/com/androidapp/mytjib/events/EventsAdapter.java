package com.androidapp.mytjib.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final Context context;
    private final ClickListener listener;
    private List<Event> events;

    public EventsAdapter(Context context, ClickListener listener) {
            this.context = context;
        events = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.nameTextView.setText(event.getName());

        Glide.with(context)
                .load(event.getImage()) // image url
                .into(holder.image);  // imageview object

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEventClicked(event.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged(); // refresh the UI
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView nameTextView;
        public final ImageView image;
        private final View parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.row_name);
            image = itemView.findViewById(R.id.row_image);
            parent = itemView.findViewById(R.id.row_parent);
        }
    }

    public interface ClickListener{
        void onEventClicked(int id);
    }

}
