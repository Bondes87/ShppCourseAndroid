package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * File: AlarmPreference.java
 * The class that contains methods for saving, retrieving and clearing the alarm time settings.
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
class AlarmPreference {

    // The constant that specifies the name of the file in which the settings for the alarm are saved.
    private static final String ALARM_CLOCK_PREFERENCES = "AlarmPreference";
    // The constant that specifies the name of the setting to save.
    private static final String ALARM_CLOCK_PREFERENCES_DATETIME = "Datetime";

    /**
     * Save the alarm time.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The number of milliseconds.
     */
    static void saveDatetimeSettings(Context context, long datetime) {
        context.getSharedPreferences(ALARM_CLOCK_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putLong(ALARM_CLOCK_PREFERENCES_DATETIME, datetime)
                .apply();
    }

    /**
     * Save the alarm time.
     *
     * @param context The Context of the application package implementing this class.
     * @return The number of milliseconds or -1 if the alarm was not activated.
     */
    static long getDatetimeSettings(Context context) {
        SharedPreferences settings = context.getSharedPreferences(ALARM_CLOCK_PREFERENCES,
                Context.MODE_PRIVATE);
        return settings.getLong(ALARM_CLOCK_PREFERENCES_DATETIME, -1);
    }

    /**
     * Remove the alarm time.
     *
     * @param context The Context of the application package implementing this class.
     */
    static void removeDatetimeSettings(Context context) {
        context.getSharedPreferences(ALARM_CLOCK_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .remove(ALARM_CLOCK_PREFERENCES_DATETIME)
                .apply();
    }
}