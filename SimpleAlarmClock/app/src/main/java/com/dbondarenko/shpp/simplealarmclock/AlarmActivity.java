package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * File: AlarmActivity.java
 * The activity in which  is stop the alarm.
 * Created by Dmitro Bondarenko on 30.08.2017.
 */
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "result_activity";

    // Number of minutes of the period snooze of  alarm clock.
    private static final int SNOOZE_PERIOD_IN_MINUTES = 10;

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private TextView tvAlarmTime;
    private AnimationDrawable animationAlarm;
    private Button bTurnOff;
    private Button bSnooze;
    private ImageView ivAlarm;

    // Variable for storing the value of the screen activity.
    // Used to select actions to stop the alarm.
    private boolean isScreenOn;
    // A variable is used to select actions before deleting an activity.
    // If its value is true, then the alarm time settings are not deleted,
    // otherwise - there will be deletions.
    private boolean isSnoozeAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        determineScreenStatus();
        setUpScreen();
        startAlarmAnimation();
        playAlarmSound();
        turnOnVibration();
        showAlarmTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onStop()");
        Log.d(LOG_TAG, "isScreenOn = " + isScreenOn);
        // If the screen was unlocked the first time the activity was activated,
        // then stop the alarm, otherwise - change the value of isScreenOn to true.
        if (isScreenOn) {
            turnOffAlarm();
        } else {
            isScreenOn = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonTurnOff:
                Log.d(LOG_TAG, "stop Alarm");
                // Provide tactile feedback for the button.
                bTurnOff.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                turnOffAlarm();
                break;

            case R.id.buttonSnooze:
                Log.d(LOG_TAG, "snooze Alarm");
                // Provide tactile feedback for the button.
                bSnooze.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                snoozeAlarm();
                break;
        }
    }

    /**
     * Initialize views and set listeners.
     */
    private void initViews() {
        setContentView(R.layout.activity_alarm);
        ivAlarm = (ImageView) findViewById(R.id.imageViewAlarm);
        ivAlarm.setBackgroundResource(R.drawable.alarm_animation);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        bTurnOff = (Button) findViewById(R.id.buttonTurnOff);
        bSnooze = (Button) findViewById(R.id.buttonSnooze);

        bTurnOff.setOnClickListener(this);
        bSnooze.setOnClickListener(this);
        // Set that the button should have tactile feedback.
        bTurnOff.setHapticFeedbackEnabled(true);
        bSnooze.setHapticFeedbackEnabled(true);
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
        animationAlarm = (AnimationDrawable) ivAlarm.getBackground();
        animationAlarm.start();
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
    private void playAlarmSound() {
        Log.d(LOG_TAG, "playAlarmSound()");
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        // Set  repeat the sound.
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    /**
     * Turn on vibration.
     */
    private void turnOnVibration() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // If there is vibration on this device, then run it.
        if (vibrator.hasVibrator()) {
            long[] pattern = {0, 400, 800};
            vibrator.vibrate(pattern, 0);
        }
    }

    /**
     * Show the time of the alarm on the screen.
     */
    private void showAlarmTime() {
        long datetime = AlarmPreference.getDatetimeSettings(getApplicationContext());
        tvAlarmTime.setText(getResources().getString(R.string.alarm_time,
                Utility.getTime(datetime)));
    }

    /**
     * Turn off the alarm clock.
     */
    private void turnOffAlarm() {
        stopAction();
        // Delete the settings if the Snooze button has not been pressed.
        if (!isSnoozeAlarm) {
            AlarmPreference.removeDatetimeSettings(getApplicationContext());
        }
        finish();
    }

    /**
     * SStop the action of music playback, animation and turn off vibration.
     */
    private void stopAction() {
        mediaPlayer.stop();
        vibrator.cancel();
        animationAlarm.stop();
    }

    /**
     * Snooze the alarm for another time.
     */
    private void snoozeAlarm() {
        isSnoozeAlarm = true;
        stopAction();
        long newAlarmDatetime = AlarmPreference.getDatetimeSettings(getApplicationContext())
                + DateUtils.MINUTE_IN_MILLIS * SNOOZE_PERIOD_IN_MINUTES;
        AlarmPreference.saveDatetimeSettings(getApplicationContext(), newAlarmDatetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        startService(AlarmIntentService.newIntent(getApplicationContext(), newAlarmDatetime));
        finish();
    }
}