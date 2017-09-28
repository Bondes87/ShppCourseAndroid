package com.dbondarenko.shpp.animationviews;

import android.util.Log;

import java.util.Random;

/**
 * File: Utility.java
 * The class that contains common methods necessary for the program to work.
 * Created by Dmitro Bondarenko on 28.09.2017.
 */
class Utility {
    private static final String LOG_TAG = "utility";

    /**
     * Checks that the specified object reference is null. If the specified object reference
     * is null then throw out the NullPointerException.
     *
     * @param objects The objects that is checked for null.
     */
    static void checkForNull(Object... objects) {
        Log.d(LOG_TAG, "checkForNull()");
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException("The object is equal null.");
            }
        }
    }

    /**
     * Checks the numbers for not positive. If the numbers is not positive then throws
     * IllegalArgumentException.
     *
     * @param number The numbers to check for not positive.
     */
    static void checkForNotPositiveNumber(long number) {
        Log.d(LOG_TAG, "checkForNegativeNumber()");
        if (number <= 0) {
            throw new IllegalArgumentException("The number is not positive.");
        }
    }

    /**
     * Returns a random number no greater than the number specified in the parameters.
     *
     * @param number The random number.
     */
    static int getRandomNumber(int number) {
        Log.d(LOG_TAG, "getRandomNumber()");
        checkForNotPositiveNumber(number);
        Random random = new Random();
        return random.nextInt(number);
    }
}
