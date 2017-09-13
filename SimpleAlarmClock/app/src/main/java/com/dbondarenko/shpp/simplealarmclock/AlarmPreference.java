package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * File: AlarmPreference.java
 * The class that contains methods for saving, retrieving and clearing the alarm time settings.
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
class AlarmPreference {

    // The constant that specifies the name of the setting to save.
    private static final String ALARM_CLOCK_PREFERENCES_DATETIME = "Datetime";

    /**
     * Save the alarm time.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The number of milliseconds.
     */
    static void saveDatetimeSettings(Context context, long datetime) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(ALARM_CLOCK_PREFERENCES_DATETIME, datetime)
                .apply();
    }

    /**
     * Get Datetime in milliseconds.
     *
     * @param context The Context of the application package implementing this class.
     * @return The number of milliseconds or -1 if the alarm was not activated.
     */
    static long getDatetimeSettings(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(ALARM_CLOCK_PREFERENCES_DATETIME, -1);
    }

    /**
     * Get the path to the alarm ringtone.
     *
     * @param context The Context of the application package implementing this class.
     * @return The path to the ringtone or the null.
     */
    static String getRingtoneSettings(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_ringtone_settings), null);
    }

    /**
     * Get the number of minutes for snooze.
     *
     * @param context The Context of the application package implementing this class.
     * @return The path to the ringtone or the null.
     */
    static String getSnoozeSettings(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_snooze_settings), null);
    }

    /**
     * Remove the alarm time.
     *
     * @param context The Context of the application package implementing this class.
     */
    static void removeDatetimeSettings(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(ALARM_CLOCK_PREFERENCES_DATETIME)
                .apply();
    }
}