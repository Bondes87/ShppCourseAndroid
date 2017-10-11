package com.dbondarenko.shpp.colorcombinations;

/**
 * File: ColorsForFragments.java
 * Created by Dmitro Bondarenko on 11.10.2017.
 */
public class ColorsForFragments {

    private static ColorsForFragments colorsForFragments;
    private FragmentColor[] arrayColorsOfFragments;

    private ColorsForFragments() {
        arrayColorsOfFragments = new FragmentColor[7];
        arrayColorsOfFragments[0] = new FragmentColor("red", 0xFFFF0000);
        arrayColorsOfFragments[1] = new FragmentColor("orange", 0xFFFFA500);
        arrayColorsOfFragments[2] = new FragmentColor("yellow", 0xFFFFFF00);
        arrayColorsOfFragments[3] = new FragmentColor("green", 0xFF00FF00);
        arrayColorsOfFragments[4] = new FragmentColor("cyan", 0xFF00FFFF);
        arrayColorsOfFragments[5] = new FragmentColor("blue", 0xFF0000FF);
        arrayColorsOfFragments[6] = new FragmentColor("purple", 0xFF800080);
    }

    static ColorsForFragments getColorsForFragments() {
        if (colorsForFragments == null) {
            colorsForFragments = new ColorsForFragments();
        }
        return colorsForFragments;
    }

    public FragmentColor[] getColorsOfFragments() {
        return arrayColorsOfFragments;
    }

    public FragmentColor getFragmentColor(int index) {
        return arrayColorsOfFragments[index];
    }

    public int getSize() {
        return arrayColorsOfFragments.length;
    }
}
