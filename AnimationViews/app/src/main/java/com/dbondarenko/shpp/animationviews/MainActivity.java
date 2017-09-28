package com.dbondarenko.shpp.animationviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    public static final String PROPERTY_X = "x";
    public static final String PROPERTY_Y = "y";
    public static final String PROPERTY_ROTATION_X = "rotationX";
    public static final String PROPERTY_ROTATION_Y = "rotationY";
    public static final String PROPERTY_TRANSLATION_X = "translationX";
    public static final String PROPERTY_TRANSLATION_Y = "translationY";
    private static final int ONE_SECOND = 1000;
    public static final int DURATION_OF_RETURN_ANIMATION = ONE_SECOND;
    private static final String LOG_TAG = "main_activity";
    @BindView(R.id.constraintLayoutBoard)
    ConstraintLayout constraintLayoutBoard;
    @BindView(R.id.textViewStickerA)
    TextView textViewStickerA;
    /*@BindView(R.id.textViewStickerB)
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
    ColorfulView colorfulViewSticker;*/

    private boolean isStickersReturn;
    private AnimatorSet slowestAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        slowestAnimation = new AnimatorSet();
    }

    @OnClick(R.id.constraintLayoutBoard)
    public void onViewClicked() {
        Log.d(LOG_TAG, "onViewClicked()");
        if (!isStickersReturn && !slowestAnimation.isRunning()) {
            isStickersReturn = true;
            runOfFallAnimation();
        }
    }

    @OnLongClick(R.id.constraintLayoutBoard)
    public boolean onViewLongClicked() {
        Log.d(LOG_TAG, "onViewLongClicked()");
        if (isStickersReturn && !slowestAnimation.isRunning()) {
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
        long maxValueOfDuration = 0;
        for (int i = 0; i < constraintLayoutBoard.getChildCount(); i++) {
            long currentValueOfDuration = getValueOfDuration();
            View sticker = constraintLayoutBoard.getChildAt(i);
            AnimatorSet fallAnimation = new AnimatorSet();
            fallAnimation.setInterpolator(new LinearInterpolator());
            fallAnimation.setDuration(currentValueOfDuration);
            fallAnimation.playTogether(ObjectAnimator.ofFloat(sticker, PROPERTY_X, getValueOfX()),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_Y, getValueOfY()),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_ROTATION_X, ROTATION_X_FOR_FALL),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_ROTATION_Y, ROTATION_Y_FOR_FALL));
            fallAnimation.start();
            if (currentValueOfDuration > maxValueOfDuration) {
                maxValueOfDuration = currentValueOfDuration;
                slowestAnimation = fallAnimation;
            }
        }
    }

    private void runOfReturnAnimation() {
        for (int i = 0; i < constraintLayoutBoard.getChildCount(); i++) {
            View sticker = constraintLayoutBoard.getChildAt(i);
            AnimatorSet returnAnimation = new AnimatorSet();
            returnAnimation.setInterpolator(new LinearInterpolator());
            returnAnimation.setDuration(DURATION_OF_RETURN_ANIMATION);
            returnAnimation.playTogether(
                    ObjectAnimator.ofFloat(sticker, PROPERTY_TRANSLATION_X, TRANSLATION_X_FOR_RETURN),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_TRANSLATION_Y, TRANSLATION_Y_FOR_RETURN),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_ROTATION_X, ROTATION_X_FOR_RETURN),
                    ObjectAnimator.ofFloat(sticker, PROPERTY_ROTATION_Y, ROTATION_Y_FOR_RETURN));
            returnAnimation.start();
            if (i == constraintLayoutBoard.getChildCount() - 1) {
                slowestAnimation = returnAnimation;
            }
        }
    }
}