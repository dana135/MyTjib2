package com.androidapp.mytjib.events;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.login.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private int userId;
    private EventsViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
   //     FragmentTransaction tr = getFragmentManager().beginTransaction();
   //     tr.replace(R.id.container, this);
   //     tr.commit();
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.events_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userId = ((UserActivity) getActivity()).getUserId();

        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);

        List<Event> events = new ArrayList<>();
        final EventsAdapter adapter = new EventsAdapter(getContext(), new EventsAdapter.ClickListener() {
            @Override
            public void onEventClicked(int id) {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", id);
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_eventsFragment_to_eventDetailsFragment, bundle);
            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.createRepository();
        mViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                adapter.setEvents(events);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        recycler = view.findViewById(R.id.edit_events_recycler);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myaccount:
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_eventsFragment_to_myAccountFragment, bundle);
        }
        return true;
    }

}