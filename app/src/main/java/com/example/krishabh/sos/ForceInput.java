package com.example.krishabh.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by krishabh on 02/09/16.
 */

public class ForceInput extends AppCompatActivity implements AccelerometerListener {


    TextView textView;
    LinearLayout ll;
    private SharedPreferences mSharedPreferences;
    SharedPreferenceActivity sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forceinput);
        sh = new SharedPreferenceActivity(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView = (TextView) findViewById(R.id.forcetext);
        AccelerometerManager.configure(10.0f);

        textView.setText(sh.returnForceValue(this));
        ll = (LinearLayout) findViewById(R.id.ll);
        startAccelerometer();

    }


    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(double force) {

        textView.setText("" + String.format("%.2f", force));

    }


    public void startAccelerometer(){
        if(!AccelerometerManager.isListening()){

        if (AccelerometerManager.isSupported(this)) {

            AccelerometerManager.startListening(this);
            Toast.makeText(getBaseContext(), "Accelerometer Started",
                    Toast.LENGTH_SHORT).show();

        }}

    }

    public void stopAccelerometer(){

        if (AccelerometerManager.isListening()) {

            AccelerometerManager.stopListening();
            Toast.makeText(getBaseContext(), "Accelerometer Stopped",
                    Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onResume() {

        super.onResume();
        startAccelerometer();

    }

    @Override
    public void onStop() {

        super.onStop();
        stopAccelerometer();

    }

    @Override
    public void onPause(){

        super.onStop();
        stopAccelerometer();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAccelerometer();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.userinput, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.done:
                sh.saveForceValue(this, textView.getText().toString());
                Snackbar snackbar = Snackbar
                        .make(ll, "Shake force configured", Snackbar.LENGTH_LONG) .setAction("HOME", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                stopAccelerometer();
                                Intent intent = new Intent(ForceInput.this,Home.class);
                                startActivity(intent);

                            }
                        });;

                snackbar.show();
                return true;

            case R.id.reset:
                textView.setText("0.00");
                stopAccelerometer();
                startAccelerometer();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
