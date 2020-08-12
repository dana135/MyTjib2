package com.androidapp.mytjib.buy_tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.admin_panel.events.EditEventDetailsFragment;
import com.androidapp.mytjib.admin_panel.events.EditEventDetailsViewModel;
import com.androidapp.mytjib.customer.MyAccountViewModel;
import com.androidapp.mytjib.customer.ShippingDetails;
import com.androidapp.mytjib.events.Event;

import java.util.ArrayList;

public class ShippingDetailsFragment  extends Fragment {


    private MyAccountViewModel mViewModel;
    private View view;
    private int userId;

    public static ShippingDetailsFragment newInstance() {
        return new ShippingDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shipping_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(MyAccountViewModel.class);

        userId = getArguments().getInt("userId");
        final int eventId = getArguments().getInt("eventId");
        final ArrayList<Integer> ticketIds = getArguments().getIntegerArrayList("ticketIds");
        final int price = getArguments().getInt("price");
        mViewModel.createRepository();
    }

    private ShippingDetails getShipping(){
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myaccount:
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
          //fixnav      Navigation.findNavController(view).navigate(R.id.action_event_buy_tickets_to_myAccountFragment, bundle);
        }
        return true;
    }

}
