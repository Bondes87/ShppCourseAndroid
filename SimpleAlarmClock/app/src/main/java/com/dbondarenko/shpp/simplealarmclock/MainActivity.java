package com.dbondarenko.shpp.simplealarmclock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "main_activity";

    private TimePicker timePicker;
    private Button bTurnOn;
    private Button bCancel;
    private TextView tvAlarmTime;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTurnOn:
                turnOnAlarmClock();
                break;
            case R.id.buttonCancel:
                cancelAlarmClock();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
    }

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
        tvAlarmTime.setText(getResources().getString(R.string.turn_on, String.valueOf(hour),
                minute < 10 ? "0" + minute : minute));
        startService(AlarmClockIntentService.newIntent(getApplicationContext(), hour, minute));
    }


    private void cancelAlarmClock() {
        Log.d(LOG_TAG, "Alarm clock cancel");
        tvAlarmTime.setText(R.string.cancel);
        stopService(new Intent(getApplicationContext(), AlarmClockIntentService.class));
    }


    private void initViews() {
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        bTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        bCancel = (Button) findViewById(R.id.buttonCancel);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);

        bTurnOn.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }
}
