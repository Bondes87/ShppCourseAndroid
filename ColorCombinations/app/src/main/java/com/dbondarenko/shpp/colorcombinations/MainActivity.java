package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    private TopLeftColorRectangleFragment topLeftColorRectangleFragment;
    private TopRightColorRectangleFragment topRightColorRectangleFragment;
    private BottomColorRectangleFragment bottomColorRectangleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        findFragments();
    }

    private void findFragments() {
        topLeftColorRectangleFragment = (TopLeftColorRectangleFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentTopLeft);
        topRightColorRectangleFragment = (TopRightColorRectangleFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentTopRight);
        bottomColorRectangleFragment = (BottomColorRectangleFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentBottom);
    }

}