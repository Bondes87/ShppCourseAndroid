package com.dbondarenko.shpp.colorcombinations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class ColorFragment extends Fragment {

    private static final String LOG_TAG = "abstract_fragment";

    private static final String INIT_RECTANGLE_COLOR =
            "com.dbondarenko.shpp.colorcombinations.InitRectangleColor";

    private int colorValue;

    private CardView cardView;

    public static ColorFragment newInstance(int colorValue) {
        ColorFragment colorFragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(INIT_RECTANGLE_COLOR, colorValue);
        colorFragment.setArguments(args);
        return colorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorValue = getArguments().getInt(INIT_RECTANGLE_COLOR);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        cardView = new CardView(getActivity());
        cardView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        cardView.setBackgroundColor(colorValue);
        registerForContextMenu(cardView);
        return cardView;
    }

    /*@Override
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
            setColorValue(color);
            //saveRectangleColor(color);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }*/

  /*  public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
        this.cardView.setBackgroundColor(colorValue);
        Log.d(LOG_TAG, "setColorValue() = " + getClass().getSimpleName() +
                "| cardView = " + cardView.hashCode());
    }
*/
   /* public void setRectangleVisibility(int visibility) {
        cardView.setVisibility(visibility);
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
*/
    /*private Spannable getMenuItemName(int index, int color) {
        String nameColor = ColorsManager.getColorsManager().getFragmentColor(index).getNameColor();
        Spannable spannable = new SpannableString("  - " + nameColor);
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color);
        circle.setIntrinsicHeight(120);
        circle.setIntrinsicWidth(120);
        circle.setBounds(0, 0, 120, 120);
        spannable.setSpan(new ImageSpan(circle), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }*/
}