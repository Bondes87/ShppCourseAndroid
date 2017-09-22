package com.dbondarenko.shpp.simplealarmclock;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

/**
 *
 */
public class AlarmPlaySoundIntentService extends IntentService {
    private static final String LOG_TAG = "sound_service";
    private static final String ACTION_ALARM_SOUND_PLAYBACK =
            "com.dbondarenko.shpp.simplealarmclock.action.AlarmSoundPlayback";
    private static final int NOTIFICATION_ID = 1;
    private boolean isAlarmTurnOff;
    private MediaPlayer mediaPlayerAlarm;
    private Vibrator vibratorAlarm;

    public AlarmPlaySoundIntentService() {
        super("AlarmPlaySoundIntentService");
    }

    public static Intent newIntent(Context context) {
        if (context == null) {
            Log.d(LOG_TAG, "newIntent(): the context is equal to null");
        } else {
            Intent intentToStartAlarmService = new Intent(context, AlarmPlaySoundIntentService.class);
            intentToStartAlarmService.setAction(ACTION_ALARM_SOUND_PLAYBACK);
            return intentToStartAlarmService;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Set the value of the dlodlo variable to true to cancel the alarm (stop the service).
        isAlarmTurnOff = true;
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ALARM_SOUND_PLAYBACK.equals(action)) {
                startForegroundService();
                playAlarmSound();
                turnOnVibration();
                while (true) {
                    if (isAlarmTurnOff) {
                        mediaPlayerAlarm.stop();
                        vibratorAlarm.cancel();
                        stopForeground(true);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Play alarm sound.
     */
    private void playAlarmSound() {
        // Log.d(LOG_TAG, "playAlarmSound()");
        String filePath = AlarmPreference.getRingtoneSettings(getApplicationContext());
        // If the path to the file is empty, then play the default melody,
        // otherwise play the selected melody
        if (TextUtils.isEmpty(filePath)) {
            mediaPlayerAlarm = MediaPlayer.create(this, R.raw.alarm_sound);
        } else {
            //  Log.d(LOG_TAG, "playAlarmSound()" + filePath);
            mediaPlayerAlarm = new MediaPlayer();
            mediaPlayerAlarm.setAudioStreamType(AudioManager.STREAM_ALARM);
            try {
                mediaPlayerAlarm.setDataSource(filePath);
                mediaPlayerAlarm.prepare();
            } catch (IOException e) {
                //    Log.d(LOG_TAG, "playAlarmSound()" + e);
                e.printStackTrace();
            }
        }
        // Set  repeat the sound.
        mediaPlayerAlarm.setLooping(true);
        mediaPlayerAlarm.start();
    }

    /**
     * Turn on vibration.
     */
    private void turnOnVibration() {
        vibratorAlarm = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // If there is vibration on this device, then run it.
        if (vibratorAlarm.hasVibrator()) {
            long[] pattern = {0, 400, 800};
            vibratorAlarm.vibrate(pattern, 0);
        }
    }

    private void startForegroundService() {
        Log.d(LOG_TAG, "startForegroundService()");
        PendingIntent pendingIntent = getPendingIntent();
        //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Alarm clock")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                //.setAutoCancel(false)
                .setContentIntent(pendingIntent)
                //.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                //.setOngoing(true)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    private PendingIntent getPendingIntent() {
        Intent notificationIntent = new Intent(getApplicationContext(), AlarmActivity.class);
        // Set this action as the beginning of a new task in this history stack.
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, notificationIntent, 0);
    }
}
