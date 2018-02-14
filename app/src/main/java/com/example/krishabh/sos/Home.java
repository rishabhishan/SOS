package com.example.krishabh.sos;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by krishabh on 12/09/16.
 */
public class Home extends AppCompatActivity {


    TextView tv;
    ImageView iv;
    EditText et;
    LinearLayout etLayout;
    boolean on = false;
    SharedPreferenceActivity sh;
    Context ctx;
    Intent mServiceIntent;
    private BackgroundService mSensorService;
    Intent serviceIntent;

    public Context getCtx() {
        return ctx;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.home);
        sh = new SharedPreferenceActivity(this);
        tv = (TextView) findViewById(R.id.info);
        iv = (ImageView) findViewById(R.id.lockicon);
        et = (EditText) findViewById(R.id.emergency_message);
        etLayout = (LinearLayout) findViewById(R.id.etLayout);
        et.setText(sh.returnEmergencyMessage(this));
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sh.saveEmergencyMessage(Home.this,et.getText().toString());
            }
        });

      /*
        mSensorService = new BackgroundService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }


        serviceIntent = new Intent(this, AccelerometerService.class);
        startService(serviceIntent);

        */
        startService(new Intent(this, AccelerometerService.class));


    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    public void lock(View v) {

        if (on) {
            sh.saveOnOff(this,false);
            on = false;
            iv.setImageResource(R.mipmap.ic_lock_open_white_48dp);
            tv.setText("SOS service is OFF");

        } else {
            sh.saveOnOff(this,true);
            on = true;
            iv.setImageResource(R.mipmap.ic_lock_outline_white_48dp);
            tv.setText("SOS service is ON");


        }

    }

    @Override
    protected void onStop() {
        super.onDestroy();
        stopService(new Intent(this, AccelerometerService.class));
        startService(new Intent(this, AccelerometerService.class));
        System.out.println("**** Ondestroy called for Home class");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, AccelerometerService.class));
        startService(new Intent(this, AccelerometerService.class));
        System.out.println("**** Ondestroy called for Home class");


    }


    public void forceInput(View v) {

        Intent i = new Intent(this, ForceInput.class);
        startActivity(i);

    }


    public void emergencyContacts(View v) {


        Intent i = new Intent(this, EmergencyContacts.class);
        startActivity(i);

    }

    boolean etVisible = false;

    public void emergencyMessage(View v) {

        if(etVisible){
            etVisible=false;
            etLayout.setVisibility(View.GONE);

        }
        else {
            etVisible = true;
            etLayout.setVisibility(View.VISIBLE);

        }

    }
}
