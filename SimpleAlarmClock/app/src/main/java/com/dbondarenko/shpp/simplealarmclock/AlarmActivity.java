package com.dbondarenko.shpp.simplealarmclock;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "result_activity";

    private MediaPlayer mediaPlayer;
    private TextView tvAlarmTime;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCancel) {
            Log.d(LOG_TAG, "stop Alarm");
            mediaPlayer.stop();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        setUpScreen();
        playAlarmSound();
        showAlarmTime();
    }

    private void initViews() {
        setContentView(R.layout.activity_results);
        findViewById(R.id.imageViewAlarm);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        Button bTurnOff = (Button) findViewById(R.id.buttonCancel);

        bTurnOff.setOnClickListener(this);
    }

    private void setUpScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void playAlarmSound() {
        Log.d(LOG_TAG, "playAlarmSound()");
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void showAlarmTime() {
        int hour = AlarmPreference.getHour(getApplicationContext());
        int minute = AlarmPreference.getMitute(getApplicationContext());
        tvAlarmTime.setText(getResources().getString(R.string.alarm_time, String.valueOf(hour),
                minute < 10 ? "0" + minute : minute));
        AlarmPreference.clearTimeSettings(getApplicationContext());
    }
}
