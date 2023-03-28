package com.android.cloud.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NotifyReceiver extends BroadcastReceiver {
    public static final String RECEIVER_ACTION = "NotifyReceiver";
    public static final String EXTRA_KEY = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras.isEmpty()) {
            return;
        }
        String method = extras.getString(EXTRA_KEY);
    }
}
