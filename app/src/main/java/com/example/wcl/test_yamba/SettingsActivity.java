package com.example.wcl.test_yamba;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by wangchenlong on 14-12-12.
 */
public class SettingsActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            SettingsFragment fragment = new SettingsFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
