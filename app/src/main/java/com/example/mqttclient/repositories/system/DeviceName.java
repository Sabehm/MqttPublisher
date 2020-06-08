package com.example.mqttclient.repositories.system;

import android.os.Build;

public class DeviceName {

    private static DeviceName instance;

    public static DeviceName getInstance() {
        if (instance == null) {
            instance = new DeviceName();
        }
        return instance;
    }

    public String getDeviceName() {
        return Build.MODEL;
    }
}
