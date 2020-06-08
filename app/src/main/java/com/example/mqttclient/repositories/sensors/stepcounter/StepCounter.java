package com.example.mqttclient.repositories.sensors.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class StepCounter implements SensorEventListener {

    private static StepCounter instance;

    private SensorManager sensorManager;
    private Sensor stepCounter;

    private static MutableLiveData<ArrayList<Float>> stepCounterValues;

    public static StepCounter getInstance(Context context) {
        if (instance == null) {
            instance = new StepCounter(context);
        }
        return instance;
    }

    public StepCounter(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepCounterValues = new MutableLiveData<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (stepCounter != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            stepCounterValues.setValue(arrayList);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public LiveData<ArrayList<Float>> getStepCounterValues() {
        return stepCounterValues;
    }

    public void register() {
        sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }
}
