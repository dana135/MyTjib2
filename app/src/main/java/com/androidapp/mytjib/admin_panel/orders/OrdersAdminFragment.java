package com.androidapp.mytjib.admin_panel.orders;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.customer.Order;

import java.util.List;

/**
 * Fragment for orders screen
 * Admin can view all the orders and their details
 */

public class OrdersAdminFragment  extends Fragment {

    private OrdersAdminViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        mViewModel = ViewModelProviders.of(this).get(OrdersAdminViewModel.class);
        mViewModel.createRepository();

        // set adapter for orders
        final OrdersAdminAdapter adapter = new OrdersAdminAdapter(getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // get order list from view model
        mViewModel.getOrders(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                adapter.setOrders(orders);
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // initialize views
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        recycler = view.findViewById(R.id.orders_admin_recycler);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        switch (item.getItemId()) {
            case R.id.orders_admin: // stay in this fragment
               return false;
        }
        return true;
    }

}
