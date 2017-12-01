package com.dbondarenko.shpp.personalnotes.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.dbondarenko.shpp.personalnotes.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * File: Util.java
 * Created by Dmitro Bondarenko on 21.11.2017.
 */
public class Util {

    private static final String LOG_TAG = Util.class.getSimpleName();

    private static final boolean IS_DO_CHECKS = true;

    public static String getStringDatetime(long datetime) {
        Log.d(LOG_TAG, "getStringDatetime()");
        DateFormat dateFormat = new SimpleDateFormat(
                Constants.PATTERN_DATETIME, Locale.US);
        return dateFormat.format(new Date(datetime));
    }

    public static String getStringDate(long datetime) {
        Log.d(LOG_TAG, "getStringDate()");
        DateFormat dateFormat = new SimpleDateFormat(
                Constants.PATTERN_DATE, Locale.US);
        return dateFormat.format(new Date(datetime));
    }

    public static String getStringTime(long datetime) {
        Log.d(LOG_TAG, "getStringTime()");
        DateFormat dateFormat = new SimpleDateFormat(
                Constants.PATTERN_TIME, Locale.US);
        return dateFormat.format(new Date(datetime));
    }

    public static void hideSoftKeyboard(Context context, View view) {
        Log.d(LOG_TAG, "hideSoftKeyboard()");
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getApplicationContext()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Context context) {
        Log.d(LOG_TAG, "showSoftKeyboard()");
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getApplicationContext()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void reportAnError(View view, String errorMessage) {
        Log.d(LOG_TAG, "reportAnError()");
        Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public static boolean isInternetConnectionAvailable(Context context) {
        Log.d(LOG_TAG, "isInternetConnectionAvailable()");
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void enableBackStackButton(ActionBar actionBar,
                                             boolean showBackStackButton) {
        Log.d(LOG_TAG, "enableBackStackButton()");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showBackStackButton);
        }
    }

    public static void setTitleForActionBar(ActionBar actionBar,
                                            String titleForActionBar) {
        Log.d(LOG_TAG, "setTitleForActionBar()");
        if (actionBar != null) {
            actionBar.setTitle(titleForActionBar);
        }
    }

    public static void checkForNull(Object... objects) {
        Log.d(LOG_TAG, "checkForNull()");
        if (IS_DO_CHECKS) {
            for (Object object : objects) {
                if (object == null) {
                    throw new NullPointerException("The object is equal null.");
                }
            }
        }
    }
}