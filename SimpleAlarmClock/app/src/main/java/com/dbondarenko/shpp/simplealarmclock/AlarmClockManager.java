package com.dbondarenko.shpp.simplealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Context.ALARM_SERVICE;

/**
 * File: AlarmClockManager.java
 * The class in which the alarm is started and canceled using the Alarm Manager.
 * The class is built using the Singleton pattern.
 * Created by Dmitro Bondarenko on 23.09.2017.
 */
class AlarmClockManager {

    private static final String LOG_TAG = "alarm_manager";

    private static AlarmClockManager alarmClockManager;

    private AlarmClockManager() {
    }

    static AlarmClockManager getAlarmClockManager() {
        if (alarmClockManager == null) {
            alarmClockManager = new AlarmClockManager();
        }
        return alarmClockManager;
    }

    /**
     * Get PendingIntent to run AlarmActivity.
     *
     * @param context The Context of the application package implementing this class.
     * @return PendingIntent to run AlarmActivity.
     */
    private PendingIntent getPendingIntentToStartAlarmActivity(Context context) {
        Utility.checkForNull(context);
        Intent intentToStartAlarmActivity = new Intent(context, AlarmActivity.class);
        // Set this action as the beginning of a new task in this history stack.
        intentToStartAlarmActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, intentToStartAlarmActivity, 0);
    }


    private AlarmManager getAlarmManager(Context context) {
        Utility.checkForNull(context);
        return (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    /**
     * Start the alarm using AlarmManager.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The number of milliseconds.
     */
    void startAlarmUsingAlarmManager(Context context, long datetime) {
        Log.d(LOG_TAG, "startAlarmUsingAlarmManager()");
        Utility.checkForNull(context);
        Utility.checkForNegativeNumber(datetime);
        getAlarmManager(context).set(AlarmManager.RTC_WAKEUP, datetime,
                getPendingIntentToStartAlarmActivity(context));
    }

    /**
     * Cansel the alarm using AlarmManager.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The number of milliseconds.
     */
    void cancelAlarmUsingAlarmManager(Context context, long datetime) {
        Log.d(LOG_TAG, "cancelAlarmUsingAlarmManager()");
        Utility.checkForNull(context);
        Utility.checkForNegativeNumber(datetime);
        getAlarmManager(context).cancel(getPendingIntentToStartAlarmActivity(context));
    }
}