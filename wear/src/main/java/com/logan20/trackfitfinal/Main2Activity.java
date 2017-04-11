package com.logan20.trackfitfinal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main2Activity extends WearableActivity implements SensorEventListener{

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);
    private static final int MY_PERMISSIONS_REQUEST_BODY_SENSORS = 1000;

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private GoogleApiClient client;
    private int prevVal;
    private final static String TAG = "ER";
    private SensorManager sensorManager;
    private Sensor heartSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BODY_SENSORS},MY_PERMISSIONS_REQUEST_BODY_SENSORS);
        }
        else{
            client = new GoogleApiClient.Builder(this)
                    .addApi(Wearable.API)
                    .build();
            client.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        heartSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(this,heartSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this,heartSensor);
        super.onPause();

    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.INVISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_HEART_RATE){
            String msg = "Heart Rate: " + (int)sensorEvent.values[0];
            if (mTextView!=null){
                mTextView.setText(msg);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(client).await();
                    for(Node node : nodes.getNodes()) {
                        Wearable.MessageApi.sendMessage(client, node.getId(), "/ListenerService", String.valueOf((int)sensorEvent.values[0]).getBytes()).await();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
