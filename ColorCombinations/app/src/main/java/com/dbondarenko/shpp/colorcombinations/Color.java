package com.dbondarenko.shpp.colorcombinations;

/**
 * File: Color.java
 * The class that creates Color objects that have
 * a color value and a color name.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
class Color {
    private String nameColor;
    private int colorValue;

    Color(String nameColor, int valueColor) {
        this.nameColor = nameColor;
        this.colorValue = valueColor;
    }

    String getNameColor() {
        return nameColor;
    }

    int getValueColor() {
        return colorValue;
    }
}
