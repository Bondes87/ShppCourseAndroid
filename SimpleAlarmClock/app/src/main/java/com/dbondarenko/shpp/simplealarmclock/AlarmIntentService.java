package com.dbondarenko.shpp.simplealarmclock;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class AlarmIntentService extends IntentService {

    public static final String ACTION_TURN_ON_ALARM_CLOCK =
            "com.dbondarenko.shpp.simplealarmclock.action.TurnOnAlarmClock";
    public static final String EXTRA_DATETIME =
            "com.dbondarenko.shpp.simplealarmclock.extra.Datetime";

    private static final String LOG_TAG = "alarm_service";

    private boolean isAlarmCanceled;

    public AlarmIntentService() {
        super("AlarmIntentService");
        setIntentRedelivery(true);
    }

    /**
     * Starts this service to perform action TurnOnAlarmClock with the given parameters. If
     * the service is already performing a task this action will be queued.
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
    public void onDestroy() {
        super.onDestroy();
        isAlarmCanceled = true;
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(LOG_TAG, "onHandleIntent()");
            final String action = intent.getAction();
            if (ACTION_TURN_ON_ALARM_CLOCK.equals(action)) {
                long datetime = intent.getLongExtra(EXTRA_DATETIME, -1);
                waitForRightTime(datetime);
                if (!isAlarmCanceled) {
                    startAlarmActivity();
                }
            }
        }
    }

    private void startAlarmActivity() {
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void waitForRightTime(long alarmDatetime) {
        Log.d(LOG_TAG, "waitForRightTime(): alarmTime = " + alarmDatetime);
        if (alarmDatetime != -1) {
            long currentDatetime;
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
}
