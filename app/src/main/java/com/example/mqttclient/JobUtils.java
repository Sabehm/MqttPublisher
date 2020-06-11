package com.example.mqttclient;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.example.mqttclient.repositories.JobSensor;

public class JobUtils {

    private static final String TAG = "JobUtils";

    public void scheduleJob (Context context) {
        ComponentName componentName = new ComponentName(context, JobSensor.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
        .setRequiresCharging(false)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        .setPersisted(true)
        .setPeriodic(15 * 60 * 1000)
        .build();

        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);

        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        }
        else {
            Log.d(TAG, "Job scheduled failed");
        }
    }

    public void cancelJob(Context context) {
            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            scheduler.cancel(123);
            Log.d(TAG, "Job cancelled");
            }
}
/*
fun startJobLocation(context: Context) {
    JobSensor.stop =false
    val componentName  = ComponentName(context, JobSensor.class)
    val builder = JobInfo.Builder(1232343, componentName)
    val  jobScheduler : JobScheduler? = generateJobScheduler(context)
//            val  jobScheduler : JobScheduler? = JobScheduler.getInstance(context)
    with(builder){
        setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        setMinimumLatency(10)
        setOverrideDeadline(10) // maximum delay
    }
    jobScheduler?.schedule(builder.build())
}

fun stopJobLocation(context: Context) {
    try {
        JobSensor.stop =true
        val jobScheduler: JobScheduler? = generateJobScheduler(context)
//                val jobScheduler: JobScheduler? = JobScheduler.getInstance(context)

        if(jobScheduler != null) {
            if (jobScheduler.allPendingJobs.size > 0) {
                for ( jobInfo in jobScheduler.allPendingJobs) {
                    if (jobInfo.id == 1232343) {
                        jobScheduler.cancel(jobInfo.id)
                        break
                    }
                }
            }
        }
    }catch (e:Exception){}

}

fun validateProcess(context: Context) {
    val jobScheduler: JobScheduler? = generateJobScheduler(context)
//            val jobScheduler: JobScheduler? = JobScheduler.getInstance(context)
    if(jobScheduler != null) {
        var enable = false
        if (jobScheduler.allPendingJobs.size > 0) {
            for ( jobInfo in jobScheduler.allPendingJobs) {
                if (jobInfo.id == 1232343) {
                    enable = true
                    break } } }

        if ( !enable )
            startJobLocation(context)//1800000
    }
}

private fun generateJobScheduler(context: Context) : JobScheduler? {
    return context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
}*/
