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

/**
 *
 */
public abstract class ColorRectangleFragment extends Fragment {

    private static final String LOG_TAG = "abstract_fragment";

    private int rectangleColor;

    private CardView rectangleCardView;

    public abstract int getInitRectangleColor();

    public abstract void saveRectangleColor(int color);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        rectangleCardView = new CardView(getActivity());
        rectangleCardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if (rectangleColor == 0) {
            rectangleColor = getInitRectangleColor();
            saveRectangleColor(rectangleColor);
        }
        rectangleCardView.setBackgroundColor(rectangleColor);
        registerForContextMenu(rectangleCardView);
        Log.d(LOG_TAG, "onCreateView() = " + getClass().getSimpleName());
        return rectangleCardView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(LOG_TAG, "onCreateContextMenu()|" + getClass().getSimpleName());
        for (int i = 0; i < ColorsForFragments.getColorsForFragments().getSize(); i++) {
            int menuItemColor = ColorsForFragments.getColorsForFragments()
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
            int color = (ColorsForFragments.getColorsForFragments()
                    .getFragmentColor(itemId).getValueColor());
            setRectangleColor(color);
            saveRectangleColor(color);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    public void setRectangleColor(int rectangleColor) {
        this.rectangleColor = rectangleColor;
        this.rectangleCardView.setBackgroundColor(rectangleColor);
        Log.d(LOG_TAG, "setRectangleColor() = " + getClass().getSimpleName() +
                "| rectangleCardView = " + rectangleCardView.hashCode());
    }

    public void setRectangleVisibility(int visibility) {
        rectangleCardView.setVisibility(visibility);
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
        String nameColor = ColorsForFragments.getColorsForFragments().getFragmentColor(index).getNameColor();
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