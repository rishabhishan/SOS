package com.example.krishabh.sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by krishabh on 05/02/17.
 */

public class RestartSensorBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //System.out.println("*** inside broadcast recieved");
        Intent service = new Intent(context, BackgroundService.class);
        context.startService(service);
    }
}
