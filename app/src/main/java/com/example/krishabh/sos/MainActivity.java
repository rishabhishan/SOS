package com.example.krishabh.sos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AccelerometerListener {


    TextView tv;
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forceinput);
        //tv = (TextView) findViewById(R.id.display);
    }

    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub

    }

    public void onShake(double force) {

            //tv.setText(tv.getText().toString().concat("\nMotion detected for i = "+(i++) + "with force =  "+force));



    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
                Toast.LENGTH_SHORT).show();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isSupported(this)) {

            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
           // AccelerometerManager.stopListening();

            Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            //AccelerometerManager.stopListening();

            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
