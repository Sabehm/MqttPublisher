package com.example.mqttclient.repositories;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.mqttclient.repositories.sensors.light.Light;
import com.example.mqttclient.repositories.sensors.accelerometer.Accelerometer;
import com.example.mqttclient.repositories.sensors.magneticfield.MagneticField;
import com.example.mqttclient.repositories.sensors.proximity.Proximity;
import com.example.mqttclient.repositories.sensors.stepcounter.StepCounter;
import com.example.mqttclient.repositories.system.Codename;
import com.example.mqttclient.repositories.system.Incremental;
import com.example.mqttclient.repositories.system.OperatingSystem;
import com.example.mqttclient.repositories.system.Release;
import com.example.mqttclient.repositories.system.SecurityPatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DeviceInfoJsonRepository {

    private static DeviceInfoJsonRepository instance;

    private OperatingSystem operatingSystem;
    private SecurityPatch securityPatch;
    private Codename codename;
    private Release release;
    private Incremental incremental;
    private Accelerometer accelerometer;
    private MagneticField magneticField;
    private Light light;
    private Proximity proximity;
    private StepCounter stepCounter;

    private JSONObject deviceInfo;
    private JSONArray sensors;
    private JSONObject sensorDetail;

    public static DeviceInfoJsonRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DeviceInfoJsonRepository(context);
        }
        return instance;
    }

    public DeviceInfoJsonRepository(Context context) {
        operatingSystem = OperatingSystem.getInstance();
        securityPatch = SecurityPatch.getInstance();
        codename = Codename.getInstance();
        release = Release.getInstance();
        incremental = Incremental.getInstance();
        accelerometer = Accelerometer.getInstance(context);
        magneticField = MagneticField.getInstance(context);
        light = Light.getInstance(context);
        proximity = Proximity.getInstance(context);
        stepCounter = StepCounter.getInstance(context);
        deviceInfo = new JSONObject();
        sensorDetail = new JSONObject();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getDeviceInfo(LifecycleOwner lifecycleOwner) throws JSONException {

        deviceInfo.put("os", operatingSystem.getOS());
        deviceInfo.put("securityPatch", securityPatch.getLastSecurityPatch());
        deviceInfo.put("codename", codename.getCodename());
        deviceInfo.put("release", release.getRelease());
        deviceInfo.put("incremental", incremental.getIncremental());
        deviceInfo.put("sensors", getSensors(lifecycleOwner));

        return deviceInfo.toString();
    }

    private JSONArray getSensors(LifecycleOwner lifecycleOwner) {
        sensors = new JSONArray();
        accelerometer.getAccelerometerValues().observe(lifecycleOwner, new Observer<ArrayList<Float>>() {
            @Override
            public void onChanged(ArrayList<Float> floatArrayList) {
                if ((sensorDetail = new SensorJsonGenerator().generate(floatArrayList, "accelerometer")) != null) {
                    sensors.put(sensorDetail);
                }
            }
        });

        magneticField.getMagneticFieldValues().observe(lifecycleOwner, new Observer<ArrayList<Float>>() {
            @Override
            public void onChanged(ArrayList<Float> floatArrayList) {
                if ((sensorDetail = new SensorJsonGenerator().generate(floatArrayList, "magneticField")) != null) {
                    sensors.put(sensorDetail);
                }
            }
        });

        light.getLightValues().observe(lifecycleOwner, new Observer<ArrayList<Float>>() {
            @Override
            public void onChanged(ArrayList<Float> floatArrayList) {
                if ((sensorDetail = new SensorJsonGenerator().generate(floatArrayList, "light")) != null) {
                    sensors.put(sensorDetail);
                }
            }
        });

        proximity.getLightValues().observe(lifecycleOwner, new Observer<ArrayList<Float>>() {
            @Override
            public void onChanged(ArrayList<Float> floatArrayList) {
                if ((sensorDetail = new SensorJsonGenerator().generate(floatArrayList, "proximity")) != null) {
                    sensors.put(sensorDetail);
                }
            }
        });

        stepCounter.getStepCounterValues().observe(lifecycleOwner, new Observer<ArrayList<Float>>() {
            @Override
            public void onChanged(ArrayList<Float> floatArrayList) {
                if ((sensorDetail = new SensorJsonGenerator().generate(floatArrayList, "stepCounter")) != null) {
                    sensors.put(sensorDetail);
                }
            }
        });

        return sensors;
    }
}
