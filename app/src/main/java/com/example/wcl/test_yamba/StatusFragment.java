package com.example.wcl.test_yamba;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

/**
 * Created by wangchenlong on 14-12-12.
 */
public class StatusFragment extends Fragment {

    private static final String TAG = "DEBUG-WCL: StatusFragment";

    private EditText mEditStatus;
    private Button mButtonTweet;
    private TextView mTextCounter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        mEditStatus = (EditText)view.findViewById(R.id.edit_status);
        mButtonTweet = (Button)view.findViewById(R.id.button_tweet);
        mTextCounter = (TextView)view.findViewById(R.id.text_counter);

        mButtonTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = mEditStatus.getText().toString();
                new PostTask().execute(status);
            }
        });

        mEditStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - mEditStatus.length();
                mTextCounter.setText(Integer.toString(count));
                mTextCounter.setTextColor(Color.GREEN);
                if (count < 50)
                    mTextCounter.setTextColor(Color.YELLOW);
                else if (count < 10)
                    mTextCounter.setTextColor(Color.RED);
            }
        });

        return view;
    }

    private final class PostTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "Posting", "Please wait...");
            progress.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
//            YambaClient yambaCloud = new YambaClient("student", "password");
//            try {
//                yambaCloud.postStatus(params[0]);
//                return "Successfully posted";
//            } catch (YambaClientException e) {
//                e.printStackTrace();
//                return "Failed to post to yamba service";
//            }

            try {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String username = prefs.getString(getString(R.string.pref_username), "");
                String password = prefs.getString(getString(R.string.pref_password), "");

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                    return "Please update your username and password";
                }

                YambaClient cloud = new YambaClient(username, password);
                cloud.postStatus(params[0]);
                Log.d(TAG, "Successfully posted to the cloud: " + params[0]);
                return "Successfully posted";
            } catch (Exception e) {
                Log.e(TAG, "Failed to post to the cloud", e);
                e.printStackTrace();
                return "Failed to post";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            if (getActivity() != null && s != null) {
                Toast.makeText(StatusFragment.this.getActivity(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
