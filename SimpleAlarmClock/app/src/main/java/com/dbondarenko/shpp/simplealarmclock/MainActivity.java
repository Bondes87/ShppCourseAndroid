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

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "main_activity";

    private TimePicker timePicker;
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
        showAlarmTime(hour, minute);
        long alarmDatetime = getDatetime(hour, minute);
        AlarmPreference.setDatetimeSettings(getApplicationContext(), alarmDatetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        startService(AlarmIntentService.newIntent(getApplicationContext(), alarmDatetime));
    }

    private void showAlarmTime(int hour, int minute) {
        tvAlarmTime.setText(getResources().getString(R.string.turn_on, String.valueOf(hour),
                minute < 10 ? "0" + minute : minute));
    }

    private long getDatetime(int alarmHour, int alarmMinute) {
        Calendar calendar = Calendar.getInstance();
        int alarmDay = calendar.get(Calendar.DAY_OF_YEAR);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        Log.d(LOG_TAG, "current datetime: " + calendar.getTimeInMillis());
        if (currentHour > alarmHour) {
            alarmDay++;
        } else if (currentHour == alarmHour) {
            if (currentMinute > alarmMinute) {
                alarmDay++;
            }
        }
        calendar.set(Calendar.DAY_OF_YEAR, alarmDay);
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 0);
        Log.d(LOG_TAG, "alarm datetime: " + calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    private void cancelAlarmClock() {
        Log.d(LOG_TAG, "Alarm clock cancel");
        tvAlarmTime.setText(R.string.cancel);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        AlarmPreference.clearDatetimeSettings(getApplicationContext());
    }


    private void initViews() {
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        Button bTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        Button bCancel = (Button) findViewById(R.id.buttonCancel);

        bTurnOn.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }
}
