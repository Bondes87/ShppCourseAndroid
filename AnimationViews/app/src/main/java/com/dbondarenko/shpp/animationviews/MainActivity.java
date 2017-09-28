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
            textViewStickerA.animate()
                    .setDuration(2000)
                    .setInterpolator(new LinearInterpolator())
                    .x(xValue / 2)
                    .y(yValue)
                    .rotationX(275).
                    rotationY(360);
        }
    }

    @OnLongClick(R.id.constraintLayoutBoard)
    public boolean onViewLongClicked() {
        Log.d(LOG_TAG, "onViewLongClicked()");
        if (isStickersReturn) {
            isStickersReturn = false;
            textViewStickerA.animate()
                    .setDuration(1000)
                    .setInterpolator(new LinearInterpolator())
                    .translationX(0)
                    .translationY(0)
                    .rotationX(0).
                    rotationY(-360);
        }
        return true;
    }
}
