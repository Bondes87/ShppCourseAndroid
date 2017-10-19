package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * File: ColorFragment.java
 * The fragment that changes color.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class ColorFragment extends Fragment {

    private static final String LOG_TAG = "color_fragment";

    // The key for accessing the color value of a fragment
    // in the arguments of a fragment.
    private static final String KEY_CONTENT_COLOR_VALUE =
            "com.dbondarenko.shpp.colorcombinations.ContentColorValue";
    // The value of the color the background of the fragment.
    private int backgroundColorValue;
    // View to display the contents of a fragment.
    private CardView cardViewContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundColorValue = getArguments().getInt(KEY_CONTENT_COLOR_VALUE);
        setRetainInstance(true);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        cardViewContent = (CardView) inflater.inflate(R.layout.fragment_color,
                container, false);
        cardViewContent.setCardBackgroundColor(backgroundColorValue);
        return cardViewContent;
    }

    /**
     * Returns a ColorFragment class object with the specified color.
     *
     * @param colorValue The color value for the background of the fragment.
     * @return ColorFragment class object.
     */
    public static ColorFragment newInstance(int colorValue) {
        ColorFragment colorFragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CONTENT_COLOR_VALUE, colorValue);
        colorFragment.setArguments(args);
        return colorFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onColorChangeEvent(ColorChangeEvent event) {
        Utility.checkForNull(event);
        if (event.getFragmentTag().equals(getTag())) {
            setBackgroundColorValue(event.getColorValue());
        }
    }

    /**
     * Sets the background color of the fragment.
     *
     * @param newBackgroundColorValue The new color value for the background
     *                                of the fragment.
     */
    private void setBackgroundColorValue(int newBackgroundColorValue) {
        backgroundColorValue = newBackgroundColorValue;
        cardViewContent.setCardBackgroundColor(backgroundColorValue);
    }
}