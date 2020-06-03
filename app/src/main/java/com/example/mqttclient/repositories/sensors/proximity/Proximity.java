package com.example.mqttclient.repositories.sensors.proximity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Proximity implements SensorEventListener {

    private static Proximity instance;

    private SensorManager sensorManager;
    private Sensor proximity;

    private static MutableLiveData<ArrayList<Float>> proximityValues;

    public static Proximity getInstance(Context context) {
        if (instance == null) {
            instance = new Proximity(context);
        }
        return instance;
    }

    public Proximity(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximityValues = new MutableLiveData<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (proximity != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            proximityValues.setValue(arrayList);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public LiveData<ArrayList<Float>> getLightValues() {
        return proximityValues;
    }

    public void register(){
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensorManager.unregisterListener(this);
    }
}