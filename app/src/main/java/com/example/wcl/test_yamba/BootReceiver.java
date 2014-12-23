package com.example.wcl.test_yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 自启动的接受器，更新Tweet的数据
 *
 * Created by wangchenlong on 14-12-23.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG =
            "DEBUG-WCL: " + BootReceiver.class.getSimpleName();

    private static final long DEFAULT_INTERVAL =
            AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        long interval = Long.parseLong(prefs.getString("interval", Long.toString(DEFAULT_INTERVAL)));

        PendingIntent operation = PendingIntent.getService(context, -1,
                new Intent(context, RefreshService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (interval == 0) {
            alarmManager.cancel(operation);
            Log.e(TAG, "cancelling repeat operation");
        } else {
            alarmManager.setInexactRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(), interval, operation);
            Log.e(TAG, "setting repeat operation for: " + interval);
        }

        Log.e(TAG, "onReceived");
    }
}
