package com.dbondarenko.shpp.simplealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * File: RestoreAlarmBroadcastReceiver.java
 * The BroaadcastReceiver which is responsible for starting the alarm
 * after the phone is rebooted, if it was activated.
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
public class RestoreAlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "alarm_receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null) {
            Log.d(LOG_TAG, "onReceive(): the context is equal to null");
        } else {
            long datetime = AlarmPreference.getAlarmPreference()
                    .getDatetimeSettings(context);
            // Start the service if there is an alarm time value.
            if (datetime != -1) {
                context.startService(AlarmIntentService.newIntent(context, datetime));
            } else {
                Log.d(LOG_TAG, "onReceive(): the time of the alarm was set incorrectly");
            }
        }
    }
}