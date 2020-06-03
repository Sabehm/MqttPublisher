package com.example.mqttclient.repositories.sensors.light;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Light implements SensorEventListener {

    private static Light instance;

    private SensorManager sensorManager;
    private Sensor light;

    private static MutableLiveData<ArrayList<Float>> lightValues;

    public static Light getInstance(Context context) {
        if (instance == null) {
            instance = new Light(context);
        }
        return instance;
    }

    public Light(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightValues = new MutableLiveData<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (light != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            lightValues.setValue(arrayList);
        }
        else {
            lightValues.setValue(null);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public LiveData<ArrayList<Float>> getLightValues() {
        return lightValues;
    }

    public void register(){
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensorManager.unregisterListener(this);
    }
}
