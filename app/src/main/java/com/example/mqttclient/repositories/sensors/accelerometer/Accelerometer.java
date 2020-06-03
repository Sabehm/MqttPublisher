package com.example.mqttclient.repositories.sensors.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Accelerometer implements SensorEventListener {

    private static Accelerometer instance;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private static MutableLiveData<ArrayList<Float>> accelerometerValues;

    public static Accelerometer getInstance(Context context) {
        if (instance == null) {
            instance = new Accelerometer(context);
        }
        return instance;
    }

    public Accelerometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometerValues = new MutableLiveData<>();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(accelerometer != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            arrayList.add(sensorEvent.values[1]);
            arrayList.add(sensorEvent.values[2]);
            accelerometerValues.setValue(arrayList);
        }
        else {
            accelerometerValues.setValue(null);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public LiveData<ArrayList<Float>> getAccelerometerValues() {
        return accelerometerValues;
    }

    public void register(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensorManager.unregisterListener(this);
    }
}
