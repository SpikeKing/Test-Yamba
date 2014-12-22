package com.example.wcl.test_yamba;

import android.os.Bundle;

/**
 * Created by wangchenlong on 14-12-22.
 */
public class DetailsActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            DetailsFragment fragment = new DetailsFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
