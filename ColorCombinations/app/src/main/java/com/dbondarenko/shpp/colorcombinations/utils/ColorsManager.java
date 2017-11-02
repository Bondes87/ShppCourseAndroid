package com.dbondarenko.shpp.colorcombinations.utils;

import android.content.Context;
import android.util.Log;

import com.dbondarenko.shpp.colorcombinations.Constants;
import com.dbondarenko.shpp.colorcombinations.R;
import com.dbondarenko.shpp.colorcombinations.models.ColorModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * File: ColorsManager.java
 * The class in which the colors are managed.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class ColorsManager {

    private static final String LOG_TAG = ColorsManager.class.getSimpleName() + "";

    private static ColorsManager colorsManager;
    // The list that contains the available colors.
    private ArrayList<ColorModel> arrayListAvailableColors;
    // The map that contains the colors used.
    private HashMap<String, ColorModel> hashMapUsedColors;

    private ColorsManager() {
        Log.d(LOG_TAG, "ColorsManager()");
        arrayListAvailableColors = new ArrayList<>();
        hashMapUsedColors = new HashMap<>();
        //initColors(context);
    }

    /**
     * Returns the ColorManager object. If an object is not created, it creates it.
     *
     * @return ColorManager object.
     */
    public static ColorsManager getColorsManager() {
        Log.d(LOG_TAG, "getColorsManager()");
        if (colorsManager == null) {
            colorsManager = new ColorsManager();
        }
        return colorsManager;
    }

    /**
     * Returns the list of available colors.
     *
     * @return the list of available colors.
     */
    public ArrayList<ColorModel> getAvailableColors() {
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
    public ColorModel getAvailableColor(int colorIndex, String fragmentTag) {
        Log.d(LOG_TAG, "getAvailableColor()");
        Util.checkStringToNull(fragmentTag);
        Util.checkIndexOutOfBounds(colorIndex, arrayListAvailableColors.size());
        ColorModel availableColor = arrayListAvailableColors.remove(colorIndex);
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
    public ColorModel getRandomAvailableColor(String fragmentTag) {
        Log.d(LOG_TAG, "getRandomAvailableColor()");
        Util.checkStringToNull(fragmentTag);
        int randomIndex = new Random().nextInt(arrayListAvailableColors.size());
        return getAvailableColor(randomIndex, fragmentTag);
    }

    /**
     * Returns the used color.
     *
     * @param fragmentTag The fragment tag for which want to get the color.
     * @return the used color.
     */
    public ColorModel getUsedColor(String fragmentTag) {
        Log.d(LOG_TAG, "getUsedColor()");
        Util.checkStringToNull(fragmentTag);
        ColorModel usedColor = hashMapUsedColors.get(fragmentTag);
        if (usedColor != null) {
            arrayListAvailableColors.remove(usedColor);
            return usedColor;
        } else {
            return getRandomAvailableColor(fragmentTag);
        }
    }

    /**
     * Returns default of color of option menu item.
     *
     * @param context The Context of the application package implementing this class.
     * @return the default of color of option menu item.
     */
    public ColorModel getColorDefaultOfOptionMenuItem(Context context) {
        Log.d(LOG_TAG, "getColorDefaultOfOptionMenuItem()");
        Util.checkForNull(context);
        return new ColorModel(Constants.BLACK_COLOR,
                context.getResources().getColor(R.color.black));
    }

    /**
     * Sets the color available, adding it to the list of available colors.
     *
     * @param fragmentTag The fragment tag the color of which
     *                    is set to make available.
     */
    public void setAvailableColor(String fragmentTag) {
        Log.d(LOG_TAG, "setAvailableColor()");
        Util.checkStringToNull(fragmentTag);
        ColorModel usedColor = hashMapUsedColors.get(fragmentTag);
        if (usedColor != null && !arrayListAvailableColors.contains(usedColor)) {
            arrayListAvailableColors.add(0, usedColor);
        }
    }

    /**
     * Initialization colors.
     *
     * @param context The Context of the application package implementing this class.
     */
    public void initColors(Context context) {
        Log.d(LOG_TAG, "initColors()");
        Util.checkForNull(context);
        arrayListAvailableColors.add(new ColorModel(Constants.RED_COLOR,
                context.getResources().getColor(R.color.red)));
        arrayListAvailableColors.add(new ColorModel(Constants.ORANGE_COLOR,
                context.getResources().getColor(R.color.orange)));
        arrayListAvailableColors.add(new ColorModel(Constants.YELLOW_COLOR,
                context.getResources().getColor(R.color.yellow)));
        arrayListAvailableColors.add(new ColorModel(Constants.GREEN_COLOR,
                context.getResources().getColor(R.color.green)));
        arrayListAvailableColors.add(new ColorModel(Constants.CYAN_COLOR,
                context.getResources().getColor(R.color.cyan)));
        arrayListAvailableColors.add(new ColorModel(Constants.BLUE_COLOR,
                context.getResources().getColor(R.color.blue)));
        arrayListAvailableColors.add(new ColorModel(Constants.PURPLE_COLOR,
                context.getResources().getColor(R.color.purple)));
    }

    /**
     * Finds for a key by value in the map.
     *
     * @param color The value for key search.
     * @return the found key or an empty string.
     */
    private String findKeyByValue(ColorModel color) {
        Log.d(LOG_TAG, "findKeyByValue()");
        Util.checkForNull(color);
        String key = "";
        for (Map.Entry<String, ColorModel> entry : hashMapUsedColors.entrySet())
            if (color.equals(entry.getValue())) {
                key = entry.getKey();
                break;
            }
        return key;
    }
}