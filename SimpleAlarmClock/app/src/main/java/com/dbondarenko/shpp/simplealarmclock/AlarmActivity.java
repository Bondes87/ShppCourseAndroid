package com.dbondarenko.shpp.simplealarmclock;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: AlarmActivity.java
 * The activity in which  is stop the alarm.
 * Created by Dmitro Bondarenko on 30.08.2017.
 */
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "alarm_activity";

    @BindView(R.id.imageViewAlarm)
    ImageView imageViewAlarm;
    @BindView(R.id.textViewAlarmTime)
    TextView textViewAlarmTime;
    @BindView(R.id.buttonTurnOff)
    Button buttonTurnOff;
    @BindView(R.id.buttonSnooze)
    Button buttonSnooze;

    private AnimationDrawable animationDrawableAlarm;

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
        ButterKnife.bind(this);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        setUpScreen();
        startAlarmAnimation();
        startPlaySoundAndVibration();
        showAlarmTime();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        super.onDestroy();
        stopAnimation();
    }

    /**
     * Stop the animation.
     */
    private void stopAnimation() {
        animationDrawableAlarm.stop();
    }

    /**
     * Turn off the alarm clock.
     */
    private void turnOffAlarm() {
        stopAnimation();
        // Delete the settings if the Snooze button has not been pressed.
        if (!isSnoozeAlarm) {
            AlarmPreference.getAlarmPreference()
                    .removeDatetimeSettings(getApplicationContext());
        }
        finish();
    }

    /**
     * Stop the service in which play sound and the included vibration.
     */
    private void stopPlaySoundAndVibration() {
        stopService(AlarmPlaySoundIntentService.newIntent(getApplicationContext()));
    }

    /**
     * Initialize views and set listeners.
     */
    private void initViews() {
        imageViewAlarm.setBackgroundResource(R.drawable.alarm_animation);
        buttonTurnOff.setOnClickListener(this);
        buttonSnooze.setOnClickListener(this);
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
     * Play alarm sound and turn on vibration.
     */
    private void startPlaySoundAndVibration() {
        startService(AlarmPlaySoundIntentService.newIntent(getApplicationContext()));
    }


    /**
     * Show the time of the alarm on the screen.
     */
    private void showAlarmTime() {
        long datetime = AlarmPreference.getAlarmPreference()
                .getDatetimeSettings(getApplicationContext());
        Utility.checkForNegativeNumber(datetime);
        textViewAlarmTime.setText(getResources().getString(R.string.alarm_time,
                Utility.getTime(datetime)));
    }

    /**
     * Snooze the alarm for another time.
     */
    private void snoozeAlarm() {
        isSnoozeAlarm = true;
        stopAnimation();
        String snoozeSettings = AlarmPreference.getAlarmPreference()
                .getSnoozeSettings(getApplicationContext());
        Utility.checkForEmptyString(snoozeSettings);
        // Get the number of minutes for sleep.
        int snoozeTime = Integer.parseInt(snoozeSettings);
        // Create a new Datetime for the alarm.
        long newAlarmDatetime = AlarmPreference.getAlarmPreference()
                .getDatetimeSettings(getApplicationContext())
                + DateUtils.MINUTE_IN_MILLIS * snoozeTime;
        Log.d(LOG_TAG, "snoozeAlarm()" + snoozeTime);
        AlarmPreference.getAlarmPreference()
                .saveDatetimeSettings(getApplicationContext(), newAlarmDatetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        startService(AlarmIntentService.newIntent(getApplicationContext(), newAlarmDatetime));
        finish();
    }
}