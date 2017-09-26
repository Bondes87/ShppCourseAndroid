package com.dbondarenko.shpp.animationviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * File: ColorfulView.java
 * Created by Dmitro Bondarenko on 25.09.2017.
 */
public class ColorfulView extends View {

    public static final int PERION_OF_TIME_BETWEEN_CHANGE_OF_COLOR = 500;
    public static final int DELAY_OF_TIME_BEFORE_BEGINNING_CHANGE_OF_COLOR = 0;
    private Paint paint;
    private Timer timer;
    private Handler handler;

    public ColorfulView(Context context) {
        super(context);
        init();
    }

    public ColorfulView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorfulView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColorfulView(Context context, @Nullable AttributeSet attrs,
                        int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        super.onDraw(canvas);
        if (timer == null) {
            timer = new Timer();
            timer.schedule(getTimerTaskRedrawTheView(),
                    DELAY_OF_TIME_BEFORE_BEGINNING_CHANGE_OF_COLOR,
                    PERION_OF_TIME_BETWEEN_CHANGE_OF_COLOR);
        }
        paint.setARGB(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber());
        canvas.drawPaint(paint);
    }

    private void init() {
        paint = new Paint();
        handler = new Handler();
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(256);
    }

    @NonNull
    private TimerTask getTimerTaskRedrawTheView() {
        return new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
            }
        };
    }
}