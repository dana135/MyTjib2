package com.androidapp.mytjib.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.network.Repository;

/**
 * Activity for admin part of the application
 */

public class AdminActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // create activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        activity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // create and inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        switch (item.getItemId()) {
            case R.id.menu_logout_admin: // log out when pressed
                Repository.getInstance().resetRepository();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            case R.id.orders_admin: // each fragment takes care of this option
                return false;
        }
        return true;
    }

}
