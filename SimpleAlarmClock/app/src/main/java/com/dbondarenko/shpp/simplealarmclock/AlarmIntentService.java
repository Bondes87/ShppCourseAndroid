package com.dbondarenko.shpp.simplealarmclock;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * File: AlarmIntentService.java
 * Service in which the logic of the alarm works. It tracks the time when need to start
 * the alarm clock. Here, also, the alarm is canceled.
 * Created by Dmitro Bondarenko on 30.08.2017.
 */
public class AlarmIntentService extends IntentService {

    public static final int ONE_SECOND = 1000;
    private static final String LOG_TAG = "alarm_service";
    private static final String ACTION_TURN_ON_ALARM_CLOCK =
            "com.dbondarenko.shpp.simplealarmclock.action.TurnOnAlarmClock";
    private static final String EXTRA_DATETIME =
            "com.dbondarenko.shpp.simplealarmclock.extra.Datetime";
    // The variable that is used to stop the service if the alarm is canceled.
    private boolean isAlarmCanceled;

    public AlarmIntentService() {
        super("AlarmIntentService");
        // Set the ability to restart the service if it was stopped and its work is not completed.
        setIntentRedelivery(true);
    }

    /**
     * Returns the intent to run this service to perform the TurnOnAlarmClock action with
     * the specified parameters.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The time in milliseconds when want to start the alarm clock.
     * @return The Intent supplied to startService(Intent), as given.
     */
    public static Intent newIntent(Context context, long datetime) {
        Utility.checkForNull(context);
        Utility.checkForNegativeNumber(datetime);
        Intent intentToStartAlarmService = new Intent(context, AlarmIntentService.class);
        intentToStartAlarmService.setAction(ACTION_TURN_ON_ALARM_CLOCK);
        intentToStartAlarmService.putExtra(EXTRA_DATETIME, datetime);
        return intentToStartAlarmService;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Set the value of the isAlarmCanceled variable to true to cancel the alarm
        // (stop the service).
        isAlarmCanceled = true;
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Utility.checkForNull(intent);
        Log.d(LOG_TAG, "onHandleIntent()");
        final String action = intent.getAction();
        if (ACTION_TURN_ON_ALARM_CLOCK.equals(action)) {
            long datetime = intent.getLongExtra(EXTRA_DATETIME, -1);
            Utility.checkForNegativeNumber(datetime);
            waitForRightTime(datetime);
            // If the alarm is canceled, do not start the alarm stop window.
            if (!isAlarmCanceled) {
                startAlarmActivity();
            }
        }
    }

    /**
     * Wait for the correct time to start the alarm.
     *
     * @param alarmDatetime The time in milliseconds when want to start the alarm clock.
     */
    private void waitForRightTime(long alarmDatetime) {
        Log.d(LOG_TAG, "waitForRightTime(): alarmTime = " + alarmDatetime);
        Utility.checkForNegativeNumber(alarmDatetime);
        long currentDatetime;
        // Check the current time as long as it is less than the alarm
        // time or until the alarm was canceled.
        do {
            currentDatetime = Calendar.getInstance().getTimeInMillis();
            Log.d(LOG_TAG, "waitForRightTime(): currentTime = " + currentDatetime);
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "playAlarmSound()" + e);
                e.printStackTrace();
            }
        }
        while (alarmDatetime > currentDatetime && !isAlarmCanceled);
    }

    /**
     * Launch alarm window.
     */
    private void startAlarmActivity() {
        Intent intentToStartAlarmActivity = new Intent(getApplicationContext(), AlarmActivity.class);
        // Set this action as the beginning of a new task in this history stack.
        intentToStartAlarmActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartAlarmActivity);
    }
}