package com.dbondarenko.shpp.colorcombinations;

import android.util.Log;

/**
 * File: OptionsMenuState.java
 * The class that stores the checkboxes settings for the options menu.
 * Created by Dmitro Bondarenko on 18.10.2017.
 */
class OptionsMenuState {

    private static final String LOG_TAG = "option_menu_state";

    // The number of checkboxes in the options menu.
    private static final int NUMBER_OF_CHECKBOXES_IN_OPTIONS_MENU = 3;

    private static OptionsMenuState optionsMenuState;
    // Array for storing checkboxes settings for options menu.
    private boolean[] checkboxesSettingsForMenuItems;

    private OptionsMenuState() {
        checkboxesSettingsForMenuItems = new boolean[NUMBER_OF_CHECKBOXES_IN_OPTIONS_MENU];
    }

    /**
     * Returns the OptionsMenuState object. If an object is not created, it creates it.
     *
     * @return OptionsMenuState object.
     */
    static OptionsMenuState getOptionsMenuState() {
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
    boolean[] getCheckboxesSettingsForMenuItems() {
        Log.d(LOG_TAG, "getCheckboxesSettingsForMenuItems()");
        return checkboxesSettingsForMenuItems;
    }

    /**
     * Returns the array of checkboxes settings for options menu.
     *
     * @param menuItemIndex      The index of item in the options menu.
     * @param isSelectedCheckbox The checkbox value in options menu.
     */
    void setCheckboxSettingsOfMenuItem(int menuItemIndex, boolean isSelectedCheckbox) {
        Log.d(LOG_TAG, "setCheckboxSettingsOfMenuItem()");
        Utility.checkIndexOutOfBounds(menuItemIndex, checkboxesSettingsForMenuItems.length);
        checkboxesSettingsForMenuItems[menuItemIndex] = isSelectedCheckbox;
    }

    /**
     * Initialization of checkboxes value in options menu.
     */
    void initCheckboxSettingsOfMenuItem() {
        Log.d(LOG_TAG, "initCheckboxSettingsOfMenuItem()");
        for (int i = 0; i < checkboxesSettingsForMenuItems.length; i++) {
            checkboxesSettingsForMenuItems[i] = true;
        }
    }
}
