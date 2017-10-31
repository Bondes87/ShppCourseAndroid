package com.dbondarenko.shpp.cookislands.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.dbondarenko.shpp.cookislands.database.CookIslandsSQLiteManager;
import com.dbondarenko.shpp.cookislands.fragments.PageFragment;

/**
 * File: ContentFragmentPagerAdapter.java
 * Created by Dmitro Bondarenko on 31.10.2017.
 */
public class ContentFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String LOG_TAG = ContentFragmentPagerAdapter.class.getSimpleName();

    private Context context;

    public ContentFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(LOG_TAG, "getItem()");
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount()");
        return CookIslandsSQLiteManager.getIslandsCount(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CookIslandsSQLiteManager.getIslandName(context,position+1);
    }
}