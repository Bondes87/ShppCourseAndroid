package com.dbondarenko.shpp.animationviews;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.constraintLayoutBoard)
    ConstraintLayout mConstraintLayoutBoard;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.constraintLayoutBoard)
    public void onViewClicked() {
        textViewStickerA.setText("0");
    }

    @OnLongClick(R.id.constraintLayoutBoard)
    public boolean onViewLongClicked() {
        textViewStickerA.setText("1");
        return true;
    }
}
