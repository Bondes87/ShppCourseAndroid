package com.dbondarenko.shpp.simplealarmclock;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "result_activity";

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private TextView tvAlarmTime;
    private Button bTurnOff;

    private boolean isScreenOn;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCancel) {
            Log.d(LOG_TAG, "stop Alarm");
            bTurnOff.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            turnOffAlarm();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        determineScreenStatus();
        setUpScreen();
        playAlarmSound();
        turnOnVibration();
        showAlarmTime();
        AlarmPreference.removeDatetimeSettings(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onStop()");
        Log.d(LOG_TAG, "isScreenOn = " + isScreenOn);
        if (isScreenOn) {
            turnOffAlarm();
        } else {
            isScreenOn = true;
        }
    }

    private void turnOffAlarm() {
        mediaPlayer.stop();
        vibrator.cancel();
        finish();
    }

    private void turnOnVibration() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            long[] pattern = {0, 400, 800};
            vibrator.vibrate(pattern, 0);
        }
    }

    private void initViews() {
        setContentView(R.layout.activity_alarm);
        findViewById(R.id.imageViewAlarm);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        bTurnOff = (Button) findViewById(R.id.buttonCancel);

        bTurnOff.setOnClickListener(this);
        bTurnOff.setHapticFeedbackEnabled(true);
    }

    private void determineScreenStatus() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();
        }
        Log.d(LOG_TAG, "isScreenOn = " + isScreenOn);
    }

    private void setUpScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void playAlarmSound() {
        Log.d(LOG_TAG, "playAlarmSound()");
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void showAlarmTime() {
        long datetime = AlarmPreference.getDatetimeSettings(getApplicationContext());
        tvAlarmTime.setText(getResources().getString(R.string.alarm_time,
                Utility.getTime(datetime)));
    }
}