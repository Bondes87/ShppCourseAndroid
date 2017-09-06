package com.dbondarenko.shpp.simplealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * File: RestoreAlarmBroaadcastReceiver.java
 * Created by Dmitro Bondarenko on 01.09.2017.
 */
public class RestoreAlarmBroaadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long datetime = AlarmPreference.getDatetimeSettings(context);
        if (datetime != -1) {
            context.startService(AlarmIntentService.newIntent(context, datetime));
        }
    }
}
