package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

import java.util.Random;

/**
 * File: TopRightColorRectangleFragment.java
 * Created by Dmitro Bondarenko on 09.10.2017.
 */
public class TopRightColorRectangleFragment extends ColorRectangleFragment {

    private static final String LOG_TAG = "right_fragment";


    public int getInitRectangleColor() {
        Log.d(LOG_TAG, "getInitRectangleColor()");
        int color = FragmentsPreferences.getFragmentsPreferences().getTopRightFragmentColor(getContext());
        if (color == -1) {
            Random random = new Random();
            return ColorsForFragments.getColorsForFragments()
                    .getFragmentColor(random.nextInt(2) + 2).getValueColor();
        } else {
            return color;
        }
    }


    public void saveRectangleColor(int color) {
        FragmentsPreferences.getFragmentsPreferences().saveTopRightFragmentColor(getContext(), color);
    }
}