package com.dbondarenko.shpp.personalnotes;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * File: PersonalNotesApplication.java
 * Created by Dmitro Bondarenko on 02.12.2017.
 */
public class PersonalNotesApplication extends Application {

    private static final String LOG_TAG = PersonalNotesApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}