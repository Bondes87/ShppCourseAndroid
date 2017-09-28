package com.dbondarenko.shpp.animationviews;

import android.util.Log;

import java.util.Random;

/**
 * File: Utility.java
 * Created by Dmitro Bondarenko on 28.09.2017.
 */
public class Utility {
    private static final String LOG_TAG = "utility";

    public static int getRandomNumber(int number) {
        Log.d(LOG_TAG, "getRandomNumber()");
        Random random = new Random();
        return random.nextInt(number);
    }
}
