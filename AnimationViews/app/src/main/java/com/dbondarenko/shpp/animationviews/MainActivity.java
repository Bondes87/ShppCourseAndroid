package com.dbondarenko.shpp.animationviews;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    public static final int ROTATION_X_FOR_FALL = 275;
    public static final int ROTATION_Y_FOR_FALL = 360;
    public static final int ROTATION_X_FOR_RETURN = 0;
    public static final int ROTATION_Y_FOR_RETURN = -360;
    public static final int TRANSLATION_X_FOR_RETURN = 0;
    public static final int TRANSLATION_Y_FOR_RETURN = 0;
    private static final int ONE_SECOND = 1000;
    public static final int DURATION_OF_RETURN_ANIMATION = ONE_SECOND;
    private static final String LOG_TAG = "main_activity";

    @BindView(R.id.constraintLayoutBoard)
    ConstraintLayout constraintLayoutBoard;
    @BindView(R.id.textViewStickerA)
    TextView textViewStickerA;
    @BindView(R.id.textViewStickerB)
    TextView textViewStickerB;
    @BindView(R.id.textViewStickerC)
    TextView textViewStickerC;
    @BindView(R.id.textViewStickerD)
    TextView textViewStickerD;
    @BindView(R.id.textViewStickerE)
    TextView textViewStickerE;
    @BindView(R.id.textViewStickerF)
    TextView textViewStickerF;
    @BindView(R.id.colorfulViewSticker)
    ColorfulView colorfulViewSticker;

    private boolean isStickersReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.constraintLayoutBoard)
    public void onViewClicked() {
        Log.d(LOG_TAG, "onViewClicked()");
        if (!isStickersReturn) {
            isStickersReturn = true;
            runOfFallAnimation();
        }
    }

    @OnLongClick(R.id.constraintLayoutBoard)
    public boolean onViewLongClicked() {
        Log.d(LOG_TAG, "onViewLongClicked()");
        if (isStickersReturn) {
            isStickersReturn = false;
            runOfReturnAnimation();
        }
        return true;
    }

    private float getValueOfX() {
        int maxValueOfX = constraintLayoutBoard.getWidth() - constraintLayoutBoard.getPaddingEnd()
                - textViewStickerA.getWidth();
        int minValueOfX = constraintLayoutBoard.getPaddingStart();
        return minValueOfX + Utility.getRandomNumber(maxValueOfX);
    }

    private float getValueOfY() {
        return constraintLayoutBoard.getHeight()
                - constraintLayoutBoard.getPaddingBottom()
                - textViewStickerA.getHeight();
    }

    private long getValueOfDuration() {
        return ONE_SECOND + Utility.getRandomNumber(4 * ONE_SECOND);
    }

    private void runOfFallAnimation() {
        for (int i = 0; i < constraintLayoutBoard.getChildCount(); i++) {
            constraintLayoutBoard.getChildAt(i).animate()
                    .setDuration(getValueOfDuration())
                    .setInterpolator(new LinearInterpolator())
                    .x(getValueOfX())
                    .y(getValueOfY())
                    .rotationX(ROTATION_X_FOR_FALL).
                    rotationY(ROTATION_Y_FOR_FALL);
        }
    }

    private void runOfReturnAnimation() {
        for (int i = 0; i < constraintLayoutBoard.getChildCount(); i++) {
            constraintLayoutBoard.getChildAt(i).animate()
                    .setDuration(DURATION_OF_RETURN_ANIMATION)
                    .setInterpolator(new LinearInterpolator())
                    .translationX(TRANSLATION_X_FOR_RETURN)
                    .translationY(TRANSLATION_Y_FOR_RETURN)
                    .rotationX(ROTATION_X_FOR_RETURN).
                    rotationY(ROTATION_Y_FOR_RETURN);
        }
    }
}