package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

/**
 * File: ColorChangeEvent.java
 * The class that creates the event to change the color of the fragment.
 * Created by Dmitro Bondarenko on 18.10.2017.
 */
class ColorChangeEvent {

    private static final String LOG_TAG = "color_change_event";

    private int colorValue;
    private String fragmentTag;

    ColorChangeEvent(int colorValue, String fragmentTag) {
        this.colorValue = colorValue;
        this.fragmentTag = fragmentTag;
    }

    int getColorValue() {
        Log.d(LOG_TAG, "getColorValue()");
        return colorValue;
    }

    String getFragmentTag() {
        Log.d(LOG_TAG, "getFragmentTag()");
        return fragmentTag;
    }
}