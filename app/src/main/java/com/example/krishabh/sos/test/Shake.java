package com.example.krishabh.sos.test;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by krishabh on 01/09/16.
 */
public class Shake implements SensorEventListener {

    private final Listener listener;
    private Sensor accelerometer;
    private SensorManager sensorManager;
    boolean after_effects = false;
    double last_acceleration = 0;
    private static final float SHAKE_THRESHOLD = 30.25f; // m/S**2
    private static final boolean userinput = true;

    public boolean start(SensorManager sensorManager) {


        if (accelerometer != null) {
            return true;
        }

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // If this phone has an accelerometer, listen to it.
        if (accelerometer != null) {
            this.sensorManager = sensorManager;
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
        return accelerometer != null;
    }

    public void stop() {
        if (accelerometer != null) {
            sensorManager.unregisterListener(this, accelerometer);
            sensorManager = null;
            accelerometer = null;
        }
    }

    public Shake(Listener listener) {
        this.listener = listener;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        System.out.println("****  Inside onsensorchanged  shake class");
        final float alpha = 0.8f;
        float gravity[] = {0, 0, 0};
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        float x = event.values[0] - gravity[0];
        float y = event.values[1] - gravity[1];
        float z = event.values[2] - gravity[2];

        System.out.println("Values for X =  " + x + "   Y  = " + y + "  Z  = " + z);


        double acceleration = Math.sqrt(Math.pow(x, 2) +
                Math.pow(y, 2) +
                Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;


        if (!userinput) {
            if (acceleration < last_acceleration - 10)
                after_effects = false;


            if (after_effects)
                return;
        }


        System.out.println("kjsdhfdskjfhsjkdfh");
        //System.out.println("****   inside accel " + acceleration);

        if (Math.abs(acceleration) > SHAKE_THRESHOLD) {
            // mLastShakeTime = curTime;
            System.out.println("****   Acceleration is " + acceleration + "m/s^2");
            listener.onShake();
            last_acceleration = acceleration;
            after_effects = true;
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
