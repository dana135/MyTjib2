package com.androidapp.mytjib.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;

/**
 * Fragment for changing account details screen
 * Customer can enter new details and update their account
 */

public class ChangeDetailsFragment  extends Fragment {

    private int userId;
    private MyAccountViewModel mViewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.change_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        userId = getArguments().getInt("userId");
        mViewModel = ViewModelProviders.of(this).get(MyAccountViewModel.class);
        mViewModel.createRepository();

       updateUi();
    }

    public void updateUi() { // update the ui
        // declare views
        final EditText usernameText = view.findViewById(R.id.edit_username);
        final EditText emailText = view.findViewById(R.id.edit_email);
        final EditText passwordText = view.findViewById(R.id.edit_password);
        final EditText passwordConfirmText = view.findViewById(R.id.edit_password_confirm);
        final CheckBox usernameCheckbox = view.findViewById(R.id.username_checkbox);
        final CheckBox emailCheckbox = view.findViewById(R.id.email_checkbox);
        final CheckBox passwordCheckbox = view.findViewById(R.id.password_checkbox);

        // set edit texts as disabled by default
        // user will choose which fields they would like to enable
        usernameText.setEnabled(false);
        emailText.setEnabled(false);
        passwordText.setEnabled(false);
        passwordConfirmText.setEnabled(false);

        // edit text will be enabled or disabled according to checkbox
        // enabled when checked, disabled when not checked
        usernameCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) usernameText.setEnabled(true);
                else usernameText.setEnabled(false);
                }
            });
        emailCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) emailText.setEnabled(true);
                else emailText.setEnabled(false);
            }
        });
        passwordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    passwordText.setEnabled(true);
                    passwordConfirmText.setEnabled(true);
                }
                else{
                    passwordText.setEnabled(false);
                    passwordConfirmText.setEnabled(false);
                }
            }
        });

        // set button for submitting the new account data
        Button submitBtn = view.findViewById(R.id.edit_account_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer updatedCustomer = getUpdatedCustomer(usernameCheckbox, emailCheckbox, passwordCheckbox, usernameText,
                        emailText, passwordText, passwordConfirmText); // get an updated customer object
                // send customer to view model for update via repository
                if(updatedCustomer != null) mViewModel.updateCustomer(userId, updatedCustomer, view, getContext());
            }
        });
    }

    // create an updated customer object
    public Customer getUpdatedCustomer(CheckBox usernameCheckbox, CheckBox emailCheckbox, CheckBox passwordCheckbox,
                                        EditText usernameText, EditText emailText, EditText passwordText, EditText passwordConfirmText ){
        String username, email, password;
        username = email = password = ""; // set empty fields as default
        if(usernameCheckbox.isChecked()) { // get updated username if valid
            username = usernameText.getText().toString();
            if(username.length() > 20) {
                Toast.makeText(getContext(), "Username must be up to 20 characters long" , Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        if(emailCheckbox.isChecked()) {  // get updated email if valid
            email = emailText.getText().toString();
            int altCount = 0;
            for (int i = 0; i < email.length(); i++) { // email must have exactly one '@'
                if (email.charAt(i) == '@') altCount++;
            }
            if (email.length() < 5 | altCount != 1 | !email.contains(".")) {
                Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        if(passwordCheckbox.isChecked()){ // get updated password if valid
            password = passwordText.getText().toString();
            if(password.length() < 8) {
                Toast.makeText(getContext(), "Password must contain at least 8 characters" , Toast.LENGTH_SHORT).show();
                return null;
            }
            if(!password.equals(passwordConfirmText.getText().toString())){
                Toast.makeText(getContext(), "Passwords do not match" , Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        if(username.isEmpty() & email.isEmpty() & password.isEmpty()) return null; // nothing to update
        return new Customer(username, email, password); // create and return updated customer
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
