package com.example.mqttclient.repositories.system;

import android.os.Build;

public class Release {

    private static Release instance;

    public static Release getInstance() {
        if (instance == null) {
            instance = new Release();
        }
        return instance;
    }

    public String getRelease() {
        return Build.VERSION.RELEASE;
    }
}
