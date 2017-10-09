package com.dbondarenko.shpp.colorcombinations;

import android.graphics.Color;
import android.util.Log;

/**
 * File: TopRightColorRectangleFragment.java
 * Created by Dmitro Bondarenko on 09.10.2017.
 */
public class TopRightColorRectangleFragment extends ColorRectangleFragment {

    private static final String LOG_TAG = "right_fragment";

    @Override
    public int getInitRectangleColor() {
        Log.d(LOG_TAG, "getInitRectangleColor()");
        return Color.rgb(255, 0, 0);
    }
}
