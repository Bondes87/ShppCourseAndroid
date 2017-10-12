package com.dbondarenko.shpp.colorcombinations;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 *
 */
public class ColorRectangleFragment extends Fragment {

    private static final String LOG_TAG = "abstract_fragment";
    private static final String INIT_RECTANGLE_COLOR =
            "com.dbondarenko.shpp.colorcombinations.InitRectangleColor";

    private int intRectangleColor;

    private CardView cardViewRectangle;

    public static ColorRectangleFragment newInstance(int colorFragment) {
        ColorRectangleFragment colorRectangleFragment = new ColorRectangleFragment();
        Bundle args = new Bundle();
        args.putInt(INIT_RECTANGLE_COLOR, colorFragment);
        colorRectangleFragment.setArguments(args);
        return colorRectangleFragment;
    }

    /*private void saveRectangleColor(int color){

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intRectangleColor = getArguments().getInt(INIT_RECTANGLE_COLOR);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        cardViewRectangle = new CardView(getActivity());
        cardViewRectangle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if (intRectangleColor == 0) {
            intRectangleColor = getInitRectangleColor();
            //saveRectangleColor(intRectangleColor);
        }
        cardViewRectangle.setBackgroundColor(intRectangleColor);
        registerForContextMenu(cardViewRectangle);
        Log.d(LOG_TAG, "onCreateView() = " + getClass().getSimpleName());
        return cardViewRectangle;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(LOG_TAG, "onCreateContextMenu()|" + getClass().getSimpleName());
        for (int i = 0; i < ColorsManager.getColorsManager().getSize(); i++) {
            int menuItemColor = ColorsManager.getColorsManager()
                    .getFragmentColor(i).getValueColor();
            if (isUsedColor(menuItemColor)) {
                menu.add(Menu.NONE, i, Menu.NONE, getMenuItemName(i, menuItemColor))
                        .setVisible(false);
            } else {
                menu.add(Menu.NONE, i, Menu.NONE, getMenuItemName(i, menuItemColor))
                        .setOnMenuItemClickListener(item -> {
                            onContextItemSelected(item);
                            return true;
                        });
            }
        }
        menu.setHeaderTitle("Select the color");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onContextItemSelected() = " + getClass().getSimpleName());
        int itemId = item.getItemId();
        if (itemId >= 0 && itemId < 7) {
            int color = (ColorsManager.getColorsManager()
                    .getFragmentColor(itemId).getValueColor());
            setIntRectangleColor(color);
            //saveRectangleColor(color);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    public void setIntRectangleColor(int intRectangleColor) {
        this.intRectangleColor = intRectangleColor;
        this.cardViewRectangle.setBackgroundColor(intRectangleColor);
        Log.d(LOG_TAG, "setIntRectangleColor() = " + getClass().getSimpleName() +
                "| cardViewRectangle = " + cardViewRectangle.hashCode());
    }

    public void setRectangleVisibility(int visibility) {
        cardViewRectangle.setVisibility(visibility);
    }

    private int getInitRectangleColor() {
        int color = FragmentsPreferences.getFragmentsPreferences().getBottomFragmentColor(getContext());
        if (color == -1) {
            Random random = new Random();
            return ColorsManager.getColorsManager()
                    .getFragmentColor(random.nextInt(7)).getValueColor();
        } else {
            return color;
        }
    }

    private boolean isUsedColor(int menuItemColor) {
        int[] usedFragmentsColors = FragmentsPreferences.getFragmentsPreferences()
                .getFragmentsColorsSettings(getContext());
        for (int fragmentColor : usedFragmentsColors) {
            if (fragmentColor == menuItemColor) {
                return true;
            }
        }
        return false;
    }

    private Spannable getMenuItemName(int index, int color) {
        String nameColor = ColorsManager.getColorsManager().getFragmentColor(index).getNameColor();
        Spannable spannable = new SpannableString("  - " + nameColor);
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color);
        circle.setIntrinsicHeight(120);
        circle.setIntrinsicWidth(120);
        circle.setBounds(0, 0, 120, 120);
        spannable.setSpan(new ImageSpan(circle), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}