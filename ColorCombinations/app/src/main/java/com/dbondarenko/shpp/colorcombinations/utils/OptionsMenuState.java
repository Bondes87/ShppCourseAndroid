package com.dbondarenko.shpp.colorcombinations.utils;

import android.util.Log;

import com.dbondarenko.shpp.colorcombinations.Constants;

/**
 * File: OptionsMenuState.java
 * The class that stores the checkboxes settings for the options menu.
 * Created by Dmitro Bondarenko on 18.10.2017.
 */
public class OptionsMenuState {

    private static final String LOG_TAG = OptionsMenuState.class.getSimpleName();

    private static OptionsMenuState optionsMenuState;
    // Array for storing checkboxes settings for options menu.
    private boolean[] checkboxesSettingsForMenuItems;

    private OptionsMenuState() {
        checkboxesSettingsForMenuItems = new boolean[Constants
                .NUMBER_OF_CHECKBOXES_IN_OPTIONS_MENU];
    }

    /**
     * Returns the OptionsMenuState object. If an object is not created, it creates it.
     *
     * @return OptionsMenuState object.
     */
    public static OptionsMenuState getOptionsMenuState() {
        Log.d(LOG_TAG, "getOptionsMenuState()");
        if (optionsMenuState == null) {
            optionsMenuState = new OptionsMenuState();
        }
        return optionsMenuState;
    }

    /**
     * Returns the array of checkboxes settings for options menu.
     *
     * @return the array of checkboxes settings for options menu.
     */
    public boolean[] getCheckboxesSettingsForMenuItems() {
        Log.d(LOG_TAG, "getCheckboxesSettingsForMenuItems()");
        return checkboxesSettingsForMenuItems;
    }

    /**
     * Returns the array of checkboxes settings for options menu.
     *
     * @param menuItemIndex      The index of item in the options menu.
     * @param isSelectedCheckbox The checkbox value in options menu.
     */
    public void setCheckboxSettingsOfMenuItem(int menuItemIndex, boolean isSelectedCheckbox) {
        Log.d(LOG_TAG, "setCheckboxSettingsOfMenuItem()");
        Util.checkIndexOutOfBounds(menuItemIndex, checkboxesSettingsForMenuItems.length);
        checkboxesSettingsForMenuItems[menuItemIndex] = isSelectedCheckbox;
    }

    /**
     * Initialization of checkboxes value in options menu.
     */
    public void initCheckboxSettingsOfMenuItem() {
        Log.d(LOG_TAG, "initCheckboxSettingsOfMenuItem()");
        for (int i = 0; i < checkboxesSettingsForMenuItems.length; i++) {
            checkboxesSettingsForMenuItems[i] = true;
        }
    }
}
