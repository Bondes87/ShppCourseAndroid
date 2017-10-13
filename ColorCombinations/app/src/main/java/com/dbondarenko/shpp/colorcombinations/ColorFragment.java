package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class ColorFragment extends Fragment {

    private static final String LOG_TAG = "color_fragment";

    private static final String KEY_COLOR_VALUE =
            "com.dbondarenko.shpp.colorcombinations.Color";

    private int colorValue;

    private CardView cardView;

    public static ColorFragment newInstance(int colorValue) {
        ColorFragment colorFragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_COLOR_VALUE, colorValue);
        colorFragment.setArguments(args);
        return colorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorValue = getArguments().getInt(KEY_COLOR_VALUE);
        setRetainInstance(true);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        cardView = new CardView(getActivity());
        cardView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        cardView.setBackgroundColor(colorValue);
        return cardView;
    }

    public void setColorValue(int newColorValue) {
        colorValue = newColorValue;
        cardView.setBackgroundColor(colorValue);
    }
}