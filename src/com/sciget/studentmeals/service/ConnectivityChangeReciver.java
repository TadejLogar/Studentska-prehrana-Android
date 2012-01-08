package com.sciget.studentmeals.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityChangeReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, UpdateService.class);
        context.startService(startServiceIntent);
    }
}