package com.dbondarenko.shpp.animationviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * File: ColorfulView.java
 * The class in which a colorful view is created.
 * Created by Dmitro Bondarenko on 25.09.2017.
 */
public class ColorfulView extends View {

    private static final String LOG_TAG = "colorful_view";
    // The time period between color changes in the view.
    private static final int PERIOD_OF_TIME_BETWEEN_CHANGE_OF_COLOR = 500;
    // The time delay before begins of color change.
    private static final int DELAY_OF_TIME_BEFORE_BEGINNING_CHANGE_OF_COLOR = 0;
    // The amount of intensity values that can be used in one of the four
    // components to compose color.
    private static final int AMOUNT_OF_COLOR_INTENSIVES = 256;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorfulView(Context context, @Nullable AttributeSet attrs,
                        int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(LOG_TAG, "onDraw()");
        Utility.checkForNull(canvas);
        super.onDraw(canvas);
        // If the timer does not exist, then create a timer and run it.
        if (timer == null) {
            timer = new Timer();
            timer.schedule(getTimerTaskRedrawView(),
                    DELAY_OF_TIME_BEFORE_BEGINNING_CHANGE_OF_COLOR,
                    PERIOD_OF_TIME_BETWEEN_CHANGE_OF_COLOR);
        }
        // Set the color to draw view.
        paint.setARGB(Utility.getRandomNumber(AMOUNT_OF_COLOR_INTENSIVES),
                Utility.getRandomNumber(AMOUNT_OF_COLOR_INTENSIVES),
                Utility.getRandomNumber(AMOUNT_OF_COLOR_INTENSIVES),
                Utility.getRandomNumber(AMOUNT_OF_COLOR_INTENSIVES));
        canvas.drawPaint(paint);
    }

    /**
     * Initialize objects Paint and Handler.
     */
    private void init() {
        paint = new Paint();
        handler = new Handler();
    }

    /**
     * Return the TimerTask to redraw the view.
     *
     * @return The TimerTask to redraw the view.
     */
    private TimerTask getTimerTaskRedrawView() {
        Log.d(LOG_TAG, "getTimerTaskRedrawView()");
        return new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> invalidate());
            }
        };
    }
}