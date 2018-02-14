package com.example.krishabh.sos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by krishabh on 27/08/16.
 */

public class AccelerometerManager {

    private static Context aContext = null;
    private static Sensor sensor;
    private static SensorManager sensorManager;
    private static AccelerometerListener listener;
    private static Boolean supported;
    private static boolean running = false;
    private static float SHAKE_THRESHOLD = 30.25f; // m/S**2



    public static boolean isListening() {

        return running;

    }

    public static void stopListening() {

        running = false;
        try {

            if (sensorManager != null && sensorEventListener != null) {

                sensorManager.unregisterListener(sensorEventListener);

            }

        } catch (Exception e) {

        }

    }

    public static boolean isSupported(Context context) {
        aContext = context;
        if (supported == null) {
            if (aContext != null) {


                sensorManager = (SensorManager) aContext.
                        getSystemService(Context.SENSOR_SERVICE);

                // Get all sensors in device
                List<Sensor> sensors = sensorManager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);

                supported = new Boolean(sensors.size() > 0);


            } else {
                supported = Boolean.FALSE;
            }
        }
        return supported;
    }


    public static void configure(float threshold) {
        AccelerometerManager.SHAKE_THRESHOLD = threshold;
    }


    public static void startListening(AccelerometerListener accelerometerListener) {

        sensorManager = (SensorManager) aContext.
                getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(
                Sensor.TYPE_ACCELEROMETER);

        if (sensors.size() > 0) {

            sensor = sensors.get(0);

            running = sensorManager.registerListener(
                    sensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

            listener = accelerometerListener;
        }


    }




    private static SensorEventListener sensorEventListener =
            new SensorEventListener() {
                boolean after_effects = false;
                double last_acceleration = 0;
                long currTime,lastTime = 0, timeDiff;
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }

                public void onSensorChanged(SensorEvent event) {



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
                        listener.onShake(acceleration);
                        last_acceleration = acceleration;

                    }


                    listener.onAccelerationChanged(x, y, z);
                }

            };


}




