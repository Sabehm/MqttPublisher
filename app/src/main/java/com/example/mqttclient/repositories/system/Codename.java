package com.example.mqttclient.repositories.system;

import android.os.Build;

public class Codename {

    private static Codename instance;

    public static Codename getInstance() {
        if (instance == null) {
            instance = new Codename();
        }
        return instance;
    }

    public String getCodename() {
        return Build.VERSION.CODENAME;
    }
}
