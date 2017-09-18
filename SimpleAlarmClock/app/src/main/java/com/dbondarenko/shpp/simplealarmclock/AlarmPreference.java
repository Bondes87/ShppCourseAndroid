package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * File: AlarmPreference.java
 * The class that contains methods for saving, retrieving and clearing the alarm time settings.
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
class AlarmPreference {

    private static final String LOG_TAG = "alarm_preference";

    // The constant that specifies the name of the setting to save.
    private static final String ALARM_CLOCK_PREFERENCES_DATETIME = "Datetime";

    /**
     * Save the alarm time.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The number of milliseconds.
     */
    static void saveDatetimeSettings(Context context, long datetime) {
        if (context == null) {
            Log.d(LOG_TAG, "saveDatetimeSettings(): the context is equal to null");
        } else if (datetime < 0) {
            Log.d(LOG_TAG, "saveDatetimeSettings(): the time of the alarm was set incorrectly");
        } else {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putLong(ALARM_CLOCK_PREFERENCES_DATETIME, datetime)
                    .apply();
        }
    }

    /**
     * Get Datetime in milliseconds.
     *
     * @param context The Context of the application package implementing this class.
     * @return The number of milliseconds or -1 if the alarm was not activated.
     */
    static long getDatetimeSettings(Context context) {
        if (context == null) {
            Log.d(LOG_TAG, "getDatetimeSettings(): the context is equal to null");
        } else {
            return PreferenceManager.getDefaultSharedPreferences(context)
                    .getLong(ALARM_CLOCK_PREFERENCES_DATETIME, -1);
        }
        return -1;
    }

    /**
     * Get the path to the alarm ringtone.
     *
     * @param context The Context of the application package implementing this class.
     * @return The path to the ringtone or the null.
     */
    static String getRingtoneSettings(Context context) {
        if (context == null) {
            Log.d(LOG_TAG, "getRingtoneSettings(): the context is equal to null");
        } else {
            return PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(context.getString(R.string.key_ringtone_settings), null);
        }
        return null;
    }

    /**
     * Get the number of minutes for snooze.
     *
     * @param context The Context of the application package implementing this class.
     * @return The path to the ringtone or the null.
     */
    static String getSnoozeSettings(Context context) {
        if (context == null) {
            Log.d(LOG_TAG, "getSnoozeSettings(): the context is equal to null");
        } else {
            return PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(context.getString(R.string.key_snooze_settings), null);
        }
        return null;
    }

    /**
     * Remove the alarm time.
     *
     * @param context The Context of the application package implementing this class.
     */
    static void removeDatetimeSettings(Context context) {
        if (context == null) {
            Log.d(LOG_TAG, "removeDatetimeSettings(): the context is equal to null");
        } else {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .remove(ALARM_CLOCK_PREFERENCES_DATETIME)
                    .apply();
        }
    }
}