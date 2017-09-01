package com.dbondarenko.shpp.simplealarmclock;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "result_activity";

    private MediaPlayer mediaPlayer;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCancel) {
            Log.d(LOG_TAG, "stop Alarm");
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        playAlarmSound();
    }

    private void playAlarmSound() {
        Log.d(LOG_TAG, "playAlarmSound()");
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void initViews() {
        setContentView(R.layout.activity_results);
        ImageView ivAlarm = (ImageView) findViewById(R.id.imageViewAlarm);
        TextView tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        Button bTurnOff = (Button) findViewById(R.id.buttonCancel);

        bTurnOff.setOnClickListener(this);
    }
}
