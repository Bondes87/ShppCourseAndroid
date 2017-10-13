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

    @BindView(R.id.frameLayoutTopLeftFragment)
    FrameLayout frameLayoutTopLeftFragment;
    @BindView(R.id.frameLayoutTopRightFragment)
    FrameLayout frameLayoutTopRightFragment;
    @BindView(R.id.frameLayoutBottomFragment)
    FrameLayout frameLayoutBottomFragment;

    private View viewSelectedFragment;

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
        viewSelectedFragment = v;
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
        if (viewSelectedFragment.equals(frameLayoutTopLeftFragment)) {
            changeFragmentColor(itemId, TOP_LEFT_FRAGMENT);
            return true;
        }
        if (viewSelectedFragment.equals(frameLayoutTopRightFragment)) {
            changeFragmentColor(itemId, TOP_RIGHT_FRAGMENT);
            return true;
        }
        if (viewSelectedFragment.equals(frameLayoutBottomFragment)) {
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
        Log.d(LOG_TAG, "setCheckboxesInOptionsMenu()");
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
        Log.d(LOG_TAG, "registerViewsForContextMenu()");
        registerForContextMenu(frameLayoutBottomFragment);
        registerForContextMenu(frameLayoutTopLeftFragment);
        registerForContextMenu(frameLayoutTopRightFragment);
    }

    private void changeFragmentColor(int colorIndex, String fragmentTag) {
        Log.d(LOG_TAG, "changeFragmentColor()");
        ColorFragment selectedColorFragment = (ColorFragment) getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
        int newColorValue = ColorsManager.getColorsManager()
                .getAvailableColor(colorIndex, fragmentTag)
                .getValueColor();
        selectedColorFragment.setContentColorValue(newColorValue);
    }

    private Spannable getContextMenuItem(Color color) {
        Log.d(LOG_TAG, "getContextMenuItem()");
        Spannable spannable = new SpannableString("  - " + color.getNameColor());
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color.getValueColor());
       /* circle.setIntrinsicHeight(60);
        circle.setIntrinsicWidth(60);*/
        circle.setBounds(0, 0, 60, 60);
        spannable.setSpan(new ImageSpan(circle), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private void setFragmentVisibility(MenuItem item,
                                       FragmentManager fragmentManager,
                                       String fragmentTag) {
        Log.d(LOG_TAG, "setFragmentVisibility()");
        ColorFragment selectedColorFragment =
                (ColorFragment) fragmentManager.findFragmentByTag(fragmentTag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (item.isChecked()) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.hide(selectedColorFragment);
            ColorsManager.getColorsManager().setAvailableColor(fragmentTag);
        } else {
            int colorValue = ColorsManager.getColorsManager().getUsedColor(fragmentTag)
                    .getValueColor();
            selectedColorFragment.setContentColorValue(colorValue);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.show(selectedColorFragment);
        }
        fragmentTransaction.commit();
        item.setChecked(!item.isChecked());
    }

    private void initFragments() {
        Log.d(LOG_TAG, "initFragments()");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutTopLeftFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getRandomAvailableColor(TOP_LEFT_FRAGMENT).getValueColor()),
                        TOP_LEFT_FRAGMENT)
                .add(R.id.frameLayoutTopRightFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getRandomAvailableColor(TOP_RIGHT_FRAGMENT).getValueColor()),
                        TOP_RIGHT_FRAGMENT)
                .add(R.id.frameLayoutBottomFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager().
                                getRandomAvailableColor(BOTTOM_FRAGMENT).getValueColor()),
                        BOTTOM_FRAGMENT)
                .commit();
    }
}