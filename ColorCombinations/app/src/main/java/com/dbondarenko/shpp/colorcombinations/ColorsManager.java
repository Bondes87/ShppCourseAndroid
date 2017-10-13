package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private HashMap<String, Color> hashMapUsedColors;

    private ColorsManager() {
        arrayListAvailableColors = new ArrayList<>();
        hashMapUsedColors = new HashMap<>();
        initColors();
    }

    static ColorsManager getColorsManager() {
        Log.d(LOG_TAG, "getColorsManager()");
        if (colorsManager == null) {
            colorsManager = new ColorsManager();
        }
        return colorsManager;
    }

    private void initColors() {
        Log.d(LOG_TAG, "initColors()");
        arrayListAvailableColors.add(new Color("red", RED));
        arrayListAvailableColors.add(new Color("orange", ORANGE));
        arrayListAvailableColors.add(new Color("yellow", YELLOW));
        arrayListAvailableColors.add(new Color("green", GREEN));
        arrayListAvailableColors.add(new Color("cyan", CYAN));
        arrayListAvailableColors.add(new Color("blue", BLUE));
        arrayListAvailableColors.add(new Color("purple", PURPLE));
    }

    private String findKeyByValue(Color color) {
        Log.d(LOG_TAG, "findKeyByValue()");
        String key = "";
        for (Map.Entry<String, Color> entry : hashMapUsedColors.entrySet())
            if (color.equals(entry.getValue())) {
                key = entry.getKey();
                break;
            }
        return key;
    }

    ArrayList<Color> getAvailableColors() {
        Log.d(LOG_TAG, "getAvailableColors()");
        return arrayListAvailableColors;
    }

    Color getAvailableColor(int colorIndex, String fragmentTag) {
        Log.d(LOG_TAG, "getAvailableColor()");
        Color availableColor = arrayListAvailableColors.remove(colorIndex);
        if (hashMapUsedColors.containsValue(availableColor)) {
            hashMapUsedColors.put(findKeyByValue(availableColor), null);
        }
        setAvailableColor(fragmentTag);
        hashMapUsedColors.put(fragmentTag, availableColor);
        return availableColor;
    }

    Color getRandomAvailableColor(String fragmentTag) {
        Log.d(LOG_TAG, "getRandomAvailableColor()");
        int randomIndex = new Random().nextInt(arrayListAvailableColors.size());
        return getAvailableColor(randomIndex, fragmentTag);
    }

    Color getUsedColor(String fragmentTag) {
        Log.d(LOG_TAG, "getUsedColor()");
        Color usedColor = hashMapUsedColors.get(fragmentTag);
        if (usedColor != null) {
            arrayListAvailableColors.remove(usedColor);
            return usedColor;
        } else {
            return getRandomAvailableColor(fragmentTag);
        }
    }

    void setAvailableColor(String fragmentTag) {
        Log.d(LOG_TAG, "setAvailableColor()");
        Color usedColor = hashMapUsedColors.get(fragmentTag);
        if (usedColor != null && !arrayListAvailableColors.contains(usedColor)) {
            arrayListAvailableColors.add(0, usedColor);
        }
    }
}