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

    private static final String KEY_CONTENT_COLOR_VALUE =
            "com.dbondarenko.shpp.colorcombinations.ContentColorValue";

    private int contentColorValue;

    private CardView cardViewContent;

    public static ColorFragment newInstance(int colorValue) {
        ColorFragment colorFragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CONTENT_COLOR_VALUE, colorValue);
        colorFragment.setArguments(args);
        return colorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentColorValue = getArguments().getInt(KEY_CONTENT_COLOR_VALUE);
        setRetainInstance(true);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        cardViewContent = (CardView) inflater.inflate(R.layout.fragment_color, container, false);
        cardViewContent.setCardBackgroundColor(contentColorValue);
        return cardViewContent;
    }

    public void setContentColorValue(int newContentColorValue) {
        contentColorValue = newContentColorValue;
        cardViewContent.setCardBackgroundColor(contentColorValue);
    }
}