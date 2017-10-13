package com.dbondarenko.shpp.colorcombinations;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    private static final String TOP_LEFT_FRAGMENT =
            "com.dbondarenko.shpp.colorcombinations.TopLeftFragment";
    private static final String TOP_RIGHT_FRAGMENT =
            "com.dbondarenko.shpp.colorcombinations.TopRightFragment";
    private static final String BOTTOM_FRAGMENT = "" +
            "com.dbondarenko.shpp.colorcombinations.BottomFragment";

    @BindView(R.id.containerOfTopLeftFragment)
    FrameLayout containerOfTopLeftFragment;
    @BindView(R.id.containerOfTopRightFragment)
    FrameLayout containerOfTopRightFragment;
    @BindView(R.id.containerOfBottomFragment)
    FrameLayout containerOfBottomFragment;
    private View viewSelected;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        setCheckboxesInOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected()");
        FragmentManager fragmentManager = getSupportFragmentManager();
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(LOG_TAG, "onCreateContextMenu()");
        viewSelected = v;
        ArrayList<Color> arrayListColors = ColorsManager.getColorsManager().getAvailableColors();
        for (int i = 0; i < arrayListColors.size(); i++) {
            Color colorOfContextMenuItem = arrayListColors.get(i);
            menu.add(Menu.NONE, i, Menu.NONE, getContextMenuItem(colorOfContextMenuItem));
        }
        menu.setHeaderTitle("Select the color");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onContextItemSelected() = ");
        int itemId = item.getItemId();
        if (viewSelected.equals(containerOfTopLeftFragment)) {
            changeFragmentColor(itemId, TOP_LEFT_FRAGMENT);
            return true;
        }
        if (viewSelected.equals(containerOfTopRightFragment)) {
            changeFragmentColor(itemId, TOP_RIGHT_FRAGMENT);
            return true;
        }
        if (viewSelected.equals(containerOfBottomFragment)) {
            changeFragmentColor(itemId, BOTTOM_FRAGMENT);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            initFragments();
        }
        registerViewsForContextMenu();
    }

    private void setCheckboxesInOptionsMenu(Menu menu) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).isHidden()) {
                menu.getItem(i).setChecked(false);
            } else {
                menu.getItem(i).setChecked(true);
            }
        }
    }

    private void registerViewsForContextMenu() {
        registerForContextMenu(containerOfBottomFragment);
        registerForContextMenu(containerOfTopLeftFragment);
        registerForContextMenu(containerOfTopRightFragment);
    }

    private void changeFragmentColor(int colorIndex, String fragmentTag) {
        ColorFragment selectedColorFragment = (ColorFragment) getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
        int newColorValue = ColorsManager.getColorsManager()
                .getAvailableColor(colorIndex, fragmentTag)
                .getValueColor();
        selectedColorFragment.setColorValue(newColorValue);
    }

    private Spannable getContextMenuItem(Color color) {
        Spannable spannable = new SpannableString("  - " + color.getNameColor());
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color.getValueColor());
        circle.setIntrinsicHeight(120);
        circle.setIntrinsicWidth(120);
        circle.setBounds(0, 0, 120, 120);
        spannable.setSpan(new ImageSpan(circle), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private void setFragmentVisibility(MenuItem item, FragmentManager fragmentManager,
                                       String fragmentTag) {
        ColorFragment selectedColorFragment =
                (ColorFragment) fragmentManager.findFragmentByTag(fragmentTag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (item.isChecked()) {
            fragmentTransaction.hide(selectedColorFragment);
            ColorsManager.getColorsManager().setAvailableColor(fragmentTag);
        } else {
            int colorValue = ColorsManager.getColorsManager().getUsedColor(fragmentTag)
                    .getValueColor();
            selectedColorFragment.setColorValue(colorValue);
            fragmentTransaction.show(selectedColorFragment);
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
                                getRandomAvailableColor(TOP_LEFT_FRAGMENT).getValueColor()),
                        TOP_LEFT_FRAGMENT)
                .add(R.id.containerOfTopRightFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getRandomAvailableColor(TOP_RIGHT_FRAGMENT).getValueColor()),
                        TOP_RIGHT_FRAGMENT)
                .add(R.id.containerOfBottomFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getRandomAvailableColor(BOTTOM_FRAGMENT).getValueColor()),
                        BOTTOM_FRAGMENT)
                .commit();
    }
}