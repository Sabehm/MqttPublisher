package com.example.mqttclient.repositories.system;

import android.os.Build;

public class SecurityPatch {

    private static SecurityPatch instance;

    public static SecurityPatch getInstance() {
        if (instance == null) {
            instance = new SecurityPatch();
        }
        return instance;
    }

    public String getLastSecurityPatch() {
        return Build.VERSION.SECURITY_PATCH;
    }
}
