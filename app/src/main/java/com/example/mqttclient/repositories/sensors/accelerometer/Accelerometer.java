package com.example.mqttclient.repositories.sensors.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

public class Accelerometer implements SensorEventListener {

    private static Accelerometer instance;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private static ArrayList<Float> accelerometerValues;

    public static Accelerometer getInstance(Context context) {
        if (instance == null) {
            instance = new Accelerometer(context);
        }
        return instance;
    }

    private Accelerometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        accelerometerValues = new ArrayList<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(accelerometer != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            arrayList.add(sensorEvent.values[1]);
            arrayList.add(sensorEvent.values[2]);
            setAccelerometerValues(arrayList);
        }
        else {
            setAccelerometerValues(null);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public ArrayList<Float> getAccelerometerValues() {
        return accelerometerValues;
    }

    private void setAccelerometerValues(ArrayList<Float> arrayList) {
        accelerometerValues = arrayList;
    }

    public void register(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensorManager.unregisterListener(this);
    }
}
