package com.androidapp.mytjib.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.network.Repository;

/**
 * Activity for customer part of the application
 */

public class UserActivity extends AppCompatActivity {

    private int userId;
    private Activity activity;
    RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // create activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        view = findViewById(R.id.user_activity_id);
        activity = this;
        userId = getIntent().getExtras().getInt("userId"); // the user who logged in
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // create and inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menu logic
        switch (item.getItemId()) {
            case R.id.menu_home: // back to homepage (event list)
                Intent intentHome = new Intent(activity, UserActivity.class);
                intentHome.putExtra("userId", userId);
                startActivity(intentHome);
                activity.finish();
                break;
            case R.id.menu_logout: // log out when pressed
                Repository.getInstance().resetRepository();
                Intent intentLogOut = new Intent(activity, LoginActivity.class);
                startActivity(intentLogOut);
                activity.finish();
                break;
            // each fragment takes care of these options
            case R.id.menu_myaccount:
            case R.id.menu_live:
            case R.id.menu_online:
            case R.id.menu_fan:
                return false;
        }

        return true;
    }

    public int getUserId(){ return userId; } // getter

}
