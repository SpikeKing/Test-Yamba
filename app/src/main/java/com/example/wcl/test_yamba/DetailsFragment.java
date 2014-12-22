package com.example.wcl.test_yamba;

import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wangchenlong on 14-12-22.
 */
public class DetailsFragment extends Fragment {

    private TextView mTextUser;
    private TextView mTextMessage;
    private TextView mTextCreatedAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item, null, false);

        mTextUser = (TextView) view.findViewById(R.id.list_item_text_user);
        mTextMessage = (TextView) view.findViewById(R.id.list_item_text_message);
        mTextCreatedAt = (TextView) view.findViewById(R.id.list_item_text_created_at);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        long id = getActivity().getIntent().getLongExtra(StatusContract.Column.ID, -1);

        updateView(id);
    }

    public void updateView(long id) {
        if (id == -1) {
            mTextUser.setText("");
            mTextMessage.setText("");
            mTextCreatedAt.setText("");
            return;
        }

        Uri uri = ContentUris.withAppendedId(StatusContract.CONTENT_URI, id);

        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

        if (!cursor.moveToFirst())
            return;

        String uer = cursor.getString(cursor.getColumnIndex(StatusContract.Column.USER));
        String message = cursor.getString(cursor.getColumnIndex(StatusContract.Column.MESSAGE));
        long createdAt = cursor.getLong(cursor.getColumnIndex(StatusContract.Column.CREATED_AT));

        mTextUser.setText(uer);
        mTextMessage.setText(message);
        mTextCreatedAt.setText(DateUtils.getRelativeTimeSpanString(createdAt));
    }

}
