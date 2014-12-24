package com.example.wcl.test_yamba;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * 控件
 * <p/>
 * Created by C.L.Wang on 14-12-24.
 */
public class YambaWidget extends AppWidgetProvider {

    private static final String TAG =
            "DEBUG-WCL: " + YambaWidget.class.getSimpleName();

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        this.onUpdate(context, appWidgetManager,
                appWidgetManager.getAppWidgetIds(new ComponentName(context, YambaWidget.class)));

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate");

        Cursor cursor = context.getContentResolver().query(
                StatusContract.CONTENT_URI, null, null, null,
                StatusContract.DEFAULT_SORT
        );

        if (!cursor.moveToFirst())
            return;

        String user = cursor.getString(
                cursor.getColumnIndex(StatusContract.Column.USER)
        );

        String message = cursor.getString(
                cursor.getColumnIndex(StatusContract.Column.MESSAGE)
        );

        long createdAt = cursor.getLong(
                cursor.getColumnIndex(StatusContract.Column.CREATED_AT)
        );

        PendingIntent operation = PendingIntent.getActivity(context, -1,
                new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.widget);

            views.setTextViewText(R.id.list_item_text_user, user);
            views.setTextViewText(R.id.list_item_text_message, message);
            views.setTextViewText(R.id.list_item_text_created_at,
                    DateUtils.getRelativeTimeSpanString(createdAt));
            views.setOnClickPendingIntent(R.id.list_item_text_user, operation);
            views.setOnClickPendingIntent(R.id.list_item_text_message, operation);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


}
