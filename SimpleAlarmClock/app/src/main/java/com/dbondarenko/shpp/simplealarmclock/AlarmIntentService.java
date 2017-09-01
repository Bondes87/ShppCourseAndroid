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
    public static final String EXTRA_HOUR = "com.dbondarenko.shpp.simplealarmclock.extra.Hour";
    public static final String EXTRA_MINUTE = "com.dbondarenko.shpp.simplealarmclock.extra.Minute";
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
    public static Intent newIntent(Context context, int hour, int minute) {
        Intent intent = new Intent(context, AlarmIntentService.class);
        intent.setAction(ACTION_TURN_ON_ALARM_CLOCK);
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);
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
                int hour = intent.getIntExtra(EXTRA_HOUR, -1);
                int minute = intent.getIntExtra(EXTRA_MINUTE, -1);
                waitForRightTime(hour, minute);
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

    private void waitForRightTime(int alarmHour, int alarmMinute) {
        Log.d(LOG_TAG, "waitForRightTime(): alarmTime = " + alarmHour + ":" + alarmMinute);
        if (alarmHour != -1 && alarmMinute != -1) {
            int currentHour;
            int currentMinute;
            do {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                Log.d(LOG_TAG, "waitForRightTime(): currentTime = " + currentHour + ":" + currentMinute);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!(alarmHour == currentHour & alarmMinute == currentMinute) && !isAlarmCanceled);
        } else {
            Log.e(LOG_TAG, "The alarm time is not set.");
        }
    }
}
