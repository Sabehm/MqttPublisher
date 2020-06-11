package com.example.mqttclient;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mqttclient.repositories.JobSensor;
import com.example.mqttclient.repositories.sensors.light.Light;
import com.example.mqttclient.repositories.sensors.accelerometer.Accelerometer;
import com.example.mqttclient.repositories.sensors.magneticfield.MagneticField;
import com.example.mqttclient.repositories.sensors.proximity.Proximity;
import com.example.mqttclient.repositories.sensors.stepcounter.StepCounter;
import com.example.mqttclient.repositories.system.Battery;


/**
 * A simple {@link Fragment} subclass.
 */
public class MqttClientFragment extends Fragment {

    private static final String TAG = "MqttClientFragment";

    private Battery battery;
    private Accelerometer accelerometer;
    private MagneticField magneticField;
    private Light light;
    private Proximity proximity;
    private StepCounter stepCounter;

    public MqttClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*battery = new Battery();
        accelerometer = Accelerometer.getInstance(Objects.requireNonNull(getContext()));
        magneticField = MagneticField.getInstance(Objects.requireNonNull(getContext()));
        light = Light.getInstance(Objects.requireNonNull(getContext()));
        proximity = Proximity.getInstance(Objects.requireNonNull(getContext()));
        stepCounter = StepCounter.getInstance(Objects.requireNonNull(getContext()));*/
        return inflater.inflate(R.layout.fragment_mqtt_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.btnSend);
        final EditText editTopic = view.findViewById(R.id.edtTopic);
        JobUtils jobUtils = new JobUtils();

        jobUtils.scheduleJob(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
/*        battery.unregister(Objects.requireNonNull(getContext()));
        accelerometer.unregister();
        magneticField.unregister();
        light.unregister();
        proximity.unregister();
        stepCounter.unregister();*/
        super.onPause();
    }
}
