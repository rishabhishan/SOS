package com.example.krishabh.sos;

/**
 * Created by krishabh on 07/02/17.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.logging.Logger;

public class AccelerometerService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private float SHAKE_THRESHOLD = 10.0f;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("*** Inside onstartcommand for AccService");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {



    }
    double last_acceleration = 0;
    @Override
    public void onSensorChanged(SensorEvent event) {


        long currTime,lastTime = 0, timeDiff;
        currTime = System.currentTimeMillis();
        final float alpha = 0.8f;
        float gravity[] = {0,0,0};
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        float x = event.values[0] - gravity[0];
        float y = event.values[1] - gravity[1];
        float z = event.values[2] - gravity[2];

        System.out.println("Values for  X =  " + x + "   Y  = "+y+ "  Z  = "+z);


        double acceleration = Math.sqrt(Math.pow(x, 2) +
                Math.pow(y, 2) +
                Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;

        acceleration = Math.abs(acceleration);

        timeDiff = currTime - lastTime;

        if (acceleration < last_acceleration && timeDiff < 1.5)
            return;



        System.out.println("acc    =   "+acceleration);

        System.out.println("threshold    =   "+SHAKE_THRESHOLD);


        if (Math.abs(acceleration) > SHAKE_THRESHOLD) {
            lastTime = currTime;
            System.out.println("****   Acceleration is " + acceleration + "m/s^2");
            last_acceleration = acceleration;
            showNotification();

        }

    }



    @Override
    public void onDestroy() {
        
        System.out.println("**** Inside on destroy for AccService");

    }

    @Override
    public void onTaskRemoved(Intent intent) {
        System.out.println("**** Inside on Task Removed for AccService");



    }
    /**
     * show notification when Accel is more then the given int.
     */
    private void showNotification() {
        final NotificationManager mgr = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder note = new NotificationCompat.Builder(this);
        note.setContentTitle("Device Accelerometer Notification");
        note.setTicker("New Message Alert!");
        note.setAutoCancel(true);
        // to set default sound/light/vibrate or all
        note.setDefaults(Notification.DEFAULT_ALL);
        // Icon to be set on Notification
        note.setSmallIcon(R.mipmap.ic_launcher);
        // This pending intent will open after notification click
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                MainActivity.class), 0);
        // set pending intent to notification builder
        note.setContentIntent(pi);
        mgr.notify(101, note.build());
    }
}

