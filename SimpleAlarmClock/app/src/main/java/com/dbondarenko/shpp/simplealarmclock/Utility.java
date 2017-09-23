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
        Log.d(LOG_TAG, "getTime()");
        checkForNegativeNumber(datetime);
        DateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        return dateFormat.format(new Date(datetime));
    }

    /**
     * Checks that the specified object reference is null. If the specified object reference
     * is null then throw out the NullPointerException.
     *
     * @param object The object that is checked for null.
     */
    static void checkForNull(Object object) {
        Log.d(LOG_TAG, "checkForNull()");
        if (object == null) {
            throw new NullPointerException("The object is equal null.");
        }
    }

    /**
     * Checks that the specified string reference is null or 0-lenght. If the specified string
     * reference is null or 0-lenght then throw out the NullPointerException.
     *
     * @param string The String that is checked for null or 0-length.
     */
    static void checkForEmptyString(String string) {
        Log.d(LOG_TAG, "checkForEmptyString()");
        if (string == null || string.length() == 0)
            throw new NullPointerException("The string is equal null or empty.");
    }

    /**
     * Checks the numbers for negative. If the numbers is negative then throws
     * IllegalArgumentException.
     *
     * @param numbers The numbers to check for negativity.
     */
    static void checkForNegativeNumber(long... numbers) {
        Log.d(LOG_TAG, "checkForNegativeNumber()");
        for (long number : numbers) {
            if (number < 0) {
                throw new IllegalArgumentException("The number is negative.");
            }
        }
    }
}