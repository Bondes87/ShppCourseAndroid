package com.dbondarenko.shpp.personalnotes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.models.User;
import com.dbondarenko.shpp.personalnotes.models.UserFirebaseModel;
import com.dbondarenko.shpp.personalnotes.models.UserSQLiteModel;

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

    public void saveInformationAboutUser(Context context, String login, String password) {
        Log.d(LOG_TAG, "saveInformationAboutUser()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.KEY_USER_LOGIN, login)
                .putString(Constants.KEY_USER_PASSWORD, password)
                .apply();
    }

    public User getUser(Context context) {
        Log.d(LOG_TAG, "getUser()");
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String login = sharedPreferences.getString(Constants.KEY_USER_LOGIN, null);
        String password = sharedPreferences.getString(Constants.KEY_USER_PASSWORD, null);
        if (login != null && password != null) {
            if (isUseFirebase(context)) {
                return new UserFirebaseModel(login, password);
            } else {
                return new UserSQLiteModel(login, password);
            }
        } else {
            return null;
        }
    }

    public void deleteInformationAboutUser(Context context) {
        Log.d(LOG_TAG, "deleteInformationAboutUser()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(Constants.KEY_USER_LOGIN)
                .remove(Constants.KEY_USER_PASSWORD)
                .apply();
    }
}
