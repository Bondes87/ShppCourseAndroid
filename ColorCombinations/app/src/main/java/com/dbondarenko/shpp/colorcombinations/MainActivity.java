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
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.oneFragment:
                item.setChecked(true);
                setFragmentsVisibility(View.GONE, View.GONE, View.VISIBLE);
                return true;
            case R.id.twoFragments:
                item.setChecked(true);
                setFragmentsVisibility(View.VISIBLE, View.VISIBLE, View.GONE);
                return true;
            case R.id.threeFragments:
                item.setChecked(true);
                setFragmentsVisibility(View.VISIBLE, View.VISIBLE, View.VISIBLE);
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

    private void setFragmentsVisibility(int firstFragmentVisibility, int secondFragmentVisibility,
                                        int thirdFragmentVisibility) {
        topLeftColorRectangleFragment.setRectangleVisibility(firstFragmentVisibility);
        topRightColorRectangleFragment.setRectangleVisibility(secondFragmentVisibility);
        bottomColorRectangleFragment.setRectangleVisibility(thirdFragmentVisibility);
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