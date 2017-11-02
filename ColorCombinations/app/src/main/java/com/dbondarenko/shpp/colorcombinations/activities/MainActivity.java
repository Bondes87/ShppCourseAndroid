package com.dbondarenko.shpp.colorcombinations.activities;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.dbondarenko.shpp.colorcombinations.Constants;
import com.dbondarenko.shpp.colorcombinations.R;
import com.dbondarenko.shpp.colorcombinations.fragments.ColorFragment;
import com.dbondarenko.shpp.colorcombinations.models.ColorChangeEventModel;
import com.dbondarenko.shpp.colorcombinations.models.ColorModel;
import com.dbondarenko.shpp.colorcombinations.utils.ColorsManager;
import com.dbondarenko.shpp.colorcombinations.utils.OptionsMenuState;
import com.dbondarenko.shpp.colorcombinations.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: MainActivity.java
 * The activity that is displayed when the program is started.
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Containers for placing fragments.
    @BindView(R.id.frameLayoutTopLeftFragment)
    public FrameLayout frameLayoutTopLeftFragment;
    @BindView(R.id.frameLayoutTopRightFragment)
    public FrameLayout frameLayoutTopRightFragment;
    @BindView(R.id.frameLayoutBottomFragment)
    public FrameLayout frameLayoutBottomFragment;

    // View which contains the selected fragment for which the context menu is called.
    private View viewSelectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            ColorsManager.getColorsManager().initColors(getApplicationContext());
            initFragments();
            OptionsMenuState.getOptionsMenuState().initCheckboxSettingsOfMenuItem();
        }
        registerViewsForContextMenu();
    }

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
                setFragmentVisibility(item, fragmentManager, Constants.TOP_LEFT_FRAGMENT);
                return true;
            case R.id.topRightFragment:
                setFragmentVisibility(item, fragmentManager, Constants.TOP_RIGHT_FRAGMENT);
                return true;
            case R.id.bottomFragment:
                setFragmentVisibility(item, fragmentManager, Constants.BOTTOM_FRAGMENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean[] checkboxesSettings = OptionsMenuState.getOptionsMenuState()
                .getCheckboxesSettingsForMenuItems();
        for (int i = 0; i < checkboxesSettings.length; i++) {
            ColorModel colorForMenuItem;
            if (checkboxesSettings[i]) {
                colorForMenuItem = ColorsManager.getColorsManager()
                        .getUsedColor(getSupportFragmentManager()
                                .getFragments().get(i).getTag());

            } else {
                colorForMenuItem = ColorsManager.getColorsManager()
                        .getColorDefaultOfOptionMenuItem(getApplicationContext());
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
        ArrayList<ColorModel> arrayListColors = ColorsManager
                .getColorsManager().getAvailableColors();
        for (int i = 0; i < arrayListColors.size(); i++) {
            ColorModel colorOfContextMenuItem = arrayListColors.get(i);
            menu.add(Menu.NONE, i, Menu.NONE, getContextMenuItem(colorOfContextMenuItem));
        }
        menu.setHeaderTitle(Constants.CONTEXT_MENU_HEADER);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onContextItemSelected() = ");
        boolean[] checkboxesSettings = OptionsMenuState.getOptionsMenuState()
                .getCheckboxesSettingsForMenuItems();
        int itemId = item.getItemId();
        if (viewSelectedFragment.equals(frameLayoutTopLeftFragment) &&
                checkboxesSettings[0]) {
            changeFragmentColor(itemId, Constants.TOP_LEFT_FRAGMENT);
            return true;
        }
        if (viewSelectedFragment.equals(frameLayoutTopRightFragment) &&
                checkboxesSettings[1]) {
            changeFragmentColor(itemId, Constants.TOP_RIGHT_FRAGMENT);
            return true;
        }
        if (viewSelectedFragment.equals(frameLayoutBottomFragment) &&
                checkboxesSettings[2]) {
            changeFragmentColor(itemId, Constants.BOTTOM_FRAGMENT);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Sets the color of the menu item.
     *
     * @param menuItem         The item of menu.
     * @param colorForMenuItem The color for changing the color of the menu item.
     */
    private void setColorOfOptionMenuItem(MenuItem menuItem, ColorModel colorForMenuItem) {
        Log.d(LOG_TAG, "setColorOfOptionMenuItem()");
        Util.checkForNull(menuItem, colorForMenuItem);
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
        Util.checkForNull(menu);
        boolean[] checkboxesSettings = OptionsMenuState.getOptionsMenuState()
                .getCheckboxesSettingsForMenuItems();
        for (int i = 0; i < checkboxesSettings.length; i++) {
            if (checkboxesSettings[i]) {
                menu.getItem(i).setChecked(true);
            } else {
                menu.getItem(i).setChecked(false);
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
        Util.checkStringToNull(fragmentTag);
        int newColorValue = ColorsManager.getColorsManager()
                .getAvailableColor(colorIndex, fragmentTag)
                .getValueColor();
        EventBus.getDefault().post(new ColorChangeEventModel(newColorValue, fragmentTag));
    }

    /**
     * Returns the name and icon of a shortcut menu item with a Spannable object.
     * An icon is a circle, the color of which is extracted from the object ColorModel.
     *
     * @param color The color, which contains the name of the color and the color value.
     * @return Spannable object that contains the name and icon of the shortcut menu item.
     */
    private Spannable getContextMenuItem(ColorModel color) {
        Log.d(LOG_TAG, "getContextMenuItem()");
        Util.checkForNull(color);
        Spannable spannableMenuItem = new SpannableString("  - " + color.getNameColor());
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color.getValueColor());
        circle.setBounds(0, 0, Constants.CIRCLE_SIZE_FOR_MENU_ITEM,
                Constants.CIRCLE_SIZE_FOR_MENU_ITEM);
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
        Util.checkForNull(menuItem, fragmentManager);
        Util.checkStringToNull(fragmentTag);
        ColorFragment selectedColorFragment =
                (ColorFragment) fragmentManager.findFragmentByTag(fragmentTag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (menuItem.isChecked()) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .hide(selectedColorFragment);
            ColorsManager.getColorsManager().setAvailableColor(fragmentTag);
        } else {
            int colorValue = ColorsManager.getColorsManager().getUsedColor(fragmentTag)
                    .getValueColor();
            EventBus.getDefault().post(new ColorChangeEventModel(colorValue, fragmentTag));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .show(selectedColorFragment);
        }
        fragmentTransaction.commit();
        menuItem.setChecked(!menuItem.isChecked());
        OptionsMenuState.getOptionsMenuState().setCheckboxSettingsOfMenuItem(
                menuItem.getOrder(), menuItem.isChecked());
    }

    /**
     * Initialization fragments.
     */
    private void initFragments() {
        Log.d(LOG_TAG, "initFragments()");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutTopLeftFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager()
                                .getRandomAvailableColor(Constants.TOP_LEFT_FRAGMENT)
                                .getValueColor()), Constants.TOP_LEFT_FRAGMENT)
                .add(R.id.frameLayoutTopRightFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager()
                                .getRandomAvailableColor(Constants.TOP_RIGHT_FRAGMENT)
                                .getValueColor()), Constants.TOP_RIGHT_FRAGMENT)
                .add(R.id.frameLayoutBottomFragment,
                        ColorFragment.newInstance(ColorsManager.getColorsManager()
                                .getRandomAvailableColor(Constants.BOTTOM_FRAGMENT)
                                .getValueColor()), Constants.BOTTOM_FRAGMENT)
                .commit();
    }
}