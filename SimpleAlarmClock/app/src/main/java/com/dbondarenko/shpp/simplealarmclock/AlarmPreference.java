package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * File: AlarmPreference.java
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
public class AlarmPreference {

    private static final String ALARM_CLOCK_PREFERENCES = "AlarmPreference";
    private static final String ALARM_CLOCK_PREFERENCES_HOUR = "Hour";
    private static final String ALARM_CLOCK_PREFERENCES_MINUTE = "Minute";

    static void setTimeSettings(Context context, int hour, int minute) {
        context.getSharedPreferences(ALARM_CLOCK_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putInt(ALARM_CLOCK_PREFERENCES_HOUR, hour)
                .putInt(ALARM_CLOCK_PREFERENCES_MINUTE, minute)
                .apply();
    }

    static int getHour(Context context) {
        SharedPreferences settings = context.getSharedPreferences(ALARM_CLOCK_PREFERENCES,
                Context.MODE_PRIVATE);
        return settings.getInt(ALARM_CLOCK_PREFERENCES_HOUR, -1);
    }

    static int getMitute(Context context) {
        SharedPreferences settings = context.getSharedPreferences(ALARM_CLOCK_PREFERENCES,
                Context.MODE_PRIVATE);
        return settings.getInt(ALARM_CLOCK_PREFERENCES_MINUTE, -1);
    }

    static void clearTimeSettings(Context context) {
        context.getSharedPreferences(ALARM_CLOCK_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}
