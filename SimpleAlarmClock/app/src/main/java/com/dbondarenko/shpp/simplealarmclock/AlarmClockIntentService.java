package com.dbondarenko.shpp.simplealarmclock;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Objects;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class AlarmClockIntentService extends IntentService {

    private static final String LOG_TAG = "alarm_service";
    private static final String ACTION_TURN_ON_ALARM_CLOCK =
            "com.dbondarenko.shpp.simplealarmclock.action.TurnOnAlarmClock";
    private static final String EXTRA_HOUR = "com.dbondarenko.shpp.simplealarmclock.extra.Hour";
    private static final String EXTRA_MINUTE = "com.dbondarenko.shpp.simplealarmclock.extra.Minute";

    public AlarmClockIntentService() {
        super("AlarmClockIntentService");
        setIntentRedelivery(true);
    }

    /**
     * Starts this service to perform action TurnOnAlarmClock with the given parameters. If
     * the service is already performing a task this action will be queued.
     */
    public static void startActionTurnOnAlarmClock(Context context, int hour, int minute) {
        Intent intent = new Intent(context, AlarmClockIntentService.class);
        intent.setAction(ACTION_TURN_ON_ALARM_CLOCK);
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                Intent dialogIntent = new Intent(getApplicationContext(), ResultsActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
        }
    }

    /**
     *
     */
    private void waitForRightTime(int alarmHour, int alarmMinute) {
        String alarmTime = alarmHour + ":" + alarmMinute;
        Log.d(LOG_TAG, "waitForRightTime(): alarmTime = " + alarmTime);
        String currentTime;
        do {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);
            currentTime = currentHour + ":" + currentMinute;
            Log.d(LOG_TAG, "waitForRightTime(): currentTime = " + currentTime);
        }
        while (!Objects.equals(currentTime, alarmTime));
    }
}
