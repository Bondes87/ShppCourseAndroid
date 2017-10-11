package com.dbondarenko.shpp.colorcombinations;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * File: FragmentsPreferences.java
 * Created by Dmitro Bondarenko on 10.10.2017.
 */
public class FragmentsPreferences {
    private static final String LOG_TAG = "alarm_preference";

    // The constant that specifies the name of the setting to save.
    private static final String TOP_LEFT_FRAGMENT_COLOR = "TopLeftFragmentColor";
    private static final String TOP_RIGHT_FRAGMENT_COLOR = "TopRightFragmentColor";
    private static final String BOTTOM_FRAGMENT_COLOR = "BottomFragmentColor";
    private static final String TOP_LEFT_FRAGMENT_VISIBILITY = "TopLeftFragmentVisibility";
    private static final String TOP_RIGHT_FRAGMENT_VISIBILITY = "TopRightFragmentVisibility";
    private static final String BOTTOM_FRAGMENT_VISIBILITY = "BottomFragmentVisibility";

    private static FragmentsPreferences fragmentsPreferences;

    private FragmentsPreferences() {
    }

    static FragmentsPreferences getFragmentsPreferences() {
        if (fragmentsPreferences == null) {
            fragmentsPreferences = new FragmentsPreferences();
        }
        return fragmentsPreferences;
    }

    private void saveFragmentColor(Context context, String keyFragmentColor, int fragmentColor) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(keyFragmentColor, fragmentColor)
                .apply();
    }

    void saveFragmentsVisibilitySettings(Context context, int firstFragmentVisibility,
                                         int secondFragmentVisibility, int thirdFragmentVisibility) {
        Log.d(LOG_TAG, "saveFragmentsVisibilitySettings()");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(TOP_LEFT_FRAGMENT_VISIBILITY, firstFragmentVisibility)
                .putInt(TOP_RIGHT_FRAGMENT_VISIBILITY, secondFragmentVisibility)
                .putInt(BOTTOM_FRAGMENT_VISIBILITY, thirdFragmentVisibility)
                .apply();
    }

    int[] getFragmentsVisibilitySettings(Context context) {
        Log.d(LOG_TAG, "getFragmentsVisibilitySettings()");
        int firstFragmentVisibility = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_LEFT_FRAGMENT_VISIBILITY, -1);
        int secondFragmentVisibility = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_LEFT_FRAGMENT_VISIBILITY, -1);
        int thirdFragmentVisibility = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_LEFT_FRAGMENT_VISIBILITY, -1);
        return new int[]{firstFragmentVisibility, secondFragmentVisibility, thirdFragmentVisibility};
    }

    void saveTopLeftFragmentColor(Context context, int fragmentColor) {
        Log.d(LOG_TAG, "saveTopLeftFragmentColor()");
        saveFragmentColor(context, TOP_LEFT_FRAGMENT_COLOR, fragmentColor);
    }

    void saveTopRightFragmentColor(Context context, int fragmentColor) {
        Log.d(LOG_TAG, "saveTopLeftFragmentColor()");
        saveFragmentColor(context, TOP_RIGHT_FRAGMENT_COLOR, fragmentColor);
    }

    void saveBottomFragmentColor(Context context, int fragmentColor) {
        Log.d(LOG_TAG, "saveTopLeftFragmentColor()");
        saveFragmentColor(context, BOTTOM_FRAGMENT_COLOR, fragmentColor);
    }

    int getTopLeftFragmentColor(Context context) {
        Log.d(LOG_TAG, "getFragmentsColors()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_LEFT_FRAGMENT_COLOR, -1);
    }

    int getTopRightFragmentColor(Context context) {
        Log.d(LOG_TAG, "getFragmentsColors()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_RIGHT_FRAGMENT_COLOR, -1);
    }

    int getBottomFragmentColor(Context context) {
        Log.d(LOG_TAG, "getFragmentsColors()");
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(BOTTOM_FRAGMENT_COLOR, -1);
    }

    int[] getFragmentsColorsSettings(Context context) {
        Log.d(LOG_TAG, "getFragmentsColorsSettings()");
        int firstFragmentColor = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_LEFT_FRAGMENT_COLOR, -1);
        int secondFragmentColor = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(TOP_RIGHT_FRAGMENT_COLOR, -1);
        int thirdFragmentColor = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(BOTTOM_FRAGMENT_COLOR, -1);
        return new int[]{firstFragmentColor, secondFragmentColor, thirdFragmentColor};
    }
}