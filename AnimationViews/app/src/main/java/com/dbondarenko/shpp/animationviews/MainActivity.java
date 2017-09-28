package com.dbondarenko.shpp.animationviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
        int xValue = constraintLayoutBoard.getWidth() - constraintLayoutBoard.getPaddingEnd()
                - textViewStickerA.getWidth();
        int yValue = constraintLayoutBoard.getHeight() - constraintLayoutBoard.getPaddingBottom()
                - textViewStickerA.getHeight();
        if (!isStickersReturn ) {
            isStickersReturn = true;
            ObjectAnimator moveX = ObjectAnimator.ofFloat(textViewStickerA, "x", xValue / 2);
            ObjectAnimator moveY = ObjectAnimator.ofFloat(textViewStickerA, "y", yValue);
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(textViewStickerA, "rotationX", 275);
            ObjectAnimator rotationY = ObjectAnimator.ofFloat(textViewStickerA, "rotationY", 360);
            AnimatorSet fallAnimation = new AnimatorSet();
            fallAnimation.setInterpolator(new LinearInterpolator());
            fallAnimation.setDuration(2000);
            fallAnimation.playTogether(moveX, moveY, rotationX, rotationY);
            fallAnimation.start();
        }
    }

    @OnLongClick(R.id.constraintLayoutBoard)
    public boolean onViewLongClicked() {
        Log.d(LOG_TAG, "onViewLongClicked()");
        if (isStickersReturn) {
            isStickersReturn = false;
            ObjectAnimator translationX = ObjectAnimator.ofFloat(textViewStickerA, "translationX", 0);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(textViewStickerA, "translationY", 0);
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(textViewStickerA, "rotationX", 0);
            ObjectAnimator rotationY = ObjectAnimator.ofFloat(textViewStickerA, "rotationY", -360);
            AnimatorSet returnAnimation = new AnimatorSet();
            returnAnimation.playTogether(translationX, translationY, rotationX, rotationY);
            returnAnimation.setInterpolator(new LinearInterpolator());
            returnAnimation.setDuration(1000);
            returnAnimation.start();
        }
        return true;
    }
}
