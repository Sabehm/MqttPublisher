package com.example.mqttclient;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.mqttclient.repositories.DeviceInfoJsonRepository;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttApplication extends Application {


    private MqttAndroidClient client;
    public DeviceInfoJsonRepository deviceInfoJsonRepository;
    public static MqttApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        mqttConnection();
        application = this;
        deviceInfoJsonRepository = DeviceInfoJsonRepository.getInstance(this);
    }

    public void mqttConnection() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this, getResources().getString(R.string.brokerIp), clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("TAG", "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("TAG", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String publish(String topic) throws JSONException {
        String payload = deviceInfoJsonRepository.getDeviceInfo(this);
        byte[] encodedPayload;
        try {
            encodedPayload = payload.getBytes(UTF_8);
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return payload;
    }
}
