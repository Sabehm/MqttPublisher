package com.example.mqttclient.repositories.sensors.magneticfield;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MagneticField implements SensorEventListener {

    private static MagneticField instance;

    private SensorManager sensorManager;
    private Sensor magneticField;

    private static MutableLiveData<ArrayList<Float>> magneticFieldsValues;

    public static MagneticField getInstance(Context context) {
        if (instance == null) {
            instance = new MagneticField(context);
        }
        return instance;
    }

    public MagneticField(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        magneticFieldsValues = new MutableLiveData<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(magneticField != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            arrayList.add(sensorEvent.values[1]);
            arrayList.add(sensorEvent.values[2]);
            magneticFieldsValues.setValue(arrayList);
        }
        else {
            magneticFieldsValues.setValue(null);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public LiveData<ArrayList<Float>> getMagneticFieldValues() {
        return magneticFieldsValues;
    }

    public void register(){
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensorManager.unregisterListener(this);
    }
}
