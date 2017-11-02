package com.dbondarenko.shpp.colorcombinations.models;

/**
 * File: ColorModel.java
 * The class that creates ColorModel objects that have
 * a color value and a color name.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class ColorModel {
    private String nameColor;
    private int colorValue;

    public ColorModel(String nameColor, int valueColor) {
        this.nameColor = nameColor;
        this.colorValue = valueColor;
    }

    public String getNameColor() {
        return nameColor;
    }

    public int getValueColor() {
        return colorValue;
    }
}