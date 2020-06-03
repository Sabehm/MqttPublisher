package com.example.mqttclient.repositories.system;

import android.os.Build;

public class Incremental {

    private static Incremental instance;

    public static Incremental getInstance() {
        if (instance == null) {
            instance = new Incremental();
        }
        return instance;
    }

    public String getIncremental() {
        return Build.VERSION.INCREMENTAL;
    }
}
