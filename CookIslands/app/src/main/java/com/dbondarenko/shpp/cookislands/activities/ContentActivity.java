package com.dbondarenko.shpp.cookislands.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.adapters.ContentFragmentPagerAdapter;
import com.dbondarenko.shpp.cookislands.utils.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity {

    private static final String LOG_TAG = ContentActivity.class.getSimpleName();
    @BindView(R.id.viewPagerContent)
    ViewPager viewPagerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        viewPagerContent.setAdapter(getPagerAdapter());
        viewPagerContent.setCurrentItem(SharedPreferencesManager.getSharedPreferencesManager()
                .getUserIslandId(getApplicationContext()) - 1);
    }

    private ContentFragmentPagerAdapter getPagerAdapter() {
        return new ContentFragmentPagerAdapter(getSupportFragmentManager(),
                getApplicationContext());
    }
}