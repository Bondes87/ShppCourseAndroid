package com.dbondarenko.shpp.colorcombinations.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * File: Util.java
 * The class that contains common methods necessary for the program to work.
 * Created by Dmitro Bondarenko on 18.10.2017.
 */
public class Util {

    private static final String LOG_TAG = Util.class.getSimpleName();

    // If want to do the checks, set the value to true, otherwise false.
    private static final boolean IS_DO_CHECKS = true;

    /**
     * Checks the index of an element beyond its boundaries.
     * If the element's index is out of bounds, it throws
     * an exception IndexOutOfBoundsException
     *
     * @param index            The index which is checked for going beyond the borders.
     * @param amountOfElements The amount of elements in the object.
     */
    static void checkIndexOutOfBounds(int index, int amountOfElements) {
        Log.d(LOG_TAG, "checkIndexOutOfBounds()");
        if (IS_DO_CHECKS) {
            if (index < 0 && index >= amountOfElements) {
                throw new IndexOutOfBoundsException("The element index went beyond the bounds.");
            }
        }
    }

    /**
     * Checks that the specified object reference is null.
     * If the specified object reference is null
     * then throw out the NullPointerException.
     *
     * @param objects The objects that is checked for null.
     */
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

    /**
     * Checks that the specified string is null or 0-length.
     * If the specified object reference is null then throw
     * out the NullPointerException.
     *
     * @param strings The Strings that is checked for null.
     */
    public static void checkStringToNull(String... strings) {
        Log.d(LOG_TAG, "checkStringToNull()");
        if (IS_DO_CHECKS) {
            for (String string : strings) {
                if (TextUtils.isEmpty(string)) {
                    throw new NullPointerException("The string is equal null or 0-length.");
                }
            }
        }
    }
}