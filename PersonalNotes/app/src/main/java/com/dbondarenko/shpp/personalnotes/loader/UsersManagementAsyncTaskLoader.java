package com.dbondarenko.shpp.personalnotes.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.dbondarenko.shpp.personalnotes.Constants;
import com.dbondarenko.shpp.personalnotes.database.DatabaseManager;
import com.dbondarenko.shpp.personalnotes.database.sqlitebase.SQLiteManager;
import com.dbondarenko.shpp.personalnotes.models.UserModel;

/**
 * File: UsersManagementAsyncTaskLoader.java
 * Created by Dmitro Bondarenko on 11.11.2017.
 */
public class UsersManagementAsyncTaskLoader extends AsyncTaskLoader<Boolean> {

    private static final String LOG_TAG = UsersManagementAsyncTaskLoader.class.getSimpleName();

    private String command;
    private UserModel user;
    private DatabaseManager databaseManager;

    public UsersManagementAsyncTaskLoader(Context context, UserModel user, String command) {
        super(context);
        this.user = user;
        this.command = command;
        databaseManager = new SQLiteManager(context);
        onContentChanged();
    }

    @Override
    public void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading");
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public Boolean loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground");
        switch (command) {
            case Constants.COMMAND_ADD_USER:
                return databaseManager.addUser(user);
            case Constants.COMMAND_IS_SER_EXIST:
                return databaseManager.isUserExists(user);
            default:
                return null;
        }
    }

    @Override
    public void onStopLoading() {
        Log.d(LOG_TAG, "onStopLoading");
        cancelLoad();
    }
}