package com.androidapp.mytjib.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class MyAccountFragment extends Fragment  {

    private Customer customer;
    private int userId;
    private MyAccountViewModel mViewModel;
    private RecyclerView recycler;
    private View view;

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyAccountViewModel.class);
        userId = getArguments().getInt("userId");
        customer = null;

        List<Order> orders = new ArrayList<>();
        final OrdersAdapter adapter = new OrdersAdapter(getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.createRepository(userId);
        mViewModel.getOrderHistory(userId).observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                adapter.setOrders(orders);
            }
        });

        mViewModel.getCustomerById(userId).observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(Customer cu) {
                setCustomer(cu);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        recycler = view.findViewById(R.id.orders_recycler);
    }

    public void setCustomer(Customer c){
        customer = c;
        String helloString = "Hello, " + customer.getUsername() + "! Your order history:";
        TextView helloText = view.findViewById(R.id.account_hello);
        helloText.setText(helloString);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myaccount:
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_shippingDetailsFragment_to_myAccountFragment, bundle);
        }
        return true;
    }

}
