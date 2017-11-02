package com.dbondarenko.shpp.cookislands.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.Constants;

/**
 * File: SharedPreferencesManager.java
 * The class to save and retrieve information from SharedPreferences.
 * Created by Dmitro Bondarenko on 30.10.2017.
 */
public class SharedPreferencesManager {

    private static final String LOG_TAG = SharedPreferencesManager.class.getSimpleName();

    private static SharedPreferencesManager sharedPreferencesManager;

    private SharedPreferencesManager() {
    }

    /**
     * Get the object SharedPreferencesManager. If the object SharedPreferencesManager
     * is null, then create the object, otherwise return the existing object.
     *
     * @return object AlarmPreference.
     */
    public static SharedPreferencesManager getSharedPreferencesManager() {
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager();
        }
        return sharedPreferencesManager;
    }

    /**
     * Save information about the user's login to SharedPreferences.
     *
     * @param context      The Context of the application package implementing this class.
     * @param userIslandId The information about islandId of user.
     */
    public void saveInformationAboutLogin(Context context, int userIslandId) {
        Log.d(LOG_TAG, "saveInformationAboutLogin()");
        Util.checkForNull(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.KEY_USER_LOGGED_IN, true)
                .putInt(Constants.KEY_USER_ISLAND_ID, userIslandId)
                .apply();
    }

    /**
     * Get information about the user's login from SharedPreferences.
     *
     * @param context The Context of the application package implementing this class.
     * @return true if the user is logged in, otherwise false.
     */
    public boolean getInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "getInformationAboutLogin()");
        Util.checkForNull(context);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(Constants.KEY_USER_LOGGED_IN, false);
    }

    /**
     * Get information about the user's islandId from SharedPreferences.
     *
     * @param context The Context of the application package implementing this class.
     * @return the user's islandId if the user is logged in, otherwise 0.
     */
    public int getUserIslandId(Context context) {
        Log.d(LOG_TAG, "getUserIslandId()");
        Util.checkForNull(context);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(Constants.KEY_USER_ISLAND_ID, 0);
    }

    /**
     * Remove information about the user's login from SharedPreferences.
     *
     * @param context The Context of the application package implementing this class.
     */
    public void removeInformationAboutLogin(Context context) {
        Log.d(LOG_TAG, "removeInformationAboutLogin()");
        Util.checkForNull(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(Constants.KEY_USER_LOGGED_IN)
                .remove(Constants.KEY_USER_ISLAND_ID)
                .apply();
    }
}