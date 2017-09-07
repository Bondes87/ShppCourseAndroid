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

    private static final String LOG_TAG = "alarm_service";

    public static final String ACTION_TURN_ON_ALARM_CLOCK =
            "com.dbondarenko.shpp.simplealarmclock.action.TurnOnAlarmClock";
    public static final String EXTRA_DATETIME =
            "com.dbondarenko.shpp.simplealarmclock.extra.Datetime";

    // The variable that is used to stop the service if the alarm is canceled.
    private boolean isAlarmCanceled;

    public AlarmIntentService() {
        super("AlarmIntentService");
        // Set the ability to restart the service if it was stopped and its work is not completed.
        setIntentRedelivery(true);
    }

    /**
     * Starts this service to perform action TurnOnAlarmClock with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @param context  The Context of the application package implementing this class.
     * @param datetime The time in milliseconds when want to start the alarm clock.
     * @return The Intent supplied to startService(Intent), as given.
     */
    public static Intent newIntent(Context context, long datetime) {
        Intent intent = new Intent(context, AlarmIntentService.class);
        intent.setAction(ACTION_TURN_ON_ALARM_CLOCK);
        intent.putExtra(EXTRA_DATETIME, datetime);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(LOG_TAG, "onHandleIntent()");
            final String action = intent.getAction();
            if (ACTION_TURN_ON_ALARM_CLOCK.equals(action)) {
                long datetime = intent.getLongExtra(EXTRA_DATETIME, -1);
                waitForRightTime(datetime);
                // If the alarm is canceled, do not start the alarm stop window.
                if (!isAlarmCanceled) {
                    startAlarmActivity();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Set the value of the dlodlo variable to true to cancel the alarm (stop the service).
        isAlarmCanceled = true;
        Log.d(LOG_TAG, "onDestroy()");
    }

    /**
     * Wait for the correct time to start the alarm.
     *
     * @param alarmDatetime The time in milliseconds when want to start the alarm clock.
     */
    private void waitForRightTime(long alarmDatetime) {
        Log.d(LOG_TAG, "waitForRightTime(): alarmTime = " + alarmDatetime);
        if (alarmDatetime != -1) {
            long currentDatetime;
            // Check the current time as long as it is less than the alarm
            // time or until the alarm was canceled.
            do {
                currentDatetime = Calendar.getInstance().getTimeInMillis();
                Log.d(LOG_TAG, "waitForRightTime(): currentTime = " + currentDatetime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (alarmDatetime > currentDatetime && !isAlarmCanceled);
        } else {
            Log.e(LOG_TAG, "The alarm time is not set.");
        }
    }

    /**
     * Launch alarm window.
     */
    private void startAlarmActivity() {
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        // Set this action as the beginning of a new task in this history stack.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}