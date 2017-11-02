package com.dbondarenko.shpp.colorcombinations.models;

import android.util.Log;

/**
 * File: ColorChangeEventModel.java
 * The class that creates the event to change the color of the fragment.
 * Created by Dmitro Bondarenko on 18.10.2017.
 */
public class ColorChangeEventModel {

    private static final String LOG_TAG = ColorChangeEventModel.class.getSimpleName();

    private int colorValue;
    private String fragmentTag;

    public ColorChangeEventModel(int colorValue, String fragmentTag) {
        this.colorValue = colorValue;
        this.fragmentTag = fragmentTag;
    }

    public int getColorValue() {
        Log.d(LOG_TAG, "getColorValue()");
        return colorValue;
    }

    public String getFragmentTag() {
        Log.d(LOG_TAG, "getFragmentTag()");
        return fragmentTag;
    }
}