package com.example.krishabh.sos;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.krishabh.sos.MainActivity;
import com.example.krishabh.sos.test.Listener;
import com.example.krishabh.sos.test.Shake;

/**
 * Created by krishabh on 01/09/16.
 */
public class BackgroundService extends Service implements AccelerometerListener {


    public BackgroundService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }
    public BackgroundService() {
    }

    @Override
    public void onCreate()
    {

        System.out.println("**** Service created calling onCreate");
        if(!AccelerometerManager.isListening()){

            if (AccelerometerManager.isSupported(this)) {

                AccelerometerManager.startListening(this);
                Toast.makeText(getBaseContext(), "Accelerometer Started",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        System.out.println("*** Inside onstartcommand");
        if(!AccelerometerManager.isListening()){

            if (AccelerometerManager.isSupported(this)) {

                AccelerometerManager.startListening(this);
                Toast.makeText(getBaseContext(), "Accelerometer Started",
                        Toast.LENGTH_SHORT).show();

            }
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("*** Inside onDestroy backgroundservice");
        if (AccelerometerManager.isListening()) {

            AccelerometerManager.stopListening();
            Toast.makeText(getBaseContext(), "Accelerometer Stopped",
                    Toast.LENGTH_SHORT).show();

        }

        Intent broadcastIntent = new Intent("com.example.krishabh.sos.RestartSensor");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

        System.out.println("*****   Acc changed");

    }

    @Override
    public void onShake(double force) {

        Toast.makeText(getBaseContext(), "Phone shaked",
                Toast.LENGTH_SHORT).show();

        System.out.println("***** Inside onShake  " + String.format("%.2f", force));

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
