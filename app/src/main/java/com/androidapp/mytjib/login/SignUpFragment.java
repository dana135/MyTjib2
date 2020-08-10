package com.androidapp.mytjib.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.Customer;
import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.admin_panel.events.AddEventFragment;
import com.androidapp.mytjib.admin_panel.events.AddEventViewModel;
import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.event_details.EventDetailsViewModel;

import java.util.List;

public class SignUpFragment extends Fragment {

    private LoginViewModel mViewModel;
    private View view;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mViewModel.createRepository();

        Button registerBtn = view.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addNewCustomer();
            }
        });

    }

    private void addNewCustomer() {
        EditText usernameText = view.findViewById(R.id.signup_username);
        EditText emailText = view.findViewById(R.id.signup_email);
        EditText passwordText = view.findViewById(R.id.signup_password);

        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if(password.length() < 8){
            Toast.makeText(getContext(), "Password must contain at least 8 characters" , Toast.LENGTH_LONG).show();
            return;
        }
        int altCount = 0;
        for(int i=0; i<email.length(); i++){
            if(email.charAt(i) == '@') altCount++;
        }
        if(email.length()<5 | altCount!=1 | !email.contains(".")){
            Toast.makeText(getContext(), "Invalid email" , Toast.LENGTH_LONG).show();
            return;
        }

        Customer newCustomer = new Customer(username, email, password);
        mViewModel.customerSignUp(newCustomer);

        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        Toast.makeText(getContext(), "Registered successfully" , Toast.LENGTH_LONG).show();
    }

}
