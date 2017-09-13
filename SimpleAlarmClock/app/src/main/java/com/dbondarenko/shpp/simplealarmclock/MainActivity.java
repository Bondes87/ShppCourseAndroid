package com.dbondarenko.shpp.simplealarmclock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * File: MainActivity.java
 * The activity which is launched when the program starts. Here can set up and start the alarm.
 * Created by Dmitro Bondarenko on 29.08.2017.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "main_activity";

    private TimePicker timePicker;
    private TextView tvAlarmTime;
    private Button bTurnOn;
    private Button bCancel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonTurnOn:
                // Provide tactile feedback for the button.
                bTurnOn.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                turnOnAlarmClock();
                break;

            case R.id.buttonCancel:
                // Provide tactile feedback for the button.
                bCancel.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                cancelAlarmClock();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        // Get and display the alarm time if it was activated earlier.
        long datetime = AlarmPreference.getDatetimeSettings(getApplicationContext());
        if (datetime != -1) {
            showAlarmTime(Utility.getTime(datetime));
        }
        PreferenceManager.setDefaultValues(this, R.xml.setting_alarm, false);
    }

    /**
     * Initialize views and set listeners.
     */
    private void initViews() {
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        bTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        bCancel = (Button) findViewById(R.id.buttonCancel);

        bTurnOn.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        // Set that the button should have tactile feedback.
        bTurnOn.setHapticFeedbackEnabled(true);
        bCancel.setHapticFeedbackEnabled(true);
    }

    /**
     * Activate alarm. Get time with the TimePiker. Translate it into the number of milliseconds
     * given the current date. Save the alarm time. Start the service,
     * which operates the alarm clock logic.
     */
    private void turnOnAlarmClock() {
        Log.d(LOG_TAG, "Alarm clock turn on");
        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }
        showAlarmTime(hour + ":" + (minute < 10 ? "0" + minute : minute));
        // Get the number of milliseconds from the date the alarm was activated.
        long alarmDatetime = getDatetime(hour, minute);
        // Save number of milliseconds (alarm activation date).
        AlarmPreference.saveDatetimeSettings(getApplicationContext(), alarmDatetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        startService(AlarmIntentService.newIntent(getApplicationContext(), alarmDatetime));
    }

    /**
     * Show the time of the alarm on the screen.
     *
     * @param time The alarm time for on-screen display.
     */
    private void showAlarmTime(String time) {
        tvAlarmTime.setText(getResources().getString(R.string.turn_on, time));
    }

    /**
     * Get time it into the number of milliseconds given the current date.
     *
     * @param alarmHour   The hour when the alarm should ring.
     * @param alarmMinute Minutes when the alarm should ring.
     * @return The time when the alarm should ring in milliseconds.
     */
    private long getDatetime(int alarmHour, int alarmMinute) {
        Calendar calendar = Calendar.getInstance();
        int alarmDay = calendar.get(Calendar.DAY_OF_YEAR);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        Log.d(LOG_TAG, "current datetime: " + calendar.getTimeInMillis());
        // If the current time is longer than the alarm time,
        // then the alarm clock is set for tomorrow (increase the number of days by one).
        if (currentHour > alarmHour) {
            alarmDay++;
        } else if (currentHour == alarmHour) {
            if (currentMinute >= alarmMinute) {
                alarmDay++;
            }
        }
        calendar.set(Calendar.DAY_OF_YEAR, alarmDay);
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.d(LOG_TAG, "alarm datetime: " + calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    /**
     * Cancel alarm. Display a message to cancel the alarm.
     * Stop the service that controls the alarm logic. Delete the alarm time settings.
     */
    private void cancelAlarmClock() {
        Log.d(LOG_TAG, "Alarm clock cancel");
        tvAlarmTime.setText(R.string.cancel);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        AlarmPreference.removeDatetimeSettings(getApplicationContext());
    }
}