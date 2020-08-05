package com.androidapp.mytjib.event_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.R;
import com.bumptech.glide.Glide;

public class EventDetailsFragment extends Fragment {

    private EventDetailsViewModel mViewModel;
    private View view;

    public static EventDetailsFragment newInstance() {
        return new EventDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        view = getView();
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel.class);

        int id = getArguments().getInt("id");
        mViewModel.createRepository(id);
        mViewModel.getEventDetails().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                updateUi(event);
            }
        });
    }

    private void updateUi(Event event) {
        ImageView imageView = view.findViewById(R.id.event_details_image);
        TextView nameView = view.findViewById(R.id.event_details_name);
        Glide.with(imageView)
                .load(event.getImage()) // image url
                .into(imageView);  // imageview object
        nameView.setText(event.getName());
    }

}