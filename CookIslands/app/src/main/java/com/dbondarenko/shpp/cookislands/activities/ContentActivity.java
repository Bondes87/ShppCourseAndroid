package com.dbondarenko.shpp.cookislands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.R;

public class ContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = ContentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_content);
    }
}