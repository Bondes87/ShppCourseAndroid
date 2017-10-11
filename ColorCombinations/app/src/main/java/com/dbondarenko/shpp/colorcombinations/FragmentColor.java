package com.dbondarenko.shpp.colorcombinations;

/**
 * File: FragmentColor.java
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class FragmentColor {
    private String nameColor;
    private int valueColor;

    public FragmentColor(String nameColor, int valueColor) {
        this.nameColor = nameColor;
        this.valueColor = valueColor;
    }

    public String getNameColor() {
        return nameColor;
    }

    public int getValueColor() {
        return valueColor;
    }
}
