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

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


/**
 * File: MainActivity.java
 * The activity which is launched when the program starts. Here can set up and start the alarm.
 * Created by Dmitro Bondarenko on 29.08.2017.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "main_activity";

    private static final String SHOWCASE_ID =
            "com.dbondarenko.shpp.simplealarmclock.action.MainActivity";

    @BindView(R.id.timePicker)
    TimePicker timePickerAlarmTime;
    @BindView(R.id.textViewAlarmTime)
    TextView textViewAlarmTime;
    @BindView(R.id.buttonTurnOn)
    Button buttonTurnOn;
    @BindView(R.id.buttonCancel)
    Button buttonCancel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intentToStartSettingsActivity = new Intent(getApplicationContext(),
                    SettingsActivity.class);
            startActivity(intentToStartSettingsActivity);
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
                buttonTurnOn.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                turnOnAlarmClock();
                break;

            case R.id.buttonCancel:
                // Provide tactile feedback for the button.
                buttonCancel.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (AlarmPreference.getAlarmPreference().
                        getDatetimeSettings(getApplicationContext()) > -1) {
                    cancelAlarmClock();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        // Save default preferences.
        PreferenceManager.setDefaultValues(this, R.xml.setting_alarm, false);
        presentShowcaseView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
        // Get and display the alarm time if it was activated earlier.
        long datetime = AlarmPreference.getAlarmPreference()
                .getDatetimeSettings(getApplicationContext());
        if (datetime != -1) {
            showAlarmTime(Utility.getTime(datetime));
        } else {
            textViewAlarmTime.setText(getResources().getString(R.string.alarm_not_installed));
        }
    }

    private void presentShowcaseView() {
        ShowcaseConfig config = new ShowcaseConfig();
        // Half second between each showcase view
        config.setDelay(500);
        config.setShapePadding(0);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);
        sequence.setConfig(config);
        sequence.addSequenceItem(buttonTurnOn,
                "This is button to turn on the alarm", "GOT IT");
        sequence.addSequenceItem(buttonCancel,
                "This is button to cancel the alarm", "GOT IT");
        sequence.addSequenceItem(timePickerAlarmTime,
                "Here you select the time of alarm clock", "GOT IT");
        sequence.start();
    }

    /**
     * Initialize views and set listeners.
     */
    private void initViews() {
        // Set the 24-hour time format for the TimePicker.
        timePickerAlarmTime.setIs24HourView(true);
        buttonTurnOn.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    /**
     * Activate alarm. Get time with the TimePiker. Translate it into the number of milliseconds
     * given the current date. Save the alarm time. Start the service,
     * which operates the alarm clock logic.
     */
    private void turnOnAlarmClock() {
        Log.d(LOG_TAG, "Alarm clock turn on");
        // Get the number of milliseconds from the date the alarm was activated.
        long alarmDatetime = getDatetime(getTime());
        showAlarmTime(Utility.getTime(alarmDatetime));
        // Save number of milliseconds (alarm activation date).
        AlarmPreference.getAlarmPreference()
                .saveDatetimeSettings(getApplicationContext(), alarmDatetime);
        // Cancel alarm clock, which was launched  AlarmManager or AlarmIntentService.
        AlarmClockManager.getAlarmClockManager()
                .cancelAlarmUsingAlarmManager(getApplicationContext(), alarmDatetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        if (AlarmPreference.getAlarmPreference().isUseAlarmManager(getApplicationContext())) {
            AlarmClockManager.getAlarmClockManager()
                    .startAlarmUsingAlarmManager(getApplicationContext(), alarmDatetime);
        } else {
            startService(AlarmIntentService.newIntent(getApplicationContext(), alarmDatetime));
        }
    }

    /**
     * Return an array with two elements, the first is a hour, and the second is a minutes.
     *
     * @return An array with two elements.
     */
    private int[] getTime() {
        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePickerAlarmTime.getHour();
            minute = timePickerAlarmTime.getMinute();
        } else {
            hour = timePickerAlarmTime.getCurrentHour();
            minute = timePickerAlarmTime.getCurrentMinute();
        }
        return new int[]{hour, minute};
    }

    /**
     * Show the time of the alarm on the screen.
     *
     * @param time The alarm time for on-screen display.
     */
    private void showAlarmTime(String time) {
        Utility.checkForEmptyString(time);
        textViewAlarmTime.setText(getResources().getString(R.string.turn_on, time));
    }

    /**
     * Get time it into the number of milliseconds given the current date.
     *
     * @param alarmTime The array with two elements, the first is a hour,
     *                  and the second is a minutes.
     * @return The time when the alarm should ring in milliseconds.
     */
    private long getDatetime(int[] alarmTime) {
        Utility.checkForNegativeNumber(alarmTime[0], alarmTime[1]);
        int alarmHour = alarmTime[0];
        int alarmMinute = alarmTime[1];
        Calendar calendar = Calendar.getInstance();
        int alarmDay = calendar.get(Calendar.DAY_OF_YEAR);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        Log.d(LOG_TAG, "current datetime: " + calendar.getTimeInMillis());
        // If the current time is longer than the alarm time,
        // then the alarm clock is set for tomorrow (increase the number of days by one).
        if (currentHour > alarmHour ||
                (currentHour == alarmHour && currentMinute >= alarmMinute)) {
            alarmDay++;
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
        textViewAlarmTime.setText(R.string.cancel);
        long datetime = AlarmPreference.getAlarmPreference()
                .getDatetimeSettings(getApplicationContext());
        Utility.checkForNegativeNumber(datetime);
        AlarmClockManager.getAlarmClockManager()
                .cancelAlarmUsingAlarmManager(getApplicationContext(), datetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        AlarmPreference.getAlarmPreference()
                .removeDatetimeSettings(getApplicationContext());
    }
}