package com.androidapp.mytjib.buy_tickets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

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
    //    final int eventId = getArguments().getInt("eventId");
        final ArrayList<Integer> ticketIds = getArguments().getIntegerArrayList("ticketIds");
        final int price = getArguments().getInt("price");
        mViewModel.createRepository(userId);

        TextView priceText = view.findViewById(R.id.shipping_price);
        priceText.setText("Total price: " + price + "₩");

        Button purchaseBtn = view.findViewById(R.id.shipping_purchase);
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer j = userId;
                Log.d("STF", j.toString());
                mViewModel.checkout(userId, getShipping(ticketIds));
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_shippingDetailsFragment_to_eventsFragment, bundle);
                Toast.makeText(getContext(), "Purchase successfully sent to your email!" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private ShippingDetails getShipping(ArrayList<Integer> ticketIds){
        EditText firstNameEdit = view.findViewById(R.id.shipping_first_name);
        EditText lastNameEdit = view.findViewById(R.id.shipping_last_name);
        EditText creditCardEdit = view.findViewById(R.id.shipping_credit_card);
        EditText cardExpirationEdit = view.findViewById(R.id.shipping_card_expiration);

        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String creditCard = creditCardEdit.getText().toString();
        int cardExpiration = Integer.valueOf(cardExpirationEdit.getText().toString());

        return new ShippingDetails(firstName, lastName, creditCard, cardExpiration, ticketIds);
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
