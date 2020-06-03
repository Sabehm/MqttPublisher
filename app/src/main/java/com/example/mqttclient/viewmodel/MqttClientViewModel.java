package com.example.mqttclient.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

import com.example.mqttclient.repositories.DeviceInfoJsonRepository;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class MqttClientViewModel extends AndroidViewModel {

    private static final String TAG = "MqttClientViewModel";

    private DeviceInfoJsonRepository deviceInfoJsonRepository;

    private String clientId;
    private MqttAndroidClient client;

    public MqttClientViewModel(@NonNull Application application) {
        super(application);
        deviceInfoJsonRepository = DeviceInfoJsonRepository.getInstance(getApplication().getApplicationContext());
    }

    public void mqttConnection() {
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getApplication().getApplicationContext(), "tcp://broker.hivemq.com:1883",
                clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String publish(String topic, LifecycleOwner lifecycleOwner) throws JSONException {
        String payload = deviceInfoJsonRepository.getDeviceInfo(lifecycleOwner);
        byte[] encodedPayload;
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
        return payload;
    }
}
