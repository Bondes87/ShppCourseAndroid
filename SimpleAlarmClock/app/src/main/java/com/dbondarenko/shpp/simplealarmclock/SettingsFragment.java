package com.dbondarenko.shpp.simplealarmclock;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * File: SettingsFragment.java
 * The fragment that displays the contents of the preferences.
 * Created by Dmitro Bondarenko on 12.09.2017.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_alarm);
    }
}