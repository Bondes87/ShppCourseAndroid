package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * File: AlarmPreference.java
 * The class that contains methods for saving, retrieving and clearing the alarm time settings,
 * methods for getting the path to the alarm sound and the snooze time for the alarm.
 * The class is built using the Singleton pattern
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
class AlarmPreference {

    private static final String LOG_TAG = "alarm_preference";

    // The constant that specifies the name of the setting to save.
    private static final String ALARM_CLOCK_PREFERENCES_DATETIME = "Datetime";

    private static AlarmPreference alarmPreference;

    private AlarmPreference() {
    }

    /**
     * Get the object AlarmPreference. If the object AlarmPreference is null,
     * then create the object, otherwise return the existing object.
     *
     * @return object AlarmPreference.
     */
    static AlarmPreference getAlarmPreference() {
        if (alarmPreference == null) {
            alarmPreference = new AlarmPreference();
        }
        return alarmPreference;
    }

    /**
     * Save the alarm time.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The number of milliseconds.
     */
    void saveDatetimeSettings(Context context, long datetime) {
        Log.d(LOG_TAG, "saveDatetimeSettings()");
        Utility.checkForNull(context);
        Utility.checkForNegativeNumber(datetime);
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
    long getDatetimeSettings(Context context) {
        Log.d(LOG_TAG, "getDatetimeSettings()");
        Utility.checkForNull(context);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(ALARM_CLOCK_PREFERENCES_DATETIME, -1);
    }

    /**
     * Get the path to the alarm ringtone.
     *
     * @param context The Context of the application package implementing this class.
     * @return The path to the ringtone or the null.
     */
    String getRingtoneSettings(Context context) {
        Log.d(LOG_TAG, "getRingtoneSettings()");
        Utility.checkForNull(context);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_ringtone_settings), null);
    }

    /**
     * Get the number of minutes for snooze.
     *
     * @param context The Context of the application package implementing this class.
     * @return The path to the ringtone or the null.
     */
    String getSnoozeSettings(Context context) {
        Log.d(LOG_TAG, "getSnoozeSettings()");
        Utility.checkForNull(context);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_snooze_settings), null);
    }

    /**
     * Remove the alarm time.
     *
     * @param context The Context of the application package implementing this class.
     */
    void removeDatetimeSettings(Context context) {
        Log.d(LOG_TAG, "removeDatetimeSettings()");
        Utility.checkForNull(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(ALARM_CLOCK_PREFERENCES_DATETIME)
                .apply();
    }
}