package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class ColorRectangleFragment extends Fragment {

    private static final String LOG_TAG = "abstract_fragment";

    private int rectangleColor;

    private View rectangleView;

    public abstract int getInitRectangleColor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        rectangleView = new View(getActivity());
        rectangleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if (rectangleColor == 0) {
            rectangleView.setBackgroundColor(getInitRectangleColor());
        } else {
            rectangleView.setBackgroundColor(rectangleColor);
        }
        return rectangleView;
    }

    public int getRectangleColor() {
        Log.d(LOG_TAG, "getRectangleColor()");
        return rectangleColor;
    }

    public void setRectangleColor(int rectangleColor) {
        Log.d(LOG_TAG, "setRectangleColor()");
        this.rectangleColor = rectangleColor;
        this.rectangleView.setBackgroundColor(rectangleColor);
    }

    public void setRectangleVisibility(int visibility) {
        rectangleView.setVisibility(visibility);
    }
}