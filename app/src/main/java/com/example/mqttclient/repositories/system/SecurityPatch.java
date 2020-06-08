package com.example.mqttclient.repositories.system;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class SecurityPatch {

    private static SecurityPatch instance;

    public static SecurityPatch getInstance() {
        if (instance == null) {
            instance = new SecurityPatch();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getLastSecurityPatch() {
        return Build.VERSION.SECURITY_PATCH;
    }
}
