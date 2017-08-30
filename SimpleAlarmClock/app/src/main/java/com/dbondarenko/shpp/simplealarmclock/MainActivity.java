package com.dbondarenko.shpp.simplealarmclock;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "simple_alarm_clock";

    private TimePicker timePicker;
    private Button bTurnOn;
    private Button bTurnOff;
    private TextView tvAlarmTime;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTurnOn:
                turnOnAlarmClock();
                break;
            case R.id.buttonTurnOff:
                turnOffAlarmClock();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }

    private void turnOffAlarmClock() {
        Log.d(LOG_TAG, "Alarm clock turn off");
        tvAlarmTime.setText(R.string.turn_off);
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
        tvAlarmTime.clearComposingText();
        /*long time = Calendar.getInstance().getTimeInMillis();
        DateFormat simpleDateFormat = new SimpleDateFormat("HH:MM", Locale.US);
        String date = simpleDateFormat.format(time);*/
        tvAlarmTime.setText(getResources().getString(R.string.turn_on, String.valueOf(hour),
                minute < 10 ? "0" + minute : minute));
        //System.currentTimeMillis();
        //SystemClock.currentThreadTimeMillis();
        //SimpleDateFormat("HH:MM", Locale.getDefault()).format(Calendar.getInstance().getTimeInMillis())
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        bTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        bTurnOff = (Button) findViewById(R.id.buttonTurnOff);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);

        bTurnOff.setOnClickListener(this);
        bTurnOn.setOnClickListener(this);
    }
}
