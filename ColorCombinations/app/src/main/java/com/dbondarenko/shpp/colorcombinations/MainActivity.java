package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    private static final String TOP_LEFT_FRAGMENT =
            "com.dbondarenko.shpp.colorcombinations.TopLeftFragment";
    private static final String TOP_RIGHT_FRAGMENT =
            "com.dbondarenko.shpp.colorcombinations.TopRightFragment";
    private static final String BOTTOM_FRAGMENT = "" +
            "com.dbondarenko.shpp.colorcombinations.BottomFragment";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        FragmentManager fragmentManager = getSupportFragmentManager();
        ;
        switch (item.getItemId()) {
            case R.id.topLeftFragment:
                setFragmentVisibility(item, fragmentManager, TOP_LEFT_FRAGMENT);
                return true;
            case R.id.topRightFragment:
                setFragmentVisibility(item, fragmentManager, TOP_RIGHT_FRAGMENT);
                return true;
            case R.id.bottomFragment:
                setFragmentVisibility(item, fragmentManager, BOTTOM_FRAGMENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        Log.d(LOG_TAG, "onCreate() " + savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initFragments();
        }
    }

    private void setFragmentVisibility(MenuItem item, FragmentManager fragmentManager,
                                       String fragmentTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (item.isChecked()) {
            fragmentTransaction.hide(fragmentManager.findFragmentByTag(fragmentTag));
        } else {
            fragmentTransaction.show(fragmentManager.findFragmentByTag(fragmentTag));
        }
        fragmentTransaction.commit();
        item.setChecked(!item.isChecked());
    }

   /* private void initFragmentVisibility() {
        int[] fragmentsVisibility = FragmentsPreferences.getFragmentsPreferences()
                .getFragmentsVisibilitySettings(getApplicationContext());
        setFragmentsVisibility(fragmentsVisibility[0], fragmentsVisibility[1],
                fragmentsVisibility[2]);
    }*/

   /* private void setFragmentsVisibility(int firstFragmentVisibility, int secondFragmentVisibility,
                                        int thirdFragmentVisibility) {
        colorRectangleFragmentTopLeft.setRectangleVisibility(firstFragmentVisibility);
        colorRectangleFragmentTopRight.setRectangleVisibility(secondFragmentVisibility);
        colorRectangleFragmentBottom.setRectangleVisibility(thirdFragmentVisibility);
    }
*/
   /* private void saveFragmentVisibility(int firstFragmentVisibility, int secondFragmentVisibility,
                                        int thirdFragmentVisibility) {
        Log.d(LOG_TAG, "saveFragmentVisibility()" + firstFragmentVisibility);
        Log.d(LOG_TAG, "saveFragmentVisibility()" + secondFragmentVisibility);
        Log.d(LOG_TAG, "saveFragmentVisibility()" + thirdFragmentVisibility);
        FragmentsPreferences.getFragmentsPreferences().
                saveFragmentsVisibilitySettings(getApplicationContext(), firstFragmentVisibility,
                        secondFragmentVisibility, thirdFragmentVisibility);
    }*/

    private void initFragments() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerOfTopLeftFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getAvailableColor(TOP_LEFT_FRAGMENT).getValueColor()),
                        TOP_LEFT_FRAGMENT)
                .add(R.id.containerOfTopRightFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getAvailableColor(TOP_RIGHT_FRAGMENT).getValueColor()),
                        TOP_RIGHT_FRAGMENT)
                .add(R.id.containerOfBottomFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getAvailableColor(BOTTOM_FRAGMENT).getValueColor()),
                        BOTTOM_FRAGMENT)
                .commit();
    }
}