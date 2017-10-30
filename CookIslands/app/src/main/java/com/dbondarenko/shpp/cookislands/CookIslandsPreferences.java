package com.dbondarenko.shpp.cookislands;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * File: CookIslandsPreferences.java
 * Created by Dmitro Bondarenko on 30.10.2017.
 */
public class CookIslandsPreferences {

    private static final String LOG_TAG = CookIslandsPreferences.class.getSimpleName();

    private static CookIslandsPreferences cookIslandsPreferences;

    private CookIslandsPreferences() {
    }

    public static CookIslandsPreferences getCookIslandsPreferences() {
        if (cookIslandsPreferences == null) {
            cookIslandsPreferences = new CookIslandsPreferences();
        }
        return cookIslandsPreferences;
    }

    public void saveInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "saveInformationAboutLogin()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.KEY_USER_LOGGED_IN, true)
                .apply();
    }

    public boolean getInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "getInformationAboutLogin()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(Constants.KEY_USER_LOGGED_IN, false);
    }

    public void removeInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "removeInformationAboutLogin()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(Constants.KEY_USER_LOGGED_IN)
                .apply();
    }
}