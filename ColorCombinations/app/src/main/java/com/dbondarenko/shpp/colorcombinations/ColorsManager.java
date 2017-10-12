package com.dbondarenko.shpp.colorcombinations;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * File: ColorsManager.java
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
class ColorsManager {

    private static final String LOG_TAG = "main_activity";

    private static ColorsManager colorsManager;
    private ArrayList<Color> arrayListAvailableColors;
    private HashMap<String, Color> hashMapUsedColors;

    private ColorsManager() {
        arrayListAvailableColors = new ArrayList<>();
        hashMapUsedColors = new HashMap<>();
        initColors();
    }

    static ColorsManager getColorsManager() {
        if (colorsManager == null) {
            colorsManager = new ColorsManager();
        }
        return colorsManager;
    }


    public ArrayList<Color> getAvailableColors() {
        Log.d(LOG_TAG, "getAvailableColors()");
        return arrayListAvailableColors;
    }

    /*public Color getAvailableColor() {
        Log.d(LOG_TAG, "getAvailableColor()");
        int randomIndex = new Random().nextInt(arrayListAvailableColors.size());
        Color color = arrayListAvailableColors.remove(randomIndex);

        return ;
    }

    public void setAvailableColor(Color color) {
        Log.d(LOG_TAG, "setAvailableColor()");
        arrayListAvailableColors.add(color);
    }*/

    public Color getAvailableColor(String tegOfFragment) {
        Log.d(LOG_TAG, "getAvailableColor()");
        if (TextUtils.isEmpty(tegOfFragment)) {
            Log.d(LOG_TAG, "Error: tegOfFragment is null");
        }
        int randomIndex = new Random().nextInt(arrayListAvailableColors.size());
        if (hashMapUsedColors.containsKey(tegOfFragment)) {
            arrayListAvailableColors.add(hashMapUsedColors.remove(tegOfFragment));
        }
        Color color = arrayListAvailableColors.remove(randomIndex);
        hashMapUsedColors.put(tegOfFragment, color);
        return color;
    }

    private void initColors() {
        arrayListAvailableColors.add(new Color("red", 0xFFFF0000));
        arrayListAvailableColors.add(new Color("orange", 0xFFFFA500));
        arrayListAvailableColors.add(new Color("yellow", 0xFFFFFF00));
        arrayListAvailableColors.add(new Color("green", 0xFF00FF00));
        arrayListAvailableColors.add(new Color("cyan", 0xFF00FFFF));
        arrayListAvailableColors.add(new Color("blue", 0xFF0000FF));
        arrayListAvailableColors.add(new Color("purple", 0xFF800080));
    }
}
