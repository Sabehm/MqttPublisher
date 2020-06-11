package com.example.mqttclient.repositories;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.mqttclient.MqttApplication;

import org.json.JSONException;

public class JobSensor extends JobService {
    private static Boolean stop = false;

    private static final String TAG = "JobSensor";

/*
    override fun onStartJob(params: JobParameters?): Boolean {

        MqttApplication.application.deviceInfoJsonRepository.register()
        android.os.Handler().postDelayed( {
            if(!stop) {
                val kpiService = OneTimeWorkRequest.Builder(KpiWorker::class.java).addTag(TAG_WORKER_KPI).build()
                WorkManager.getInstance().enqueue(kpiService)
                UtilsJob.startJobLocation(this)
            }
        }, 1000)


        return true
    }*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters params) {
        MqttApplication.application.deviceInfoJsonRepository.registerSensors(getApplicationContext());
        String topic = "Hola";
        String payload = null;

        try {
            payload = MqttApplication.application.publish(topic);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onViewCreated: Pressed... Trying to send: " +
                payload + " (topic: " + topic + ")");

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}