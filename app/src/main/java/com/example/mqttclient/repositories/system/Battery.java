package com.example.mqttclient.repositories.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Battery extends BroadcastReceiver {

    private static Battery instance;

    private IntentFilter intentFilter;

    private static MutableLiveData<String> batteryLevel;

    public static Battery getInstance() {
        if (instance == null) {
            instance = new Battery();
        }
        return instance;
    }

    public Battery() {
        batteryLevel = new MutableLiveData<>();
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        int batteryPercentage = (level * 100) / scale;
        batteryLevel.setValue(batteryPercentage + "%");
    }

    public LiveData<String> getBatteryLevel() {
        return batteryLevel;
    }

    public void register(Context context){
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context){
        context.unregisterReceiver(this);
    }
}
