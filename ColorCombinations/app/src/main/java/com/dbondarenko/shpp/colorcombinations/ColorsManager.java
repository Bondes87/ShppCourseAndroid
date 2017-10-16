package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * File: ColorsManager.java
 * The class in which the colors are managed.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
class ColorsManager {

    private static final String LOG_TAG = "main_activity";

    // The colors values.
    private static final int RED = 0xFFFF0000;
    private static final int ORANGE = 0xFFFFA500;
    private static final int YELLOW = 0xFFFFFF00;
    private static final int GREEN = 0xFF00FF00;
    private static final int CYAN = 0xFF00FFFF;
    private static final int BLUE = 0xFF0000FF;
    private static final int PURPLE = 0xFF800080;

    private static ColorsManager colorsManager;
    // The list that contains the available colors.
    private ArrayList<Color> arrayListAvailableColors;
    // The map that contains the colors used.
    private HashMap<String, Color> hashMapUsedColors;

    private ColorsManager() {
        arrayListAvailableColors = new ArrayList<>();
        hashMapUsedColors = new HashMap<>();
        initColors();
    }

    /**
     * Returns the ColorManager object. If an object is not created, it creates it.
     *
     * @return ColorManager object.
     */
    static ColorsManager getColorsManager() {
        Log.d(LOG_TAG, "getColorsManager()");
        if (colorsManager == null) {
            colorsManager = new ColorsManager();
        }
        return colorsManager;
    }

    /**
     * Initialization colors.
     */
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

    /**
     * Finds for a key by value in the map.
     *
     * @param color The value for key search.
     * @return the found key or an empty string.
     */
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

    /**
     * Returns the list of available colors.
     *
     * @return the list of available colors.
     */
    ArrayList<Color> getAvailableColors() {
        Log.d(LOG_TAG, "getAvailableColors()");
        return arrayListAvailableColors;
    }

    /**
     * Returns the available color.
     *
     * @param colorIndex  The color index in the list.
     * @param fragmentTag The fragment tag for which want to get the color.
     * @return the available color.
     */
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

    /**
     * Returns the random available color.
     *
     * @param fragmentTag The fragment tag for which want to get the color.
     * @return the random available color.
     */
    Color getRandomAvailableColor(String fragmentTag) {
        Log.d(LOG_TAG, "getRandomAvailableColor()");
        int randomIndex = new Random().nextInt(arrayListAvailableColors.size());
        return getAvailableColor(randomIndex, fragmentTag);
    }

    /**
     * Returns the used color.
     *
     * @param fragmentTag The fragment tag for which want to get the color.
     * @return the used color.
     */
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

    /**
     * Sets the color available, adding it to the list of available colors.
     *
     * @param fragmentTag The fragment tag the color of which
     *                    is set to make available.
     */
    void setAvailableColor(String fragmentTag) {
        Log.d(LOG_TAG, "setAvailableColor()");
        Color usedColor = hashMapUsedColors.get(fragmentTag);
        if (usedColor != null && !arrayListAvailableColors.contains(usedColor)) {
            arrayListAvailableColors.add(0, usedColor);
        }
    }
}