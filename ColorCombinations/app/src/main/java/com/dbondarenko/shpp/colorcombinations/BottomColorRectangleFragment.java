package com.dbondarenko.shpp.colorcombinations;

import android.graphics.Color;
import android.util.Log;

/**
 * File: BottomColorRectangleFragment.java
 * Created by Dmitro Bondarenko on 09.10.2017.
 */
public class BottomColorRectangleFragment extends ColorRectangleFragment {

    private static final String LOG_TAG = "bottom_fragment";

    @Override
    public int getInitRectangleColor() {
        Log.d(LOG_TAG, "getInitRectangleColor()");
        return Color.rgb(0, 0, 255);
    }
}
