package com.androidapp.mytjib.buy_tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.customer.MyAccountViewModel;
import com.androidapp.mytjib.customer.ShippingDetails;

import java.util.ArrayList;

/**
 * Fragment for shipping details screen
 * Customer fills payment details to complete ticket purchase
 */

public class ShippingDetailsFragment extends Fragment {

    private MyAccountViewModel mViewModel;
    private View view;
    private int userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.shipping_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(MyAccountViewModel.class);
        mViewModel.createRepository();

        userId = getArguments().getInt("userId");
        final ArrayList<Integer> ticketIds = getArguments().getIntegerArrayList("ticketIds");
        final int price = getArguments().getInt("price");

        // set price text view
        TextView priceText = view.findViewById(R.id.shipping_price);
        priceText.setText("Total price: " + price + "₩");

        // credit card expiration month and year
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] years = {"2021", "2022", "2023", "2025", "2026"};
        Spinner monthSpinner = view.findViewById(R.id.card_month_spinner);
        Spinner yearsSpinner = view.findViewById(R.id.card_year_spinner);
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(monthsAdapter);
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, years);
        yearsSpinner.setAdapter(yearsAdapter);

        // set button for completing purchase
        Button purchaseBtn = view.findViewById(R.id.shipping_purchase);
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShippingDetails shipping = getShipping(ticketIds); // ordered tickets
                if(shipping != null) {
                    mViewModel.checkout(userId, shipping, getContext()); // send to view model for checkout
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", userId);
                    // go back to homepage
                    Navigation.findNavController(view).navigate(R.id.action_shippingDetailsFragment_to_eventsFragment, bundle);
                    Toast.makeText(getContext(), "Purchase successfully sent to your email!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ShippingDetails getShipping(ArrayList<Integer> ticketIds) { // get the shipping details from user
        // declare views
        EditText firstNameEdit = view.findViewById(R.id.shipping_first_name);
        EditText lastNameEdit = view.findViewById(R.id.shipping_last_name);
        EditText creditCardEdit = view.findViewById(R.id.shipping_credit_card);
        Spinner monthSpinner = view.findViewById(R.id.card_month_spinner);
        Spinner yearsSpinner = view.findViewById(R.id.card_year_spinner);

        // get data filled by user
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String creditCard = creditCardEdit.getText().toString();
        String cardExpirationString = monthSpinner.getSelectedItem().toString() + yearsSpinner.getSelectedItem().toString();
        int cardExpiration = Integer.valueOf(cardExpirationString);

        // check validation of fields
        if(creditCard.length() < 8) {
            Toast.makeText(getContext(), "Credit card must contain at least 8 digits", Toast.LENGTH_LONG).show();
            return null;
        }
        for(int i=0; i<creditCard.length(); i++) {
            if(!(Character.isDigit(creditCard.charAt(i)))){
                Toast.makeText(getContext(), "Invalid credit card number", Toast.LENGTH_LONG).show();
                return null;
            }
        }
        if(firstName.isEmpty() | lastName.isEmpty()) return null;
        // create and return new shipping details object
        return new ShippingDetails(firstName, lastName, creditCard, cardExpiration, ticketIds);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        switch (item.getItemId()) {
            case R.id.menu_myaccount: // go to my account fragment
                Navigation.findNavController(view).navigate(R.id.myAccountFragment, bundle);
                break;
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
