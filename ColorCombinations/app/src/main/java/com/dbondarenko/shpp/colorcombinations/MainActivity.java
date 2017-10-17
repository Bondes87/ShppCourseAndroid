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
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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

/**
 * File: MainActivity.java
 * The activity that is displayed when the program is started.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "main_activity";

    // The tags for color fragments.
    private static final String TOP_LEFT_FRAGMENT =
            "com.dbondarenko.shpp.colorcombinations.TopLeftFragment";
    private static final String TOP_RIGHT_FRAGMENT =
            "com.dbondarenko.shpp.colorcombinations.TopRightFragment";
    private static final String BOTTOM_FRAGMENT = "" +
            "com.dbondarenko.shpp.colorcombinations.BottomFragment";
    // The size of circle for the context menu item.
    private static final int CIRCLE_SIZE_FOR_MENU_ITEM = 60;
    // The context menu header.
    private static final String CONTEXT_MENU_HEADER = "Select the color";

    // Containers for placing fragments.
    @BindView(R.id.frameLayoutTopLeftFragment)
    FrameLayout frameLayoutTopLeftFragment;
    @BindView(R.id.frameLayoutTopRightFragment)
    FrameLayout frameLayoutTopRightFragment;
    @BindView(R.id.frameLayoutBottomFragment)
    FrameLayout frameLayoutBottomFragment;

    // View which contains the selected fragment for which the context menu is called.
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            Color colorForMenuItem;
            if (fragment.isHidden()) {
                colorForMenuItem = ColorsManager.getColorsManager()
                        .getColorDefaultOfOptionMenuItem();
            } else {
                colorForMenuItem = ColorsManager.getColorsManager()
                        .getUsedColor(fragment.getTag());
            }
            setColorOfOptionMenuItem(menu.getItem(i), colorForMenuItem);
        }
        return super.onPrepareOptionsMenu(menu);
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
        menu.setHeaderTitle(CONTEXT_MENU_HEADER);
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

    /**
     * Sets the color of the menu item.
     *
     * @param menuItem         The item of menu.
     * @param colorForMenuItem The color for changing the color of the menu item.
     */
    private void setColorOfOptionMenuItem(MenuItem menuItem, Color colorForMenuItem) {
        Log.d(LOG_TAG, "setColorOfOptionMenuItem()");
        if (menuItem == null) {
            Log.e(LOG_TAG, "setFragmentVisibility(): menuItem is null!!!");
            return;
        }
        if (colorForMenuItem == null) {
            Log.e(LOG_TAG, "setFragmentVisibility(): fragmentTag is null!!!");
            return;
        }
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(colorForMenuItem.getValueColor()), 0, s.length(), 0);
        menuItem.setTitle(s);
    }

    /**
     * Sets the check boxes in the options menu if
     * the corresponding fragment is displayed on the screen.
     *
     * @param menu The menu for which want to set the checkboxes.
     */
    private void setCheckboxesInOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "setCheckboxesInOptionsMenu()");
        if (menu == null) {
            Log.e(LOG_TAG, "setCheckboxesInOptionsMenu(): menu is null!!!");
            return;
        }
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).isHidden()) {
                menu.getItem(i).setChecked(false);
            } else {
                menu.getItem(i).setChecked(true);
            }
        }
    }

    /**
     * Registers a context menu to be shown for the given views.
     */
    private void registerViewsForContextMenu() {
        Log.d(LOG_TAG, "registerViewsForContextMenu()");
        registerForContextMenu(frameLayoutBottomFragment);
        registerForContextMenu(frameLayoutTopLeftFragment);
        registerForContextMenu(frameLayoutTopRightFragment);
    }

    /**
     * Changes the color of the fragment.
     *
     * @param colorIndex  The color index is the index of the context menu item.
     *                    He is necessary to get the right color from a set of colors.
     * @param fragmentTag The fragment tag for which want to change the color.
     */
    private void changeFragmentColor(int colorIndex, String fragmentTag) {
        Log.d(LOG_TAG, "changeFragmentColor()");
        if (TextUtils.isEmpty(fragmentTag)) {
            Log.e(LOG_TAG, "changeFragmentColor(): fragmentTag is null!!!");
            return;
        }
        ColorFragment selectedColorFragment = (ColorFragment) getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
        int newColorValue = ColorsManager.getColorsManager()
                .getAvailableColor(colorIndex, fragmentTag)
                .getValueColor();
        selectedColorFragment.setBackgroundColorValue(newColorValue);
    }

    /**
     * Returns the name and icon of a shortcut menu item with a Spannable object.
     * An icon is a circle, the color of which is extracted from the object Color.
     *
     * @param color The color, which contains the name of the color and the color value.
     * @return Spannable object that contains the name and icon of the shortcut menu item.
     */
    private Spannable getContextMenuItem(Color color) {
        Log.d(LOG_TAG, "getContextMenuItem()");
        Spannable spannableMenuItem = new SpannableString("  - " + color.getNameColor());
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color.getValueColor());
        circle.setBounds(0, 0, CIRCLE_SIZE_FOR_MENU_ITEM, CIRCLE_SIZE_FOR_MENU_ITEM);
        spannableMenuItem.setSpan(new ImageSpan(circle), 0, 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableMenuItem;
    }

    /**
     * Sets the visibility of a fragment, depending on the value of
     * the corresponding checkbox from the options menu. If the checkbox
     * is selected, then the fragment is shown on the screen, otherwise
     * it is hidden.
     *
     * @param menuItem        The option menu item.
     * @param fragmentManager The Fragment manager.
     * @param fragmentTag     The fragment tag for which want to change the color.
     */
    private void setFragmentVisibility(MenuItem menuItem,
                                       FragmentManager fragmentManager,
                                       String fragmentTag) {
        Log.d(LOG_TAG, "setFragmentVisibility()");
        if (menuItem == null) {
            Log.e(LOG_TAG, "setFragmentVisibility(): menuItem is null!!!");
            return;
        }
        if (fragmentManager == null) {
            Log.e(LOG_TAG, "setFragmentVisibility(): fragmentManager is null!!!");
            return;
        }
        if (TextUtils.isEmpty(fragmentTag)) {
            Log.e(LOG_TAG, "setFragmentVisibility(): fragmentTag is null!!!");
            return;
        }
        ColorFragment selectedColorFragment =
                (ColorFragment) fragmentManager.findFragmentByTag(fragmentTag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (menuItem.isChecked()) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .hide(selectedColorFragment);
            ColorsManager.getColorsManager().setAvailableColor(fragmentTag);
        } else {
            int colorValue = ColorsManager.getColorsManager()
                    .getUsedColor(fragmentTag)
                    .getValueColor();
            selectedColorFragment.setBackgroundColorValue(colorValue);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .show(selectedColorFragment);
        }
        fragmentTransaction.commit();
        menuItem.setChecked(!menuItem.isChecked());
    }

    /**
     * Initialization fragments.
     */
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