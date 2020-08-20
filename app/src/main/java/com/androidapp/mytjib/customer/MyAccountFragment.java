package com.androidapp.mytjib.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.R;

import java.util.List;

/**
 * Fragment for personal customer account screen
 * Contains order history and an option to change personal details
 */

public class MyAccountFragment extends Fragment {

    private Customer customer;
    private int userId;
    private MyAccountViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.my_account_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        mViewModel = ViewModelProviders.of(this).get(MyAccountViewModel.class);
        mViewModel.createRepository();
        userId = getArguments().getInt("userId");
        customer = null;

        // set adapter for recycler view
        final OrdersAdapter adapter = new OrdersAdapter(getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // get order history from view model
        mViewModel.getOrderHistory(userId, getContext()).observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                adapter.setOrders(orders);
            }
        });

        // get current customer from view model
        mViewModel.getCustomerById(userId, getContext()).observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(Customer cu) {
                setCustomer(cu);
            }
        });

        // set button for changing account details
        Button changeDetails = view.findViewById(R.id.change_account_btn);
        changeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                // go to change details fragment
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_changeDetailsFragment, bundle);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // initialize views
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        recycler = view.findViewById(R.id.orders_recycler);
    }

    public void setCustomer(Customer c){ // say hello to customer
        customer = c;
        String helloString = "Hello, " + customer.getUsername() + "! Your order history:";
        TextView helloText = view.findViewById(R.id.account_hello);
        helloText.setText(helloString);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        switch (item.getItemId()) {
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
