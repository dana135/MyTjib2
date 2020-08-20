package com.androidapp.mytjib.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.R;

/**
 * Fragment for sign up screen
 * Create a new customer (user) account
 */

public class SignUpFragment extends Fragment {

    private LoginViewModel mViewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.sign_up_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mViewModel.createRepository();

        // register the new account when button is pressed
        Button registerBtn = view.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCustomer();
            }
        });

    }

    private void addNewCustomer() { // create a new account
        // declare views
        EditText usernameText = view.findViewById(R.id.signup_username);
        EditText emailText = view.findViewById(R.id.signup_email);
        EditText passwordText = view.findViewById(R.id.signup_password);
        EditText passwordConfText = view.findViewById(R.id.signup_password_conf);

        // get data filled by user in edit text
        final String username = usernameText.getText().toString();
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        String passwordConf = passwordConfText.getText().toString();

        // check if data meets the requirements

        if(username.isEmpty()) {
            Toast.makeText(getContext(), "Invalid username" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(username.length() > 20) {
            Toast.makeText(getContext(), "Username must be up to 20 characters long" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 8) {
            Toast.makeText(getContext(), "Password must contain at least 8 characters" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordConf)) {
            Toast.makeText(getContext(), "Passwords do not match" , Toast.LENGTH_SHORT).show();
            return;
        }

        int altCount = 0;
        for(int i=0; i<email.length(); i++) { // email must have exactly one '@'
            if(email.charAt(i) == '@') altCount++;
        }
        if(email.length()<5 | altCount!=1 | !email.contains(".")){
            Toast.makeText(getContext(), "Invalid email" , Toast.LENGTH_SHORT).show();
            return;
        }

        Customer newCustomer = new Customer(username, email, password); // create a new customer object
        mViewModel.customerSignUp(newCustomer, getContext(), view); // send the new customer to view model for registration via repository

    }

}
