package com.example.mqttclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mqttclient.viewmodel.MqttClientViewModel;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MqttClientFragment extends Fragment {

    private static final String TAG = "MqttClientFragment";

    private MqttClientViewModel mqttClientViewModel;

    public MqttClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mqtt_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mqttClientViewModel = new MqttClientViewModel(Objects.requireNonNull(getActivity()).getApplication());

        Button button = view.findViewById(R.id.btnSend);
        final EditText editTopic = view.findViewById(R.id.edtTopic);
        final EditText editMessage = view.findViewById(R.id.edtMessage);

        mqttConnection();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Log.d(TAG, "onViewCreated: Pressed... Trying to send: " +
                        editMessage.getText().toString() + " (topic: " + editTopic.getText().toString() + ")");
                publish(editTopic.getText().toString(), android.os.Build.MODEL);
            }
        });
    }

    private void mqttConnection() {
        mqttClientViewModel.mqttConnection();
    }

    private void publish(String topic, String payload) {
        mqttClientViewModel.publish(topic, payload);
    }
}
