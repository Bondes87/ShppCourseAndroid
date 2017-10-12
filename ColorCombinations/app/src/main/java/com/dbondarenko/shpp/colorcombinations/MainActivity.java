package com.dbondarenko.shpp.colorcombinations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    private ColorRectangleFragment colorRectangleFragmentTopLeft;
    private ColorRectangleFragment colorRectangleFragmentTopRight;
    private ColorRectangleFragment colorRectangleFragmentBottom;

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
                /*item.setChecked(true);
                setFragmentsVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                saveFragmentVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE);*/
                return true;
            case R.id.twoFragments:
                /*item.setChecked(true);
                setFragmentsVisibility(View.VISIBLE, View.VISIBLE, View.INVISIBLE);
                saveFragmentVisibility(View.VISIBLE, View.VISIBLE, View.INVISIBLE);*/
                return true;
            case R.id.threeFragments:
               /* item.setChecked(true);
                setFragmentsVisibility(View.VISIBLE, View.VISIBLE, View.VISIBLE);
                saveFragmentVisibility(View.VISIBLE, View.VISIBLE, View.VISIBLE);*/
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
        if (savedInstanceState == null) {
            initFragments();
        }
        //initFragmentVisibility();
    }

    private void initFragmentVisibility() {
        int[] fragmentsVisibility = FragmentsPreferences.getFragmentsPreferences()
                .getFragmentsVisibilitySettings(getApplicationContext());
        setFragmentsVisibility(fragmentsVisibility[0], fragmentsVisibility[1],
                fragmentsVisibility[2]);
    }

    private void setFragmentsVisibility(int firstFragmentVisibility, int secondFragmentVisibility,
                                        int thirdFragmentVisibility) {
        colorRectangleFragmentTopLeft.setRectangleVisibility(firstFragmentVisibility);
        colorRectangleFragmentTopRight.setRectangleVisibility(secondFragmentVisibility);
        colorRectangleFragmentBottom.setRectangleVisibility(thirdFragmentVisibility);
    }

    private void saveFragmentVisibility(int firstFragmentVisibility, int secondFragmentVisibility,
                                        int thirdFragmentVisibility) {
        Log.d(LOG_TAG, "saveFragmentVisibility()" + firstFragmentVisibility);
        Log.d(LOG_TAG, "saveFragmentVisibility()" + secondFragmentVisibility);
        Log.d(LOG_TAG, "saveFragmentVisibility()" + thirdFragmentVisibility);
        FragmentsPreferences.getFragmentsPreferences().
                saveFragmentsVisibilitySettings(getApplicationContext(), firstFragmentVisibility,
                        secondFragmentVisibility, thirdFragmentVisibility);
    }

    private void initFragments() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerOfTopLeftFragment, ColorRectangleFragment.newInstance(Color.RED))
                .add(R.id.containerOfTopRightFragment, ColorRectangleFragment.newInstance(Color.GREEN))
                .add(R.id.containerOfBottomFragment, ColorRectangleFragment.newInstance(Color.CYAN))
                .commit();
    }
}