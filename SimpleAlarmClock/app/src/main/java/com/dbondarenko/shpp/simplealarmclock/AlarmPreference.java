package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * File: AlarmPreference.java
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
class AlarmPreference {

    private static final String ALARM_CLOCK_PREFERENCES = "AlarmPreference";
    private static final String ALARM_CLOCK_PREFERENCES_DATETIME = "Datetime";

    static void setDatetimeSettings(Context context, long datetime) {
        context.getSharedPreferences(ALARM_CLOCK_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putLong(ALARM_CLOCK_PREFERENCES_DATETIME, datetime)
                .apply();
    }

    static long getDatetimeSettings(Context context) {
        SharedPreferences settings = context.getSharedPreferences(ALARM_CLOCK_PREFERENCES,
                Context.MODE_PRIVATE);
        return settings.getLong(ALARM_CLOCK_PREFERENCES_DATETIME, -1);
    }

    static void removeDatetimeSettings(Context context) {
        context.getSharedPreferences(ALARM_CLOCK_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .remove(ALARM_CLOCK_PREFERENCES_DATETIME)
                .apply();
    }
}
