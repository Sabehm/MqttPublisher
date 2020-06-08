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

import com.example.mqttclient.repositories.sensors.light.Light;
import com.example.mqttclient.repositories.sensors.accelerometer.Accelerometer;
import com.example.mqttclient.repositories.sensors.magneticfield.MagneticField;
import com.example.mqttclient.repositories.sensors.proximity.Proximity;
import com.example.mqttclient.repositories.sensors.stepcounter.StepCounter;
import com.example.mqttclient.repositories.system.Battery;
import com.example.mqttclient.viewmodel.MqttClientViewModel;

import org.json.JSONException;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MqttClientFragment extends Fragment {

    private static final String TAG = "MqttClientFragment";

    private MqttClientViewModel mqttClientViewModel;
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
        battery = new Battery();
        accelerometer = new Accelerometer(Objects.requireNonNull(getContext()));
        magneticField = new MagneticField(Objects.requireNonNull(getContext()));
        light = new Light(Objects.requireNonNull(getContext()));
        proximity = new Proximity(Objects.requireNonNull(getContext()));
        stepCounter = new StepCounter(Objects.requireNonNull(getContext()));
        return inflater.inflate(R.layout.fragment_mqtt_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mqttClientViewModel = new MqttClientViewModel(Objects.requireNonNull(getActivity()).getApplication());

        Button button = view.findViewById(R.id.btnSend);
        final EditText editTopic = view.findViewById(R.id.edtTopic);

        mqttConnection();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view1) {
                if (!editTopic.getText().toString().equals(getResources().getString(R.string.nullable))) {
                    String payload = null;
                    try {
                        payload = publish(editTopic.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onViewCreated: Pressed... Trying to send: " +
                            payload + " (topic: " + editTopic.getText().toString() + ")");
                }
                else {
                    Toast emptyTopic = Toast.makeText(getActivity(),
                            getResources().getString(R.string.emptyTopic), Toast.LENGTH_LONG);
                    emptyTopic.show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        battery.register(Objects.requireNonNull(getContext()));
        accelerometer.register();
        magneticField.register();
        light.register();
        proximity.register();
        stepCounter.register();
    }

    @Override
    public void onPause() {
        battery.unregister(Objects.requireNonNull(getContext()));
        accelerometer.unregister();
        magneticField.unregister();
        light.unregister();
        proximity.unregister();
        stepCounter.unregister();
        super.onPause();
    }

    private void mqttConnection() {
        mqttClientViewModel.mqttConnection();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String publish(String topic) throws JSONException {
        return mqttClientViewModel.publish(topic, getViewLifecycleOwner(), Objects.requireNonNull(getContext()));
    }
}
