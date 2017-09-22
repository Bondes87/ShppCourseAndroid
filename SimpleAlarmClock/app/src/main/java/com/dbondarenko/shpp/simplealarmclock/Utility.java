package com.dbondarenko.shpp.simplealarmclock;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * File: Utility.java
 * The class that contains common methods necessary for the program to work.
 * Created by Dmitro Bondarenko on 06.09.2017.
 */
class Utility {

    private static final String LOG_TAG = "utility";

    /**
     * Get a string representation of the date from a given number.
     * The incoming number is the number of milliseconds.
     *
     * @param datetime The number of milliseconds.
     * @return The date string representation.
     */
    static String getTime(long datetime) {
        if (datetime < 0) {
            Log.d(LOG_TAG, "getTime(): the time of the alarm was set incorrectly");
        } else {
            DateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
            return dateFormat.format(new Date(datetime));
        }
        return null;
    }

    static boolean checkForNull(Object o) {
        if (o == null) {
            throw new NullPointerException("The object is equal null.");
        }
        return false;
    }

    static boolean isNotEmptyString(String string) {
        if (string == null || string.length() == 0)
            throw new NullPointerException("The string is equal null or empty.");
        return false;
    }

    static boolean isNotNegativeNumber(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("The number is negative.");
        }
        return false;
    }
}