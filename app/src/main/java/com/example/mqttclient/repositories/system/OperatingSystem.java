package com.example.mqttclient.repositories.system;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class OperatingSystem {

    private static OperatingSystem instance;

    public static OperatingSystem getInstance() {
        if (instance == null) {
            instance = new OperatingSystem();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getOS() {
        return Build.VERSION.BASE_OS;
    }
}
