package com.dbondarenko.shpp.cookislands.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dbondarenko.shpp.cookislands.R;
import com.dbondarenko.shpp.cookislands.adapters.ContentFragmentPagerAdapter;
import com.dbondarenko.shpp.cookislands.utils.SharedPreferencesManager;
import com.dbondarenko.shpp.cookislands.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: ContentActivity.java
 * Activity which shows information about the island of Cook.
 * Created by Dmitro Bondarenko on 23.10.2017.
 */
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
        setViewPagerContentSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_content_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemLogOut) {
            SharedPreferencesManager.getSharedPreferencesManager()
                    .removeInformationAboutLogin(getApplicationContext());
            runMainActivity();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Get the intent to run ContentActivity.
     *
     * @param context  The Context of the application package implementing this class.
     * @return the intent to run ContentActivity.
     */
    public static Intent newInstance(Context context) {
        Log.d(LOG_TAG, "runContentActivity()");
        Util.checkForNull(context);
        Intent intentToStartContentActivity = new Intent(context, ContentActivity.class);
        intentToStartContentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intentToStartContentActivity;
    }

    /**
     * Set ViewPager settings for displaying information. Set the adapter
     * and the number of the item.
     */
    private void setViewPagerContentSettings() {
        viewPagerContent.setAdapter(new ContentFragmentPagerAdapter(
                getApplicationContext(), getSupportFragmentManager()));
        viewPagerContent.setCurrentItem(SharedPreferencesManager
                .getSharedPreferencesManager()
                .getUserIslandId(getApplicationContext()) - 1);
    }

    /**
     * Run the MainActivity.
     */
    private void runMainActivity() {
        Log.d(LOG_TAG, "runMainActivity()");
        Intent intentToStartNewActivity = new Intent(
                getApplicationContext(), MainActivity.class);
        intentToStartNewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToStartNewActivity);
    }
}