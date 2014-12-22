package com.example.wcl.test_yamba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by wangchenlong on 14-12-12.
 */
public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences prefs;
    public static final String INTENT_UPDATED_INTERVAL =
            "com.example.wcl.test_yamba.action.UPDATED_INTERVAL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        prefs.unregisterOnSharedPreferenceChangeListener(mListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener mListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    getActivity().sendBroadcast(new Intent(INTENT_UPDATED_INTERVAL));
                }
            };
}
