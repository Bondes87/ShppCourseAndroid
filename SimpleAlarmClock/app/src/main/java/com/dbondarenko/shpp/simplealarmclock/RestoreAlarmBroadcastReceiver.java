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
        Log.d(LOG_TAG, "onReceive()");
        Utility.checkForNull(context);
        long datetime = AlarmPreference.getAlarmPreference().getDatetimeSettings(context);
        Utility.checkForNegativeNumber(datetime);
        context.startService(AlarmIntentService.newIntent(context, datetime));
    }
}