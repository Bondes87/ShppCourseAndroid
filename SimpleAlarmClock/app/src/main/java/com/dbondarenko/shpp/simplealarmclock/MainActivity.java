package com.dbondarenko.shpp.simplealarmclock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "main_activity";

    private TimePicker timePicker;
    private TextView tvAlarmTime;
    private Button bTurnOn;
    private Button bCancel;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonTurnOn:
                bTurnOn.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                turnOnAlarmClock();
                break;

            case R.id.buttonCancel:
                bCancel.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                cancelAlarmClock();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        initViews();
        long datetime = AlarmPreference.getDatetimeSettings(getApplicationContext());
        if (datetime != -1) {
            showAlarmTime(Utility.getTime(datetime));
        }
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        tvAlarmTime = (TextView) findViewById(R.id.textViewAlarmTime);
        bTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        bCancel = (Button) findViewById(R.id.buttonCancel);

        bTurnOn.setOnClickListener(this);
        bCancel.setOnClickListener(this);
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
        showAlarmTime(hour + ":" + (minute < 10 ? "0" + minute : minute));
        long alarmDatetime = getDatetime(hour, minute);
        AlarmPreference.setDatetimeSettings(getApplicationContext(), alarmDatetime);
        stopService(new Intent(getApplicationContext(), AlarmIntentService.class));
        startService(AlarmIntentService.newIntent(getApplicationContext(), alarmDatetime));
    }

    private void showAlarmTime(String time) {
        tvAlarmTime.setText(getResources().getString(R.string.turn_on, time));
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
        AlarmPreference.removeDatetimeSettings(getApplicationContext());
    }
}
