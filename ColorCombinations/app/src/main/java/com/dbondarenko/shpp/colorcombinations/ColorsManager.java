package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * File: ColorsManager.java
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
class ColorsManager {

    private static final String LOG_TAG = "main_activity";
    private static final int RED = 0xFFFF0000;
    private static final int ORANGE = 0xFFFFA500;
    private static final int YELLOW = 0xFFFFFF00;
    private static final int GREEN = 0xFF00FF00;
    private static final int CYAN = 0xFF00FFFF;
    private static final int BLUE = 0xFF0000FF;
    private static final int PURPLE = 0xFF800080;

    private static ColorsManager colorsManager;
    private ArrayList<Color> arrayListAvailableColors;
    private ArrayList<Color> arrayListUsedColors;

    private ColorsManager() {
        arrayListAvailableColors = new ArrayList<>();
        arrayListUsedColors = new ArrayList<>();
        initColors();
    }

    static ColorsManager getColorsManager() {
        if (colorsManager == null) {
            colorsManager = new ColorsManager();
        }
        return colorsManager;
    }

    private void initColors() {
        arrayListAvailableColors.add(new Color("red", RED));
        arrayListAvailableColors.add(new Color("orange", ORANGE));
        arrayListAvailableColors.add(new Color("yellow", YELLOW));
        arrayListAvailableColors.add(new Color("green", GREEN));
        arrayListAvailableColors.add(new Color("cyan", CYAN));
        arrayListAvailableColors.add(new Color("blue", BLUE));
        arrayListAvailableColors.add(new Color("purple", PURPLE));
    }

    ArrayList<Color> getAvailableColors() {
        Log.d(LOG_TAG, "getAvailableColors()");
        return arrayListAvailableColors;
    }

    Color getAvailableColor(int colorIndex) {
        Log.d(LOG_TAG, "getAvailableColor()");
        Color color = arrayListAvailableColors.remove(colorIndex);
        arrayListUsedColors.add(color);
        return color;
    }

    Color getRandomAvailableColor() {
        Log.d(LOG_TAG, "getAvailableColor()");
        int randomIndex = new Random().nextInt(arrayListAvailableColors.size());
        return getAvailableColor(randomIndex);
    }

    void setAvailableColor(int colorValue) {
        Log.d(LOG_TAG, "setAvailableColor()");
        int indexColor = 0;
        for (int i = 0; i < arrayListUsedColors.size(); i++) {
            if (arrayListUsedColors.get(i).getValueColor() == colorValue) {
                indexColor = i;
                break;
            }
        }
        arrayListAvailableColors.add(0, arrayListUsedColors.remove(indexColor));
    }
}