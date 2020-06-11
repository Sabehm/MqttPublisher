package com.example.mqttclient.repositories;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mqttclient.R;
import com.example.mqttclient.repositories.sensors.light.Light;
import com.example.mqttclient.repositories.sensors.accelerometer.Accelerometer;
import com.example.mqttclient.repositories.sensors.magneticfield.MagneticField;
import com.example.mqttclient.repositories.sensors.proximity.Proximity;
import com.example.mqttclient.repositories.sensors.stepcounter.StepCounter;
import com.example.mqttclient.repositories.system.Battery;
import com.example.mqttclient.repositories.system.Codename;
import com.example.mqttclient.repositories.system.DeviceName;
import com.example.mqttclient.repositories.system.Incremental;
import com.example.mqttclient.repositories.system.OperatingSystem;
import com.example.mqttclient.repositories.system.Release;
import com.example.mqttclient.repositories.system.SecurityPatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfoJsonRepository {

    private static DeviceInfoJsonRepository instance;

    private DeviceName deviceName;
    private OperatingSystem operatingSystem;
    private Battery battery;
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
    private JSONObject sensorDetail;

    public static DeviceInfoJsonRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DeviceInfoJsonRepository(context);
        }
        return instance;
    }

    public DeviceInfoJsonRepository(Context context) {
        deviceName = DeviceName.getInstance();
        operatingSystem = OperatingSystem.getInstance();
        battery = Battery.getInstance();
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
    public String getDeviceInfo(final Context context) throws JSONException {

        deviceInfo.put(context.getResources().getString(R.string.model), deviceName.getDeviceName());
        deviceInfo.put(context.getResources().getString(R.string.os), operatingSystem.getOS());
        deviceInfo.put(context.getResources().getString(R.string.battery), battery.getBatteryLevel());
        deviceInfo.put(context.getResources().getString(R.string.securityPatch), securityPatch.getLastSecurityPatch());
        deviceInfo.put(context.getResources().getString(R.string.codename), codename.getCodename());
        deviceInfo.put(context.getResources().getString(R.string.release), release.getRelease());
        deviceInfo.put(context.getResources().getString(R.string.incremental), incremental.getIncremental());
        deviceInfo.put(context.getResources().getString(R.string.sensors), getSensors(context));

        return deviceInfo.toString();
    }

    private JSONArray getSensors(Context context) {
        JSONArray sensors = new JSONArray();

        if ((sensorDetail = new SensorJsonGenerator().generate(accelerometer.getAccelerometerValues(), context,
                context.getResources().getString(R.string.accelerometer))) != null) {
            sensors.put(sensorDetail);
        }

        if ((sensorDetail = new SensorJsonGenerator().generate(magneticField.getMagneticFieldValues(), context,
                context.getResources().getString(R.string.magneticField))) != null) {
            sensors.put(sensorDetail);
        }

        if ((sensorDetail = new SensorJsonGenerator().generate(light.getLightValues(), context,
                context.getResources().getString(R.string.light))) != null) {
            sensors.put(sensorDetail);
        }

        if ((sensorDetail = new SensorJsonGenerator().generate(proximity.getLightValues(), context,
                context.getResources().getString(R.string.proximity))) != null) {
            sensors.put(sensorDetail);
        }

        if ((sensorDetail = new SensorJsonGenerator().generate(stepCounter.getStepCounterValues(), context,
                context.getResources().getString(R.string.stepCounter))) != null) {
            sensors.put(sensorDetail);
        }

        return sensors;
    }

    public void registerSensors(Context context) {
        battery.register(context);
        accelerometer.register();
        magneticField.register();
        light.register();
        proximity.register();
        stepCounter.register();
    }
}
