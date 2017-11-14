package com.dbondarenko.shpp.personalnotes.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;

/**
 * File: SharedPreferencesManager.java
 * Created by Dmitro Bondarenko on 13.11.2017.
 */
public class SharedPreferencesManager {

    private static final String LOG_TAG = SharedPreferencesManager.class.getSimpleName();

    private static SharedPreferencesManager sharedPreferencesManager;

    private SharedPreferencesManager() {
    }

    public static SharedPreferencesManager getSharedPreferencesManager() {
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager();
        }
        return sharedPreferencesManager;
    }

    public void saveInformationAboutDatabase(Context context, boolean isUseFirebase) {
        Log.d(LOG_TAG, "saveInformationAboutDatabase()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.KEY_USE_DATABASE, isUseFirebase)
                .apply();
    }

    public boolean isUseFirebase(Context context) {
        Log.d(LOG_TAG, "isUseFirebase()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(Constants.KEY_USE_DATABASE, false);
    }
}
