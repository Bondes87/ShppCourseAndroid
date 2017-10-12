package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

import java.util.Random;

/**
 * File: BottomColorRectangleFragment.java
 * Created by Dmitro Bondarenko on 09.10.2017.
 */
public class BottomColorRectangleFragment extends ColorRectangleFragment {

    private static final String LOG_TAG = "bottom_fragment";


    public int getInitRectangleColor() {
        Log.d(LOG_TAG, "getInitRectangleColor()");
        int color = FragmentsPreferences.getFragmentsPreferences().getBottomFragmentColor(getContext());
        if (color == -1) {
            Random random = new Random();
            return ColorsManager.getColorsManager()
                    .getFragmentColor(random.nextInt(3) + 4).getValueColor();
        } else {
            return color;
        }
    }


    public void saveRectangleColor(int color) {
        FragmentsPreferences.getFragmentsPreferences().saveBottomFragmentColor(getContext(), color);
    }
}