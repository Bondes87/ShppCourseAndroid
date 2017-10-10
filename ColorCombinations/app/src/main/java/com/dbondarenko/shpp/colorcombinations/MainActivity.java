package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    private TopLeftColorRectangleFragment topLeftColorRectangleFragment;
    private TopRightColorRectangleFragment topRightColorRectangleFragment;
    private BottomColorRectangleFragment bottomColorRectangleFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.oneFragment:
                item.setChecked(true);
                topLeftColorRectangleFragment.setRectangleVisibility(View.GONE);
                topRightColorRectangleFragment.setRectangleVisibility(View.GONE);
                bottomColorRectangleFragment.setRectangleVisibility(View.VISIBLE);
                return true;
            case R.id.twoFragments:
                item.setChecked(true);
                topLeftColorRectangleFragment.setRectangleVisibility(View.VISIBLE);
                topRightColorRectangleFragment.setRectangleVisibility(View.VISIBLE);
                bottomColorRectangleFragment.setRectangleVisibility(View.GONE);
                return true;
            case R.id.threeFragments:
                item.setChecked(true);
                topLeftColorRectangleFragment.setRectangleVisibility(View.VISIBLE);
                topRightColorRectangleFragment.setRectangleVisibility(View.VISIBLE);
                bottomColorRectangleFragment.setRectangleVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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