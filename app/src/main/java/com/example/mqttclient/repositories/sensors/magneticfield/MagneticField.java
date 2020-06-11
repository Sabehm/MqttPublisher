package com.example.mqttclient.repositories.sensors.magneticfield;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

public class MagneticField implements SensorEventListener {

    private static MagneticField instance;

    private SensorManager sensorManager;
    private Sensor magneticField;

    private static ArrayList<Float> magneticFieldsValues;

    public static MagneticField getInstance(Context context) {
        if (instance == null) {
            instance = new MagneticField(context);
        }
        return instance;
    }

    public MagneticField(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        magneticFieldsValues = new ArrayList<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(magneticField != null) {
            ArrayList<Float> arrayList = new ArrayList<>();
            arrayList.add(sensorEvent.values[0]);
            arrayList.add(sensorEvent.values[1]);
            arrayList.add(sensorEvent.values[2]);
            setMagneticFieldsValues(arrayList);
        }
        else {
            setMagneticFieldsValues(null);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public ArrayList<Float> getMagneticFieldValues() {
        return magneticFieldsValues;
    }

    private void setMagneticFieldsValues(ArrayList<Float> arrayList) {
        magneticFieldsValues = arrayList;
    }

    public void register(){
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensorManager.unregisterListener(this);
    }
}
