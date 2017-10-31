package com.dbondarenko.shpp.cookislands.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.Constants;

/**
 * File: SharedPreferencesManager.java
 * Created by Dmitro Bondarenko on 30.10.2017.
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

    public void saveInformationAboutLogin(Context context, int userIslandId) {
        Log.d(LOG_TAG, "saveInformationAboutLogin()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.KEY_USER_LOGGED_IN, true)
                .putInt(Constants.KEY_USER_ISLAND_ID, userIslandId)
                .apply();
    }

    public boolean getInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "getInformationAboutLogin()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(Constants.KEY_USER_LOGGED_IN, false);
    }

    public int getUserIslandId(Context context) {
        Log.d(LOG_TAG, "getUserIslandId()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(Constants.KEY_USER_ISLAND_ID, 0);
    }

    public void removeInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "removeInformationAboutLogin()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(Constants.KEY_USER_LOGGED_IN)
                .remove(Constants.KEY_USER_ISLAND_ID)
                .apply();
    }
}