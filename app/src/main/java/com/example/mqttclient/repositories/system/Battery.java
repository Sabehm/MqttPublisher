package com.example.mqttclient.repositories.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class Battery extends BroadcastReceiver {

    private static Battery instance;

    private IntentFilter intentFilter;

    private static String batteryLevel;

    public static Battery getInstance() {
        if (instance == null) {
            instance = new Battery();
        }
        return instance;
    }

    public Battery() {
        batteryLevel = "";
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        int batteryPercentage = (level * 100) / scale;
        setBatteryLevel(batteryPercentage + "%");
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    private void setBatteryLevel(String string) {
        batteryLevel = string;
    }

    public void register(Context context){
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context){
        context.unregisterReceiver(this);
    }
}
