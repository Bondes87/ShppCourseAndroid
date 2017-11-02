package com.dbondarenko.shpp.cookislands.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.fragments.PageFragment;
import com.dbondarenko.shpp.cookislands.models.IslandModel;

import java.util.ArrayList;

/**
 * File: ContentFragmentPagerAdapter.java
 * The class that is responsible for providing data about the Cook Islands.
 * Created by Dmitro Bondarenko on 31.10.2017.
 */
public class ContentFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String LOG_TAG = ContentFragmentPagerAdapter.class.getSimpleName();

    private ArrayList<IslandModel> arrayListIslands;

    public ContentFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        arrayListIslands = CookIslandsSQLiteManager.getIslands(context);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(LOG_TAG, "getItem()");
        return PageFragment.newInstance(arrayListIslands.get(position).getUrl());
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount()");
        return arrayListIslands.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(LOG_TAG, "getPageTitle()");
        return arrayListIslands.get(position).getName();
    }
}