package com.androidapp.mytjib.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.R;
import com.androidapp.mytjib.admin_panel.Admin;


public class LoginFragment extends Fragment {

    private Admin admin;
    private Customer customer;
    private LoginViewModel mViewModel;
    private View view;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.login_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mViewModel.createRepository();
        view = getView();
        admin = null;
        customer = null;

        final EditText emailEditText = view.findViewById(R.id.login_email);
        final EditText passwordEditText = view.findViewById(R.id.login_password);
        final TextView signupEditText = view.findViewById(R.id.login_signup);
        final Button loginButton = view.findViewById(R.id.login_btn);

        signupEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mViewModel.login(email, password, view, getContext(), getActivity());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.init_db:
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_initDatabaseFragment);
                break;
        }
        return true;
    }

}
