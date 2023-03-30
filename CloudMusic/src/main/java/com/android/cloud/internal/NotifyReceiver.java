package com.android.cloud.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

public class NotifyReceiver extends BroadcastReceiver {
    public static final String TAG = "NotifyReceiver";
    public static final String RECEIVER_ACTION = "NotifyReceiver";
    public static final String EXTRA_KEY = "method";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras.isEmpty()) {
            return;
        }
        String method = extras.getString(EXTRA_KEY);
        Toast.makeText(context, "onReceive: code " + method, Toast.LENGTH_SHORT).show();
        switch (method) {
            case "nightMode":
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                break;


        }
    }
}
