package com.androidapp.mytjib.login;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidapp.mytjib.R;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = getView().findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        if (checkCreds()) {
            Bundle bundle = new Bundle();
      //      Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_eventsFragment, bundle);
        } else {
            Context context = getContext();
            CharSequence text = "User does not exist - please try again or sign up";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    private boolean checkCreds() {
        EditText emailBox = getView().findViewById(R.id.email);
        EditText passwdBox = getView().findViewById(R.id.password);
        String email = emailBox.getText().toString();
        String password = passwdBox.getText().toString();
        return true;
    }
}