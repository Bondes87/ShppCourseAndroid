package com.dbondarenko.shpp.simplealarmclock;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * File: AlarmActivity.java
 * The activity in which  is stop the alarm.
 * Created by Dmitro Bondarenko on 30.08.2017.
 */
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "result_activity";


    private TextView textViewAlarmTime;
    private AnimationDrawable animationDrawableAlarm;
    private Button buttonTurnOff;
    private Button buttonSnooze;
    private ImageView imageViewAlarm;

    // Variable for storing the value of the screen activity.
    // Used to select actions to stop the alarm.
    private boolean isScreenOn;
    // A variable is used to select actions before deleting an activity.
    // If its value is true, then the alarm time settings are not deleted,
    // otherwise - there will be deletions.
    private boolean isSnoozeAlarm;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonTurnOff:
                Log.d(LOG_TAG, "stop Alarm");
                // Provide tactile feedback for the button.
                buttonTurnOff.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                turnOffAlarm();
                stopPlaySoundAndVibration();
                break;

            case R.id.buttonSnooze:
                Log.d(LOG_TAG, "snooze Alarm");
                // Provide tactile feedback for the button.
                buttonSnooze.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                snoozeAlarm();
                stopPlaySoundAndVibration();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Log.d(LOG_TAG, "onCreate()");
        findViews();
        initViews();
        Log.d(LOG_TAG, "onCreate() >-1  " + AlarmPreference.getDatetimeSettings(getApplicationContext()));
        determineScreenStatus();
        setUpScreen();
        startAlarmAnimation();
        startPlaySoundAndVibration();
        showAlarmTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
        Log.d(LOG_TAG, "isScreenOn = " + isScreenOn);
        // If the screen was unlocked the first time the activity was activated,
        // then stop the alarm, otherwise - change the value of isScreenOn to true.
        /*if (isScreenOn) {
            turnOffAlarm();
        } else {
            isScreenOn = true;
        }*/
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        super.onDestroy();
        stopAnimation();
        finish();
    }

    private void stopPlaySoundAndVibration() {
        stopService(AlarmPlaySoundIntentService.newIntent(getApplicationContext()));
    }

    private void findViews() {
        imageViewAlarm = (ImageView) findViewById(R.id.imageViewAlarm);
        imageViewAlarm.setBackgroundResource(R.drawable.alarm_animation);
        textViewAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        buttonTurnOff = (Button) findViewById(R.id.buttonTurnOff);
        buttonSnooze = (Button) findViewById(R.id.buttonSnooze);
    }

    /**
     * Initialize views and set listeners.
     */
    private void initViews() {
        buttonTurnOff.setOnClickListener(this);
        buttonSnooze.setOnClickListener(this);
        // Set that the button should have tactile feedback.
        buttonTurnOff.setHapticFeedbackEnabled(true);
        buttonSnooze.setHapticFeedbackEnabled(true);
    }

    /**
     * Determine the status of the screen when activates for the first time.
     * The result is assigned to the variable isScreenOn.
     */
    private void determineScreenStatus() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();
        }
        Log.d(LOG_TAG, "isScreenOn = " + isScreenOn);
    }

    /**
     * Start the alarm animation.
     */
    private void startAlarmAnimation() {
        animationDrawableAlarm = (AnimationDrawable) imageViewAlarm.getBackground();
        animationDrawableAlarm.start();
    }

    /**
     * Set screen options.
     */
    private void setUpScreen() {
        // Show activity when the screen is locked.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        // Enable the screen for this activity.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        // Do not turn off the screen while this activity is displayed.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Play alarm sound.
     */
    private void startPlaySoundAndVibration() {
        startService(AlarmPlaySoundIntentService.newIntent(getApplicationContext()));
    }


    /**
     * Show the time of the alarm on the screen.
     */
    private void showAlarmTime() {
        long datetime = AlarmPreference.getDatetimeSettings(getApplicationContext());
        if (datetime < 0) {
            Log.d(LOG_TAG, "showAlarmTime(): the time of the alarm was set incorrectly");
        } else {
            textViewAlarmTime.setText(getResources().getString(R.string.alarm_time,
                    Utility.getTime(datetime)));
        }
    }

    /**
     * Turn off the alarm clock.
     */
    private void turnOffAlarm() {
        stopAnimation();
        // Delete the settings if the Snooze button has not been pressed.
        if (!isSnoozeAlarm) {
            AlarmPreference.removeDatetimeSettings(getApplicationContext());
        }
        finish();
    }

    /**
     * Stop the animation.
     */
    private void stopAnimation() {
        animationDrawableAlarm.stop();
    }

    /**
     * Snooze the alarm for another time.
     */
    private void snoozeAlarm() {
        isSnoozeAlarm = true;
        stopAnimation();
        String snoozeSettings = AlarmPreference.getSnoozeSettings(getApplicationContext());
        if (TextUtils.isEmpty(snoozeSettings)) {
            Log.d(LOG_TAG, "showAlarmTime(): the snooze time of the alarm is not set");
        } else {
            // Get the number of minutes for sleep.
            int snoozeTime = Integer.parseInt(snoozeSettings);
            // Create a new Datetime for the alarm.
            long newAlarmDatetime = AlarmPreference.getDatetimeSettings(getApplicationContext())
                    + DateUtils.MINUTE_IN_MILLIS * snoozeTime;
            Log.d(LOG_TAG, "snoozeAlarm()" + snoozeTime);
            AlarmPreference.saveDatetimeSettings(getApplicationContext(), newAlarmDatetime);
            stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
            startService(AlarmIntentService.newIntent(getApplicationContext(), newAlarmDatetime));
        }
        finish();
    }
}