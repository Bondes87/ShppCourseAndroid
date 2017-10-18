package com.dbondarenko.shpp.colorcombinations;

/**
 * File: ColorChangeEvent.java
 * The class that creates the event to change the color of the fragment.
 * Created by Dmitro Bondarenko on 18.10.2017.
 */
class ColorChangeEvent {
    private int colorValue;
    private String fragmentTag;

    ColorChangeEvent(int colorValue, String fragmentTag) {
        this.colorValue = colorValue;
        this.fragmentTag = fragmentTag;
    }

    int getColorValue() {
        return colorValue;
    }

    String getFragmentTag() {
        return fragmentTag;
    }
}