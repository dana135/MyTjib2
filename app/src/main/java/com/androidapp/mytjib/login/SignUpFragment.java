package com.androidapp.mytjib.login;

import android.database.Observable;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.R;

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
        final Customer[] customer = {null};
        EditText usernameText = view.findViewById(R.id.signup_username);
        EditText emailText = view.findViewById(R.id.signup_email);
        EditText passwordText = view.findViewById(R.id.signup_password);
        EditText passwordConfText = view.findViewById(R.id.signup_password_conf);

        final String username = usernameText.getText().toString();
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        String passwordConf = passwordConfText.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(getContext(), "Invalid username" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 8){
            Toast.makeText(getContext(), "Password must contain at least 8 characters" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordConf)){
            Toast.makeText(getContext(), "Passwords do not match" , Toast.LENGTH_SHORT).show();
            return;
        }

        int altCount = 0;
        for(int i=0; i<email.length(); i++){
            if(email.charAt(i) == '@') altCount++;
        }
        if(email.length()<5 | altCount!=1 | !email.contains(".")){
            Toast.makeText(getContext(), "Invalid email" , Toast.LENGTH_SHORT).show();
            return;
        }

        Customer newCustomer = new Customer(username, email, password);
        mViewModel.customerSignUp(newCustomer, getContext(), view);

    }

}
