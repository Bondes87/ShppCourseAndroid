package com.dbondarenko.shpp.cookislands.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.fragments.LoginFragment;

/**
 * File: MainActivity.java
 * The activity that is displayed when the program is started.
 * This activity can provide a fragment of the user's login
 * or registration.
 * Created by Dmitro Bondarenko on 01.11.2017.
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Fragment loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayoutContainer, loginFragment)
                    .commit();
        }
    }
}