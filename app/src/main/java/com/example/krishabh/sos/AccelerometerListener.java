package com.example.krishabh.sos;

/**
 * Created by krishabh on 27/08/16.
 */
public interface AccelerometerListener {

    public void onAccelerationChanged(float x, float y, float z);

    public void onShake(double force);

}