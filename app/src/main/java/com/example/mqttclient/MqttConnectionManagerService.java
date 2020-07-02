package com.example.mqttclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttConnectionManagerService extends Service {

    private MqttAndroidClient client;
    private MqttConnectOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        options = createMqttConnectOptions();
        client = createMqttAndroidClient();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.connect(client, options);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private MqttConnectOptions createMqttConnectOptions() {
        return new MqttConnectOptions();
    }

    private MqttAndroidClient createMqttAndroidClient() {
        String clientId = MqttClient.generateClientId();
        return new MqttAndroidClient(this, getResources().getString(R.string.brokerIp), clientId);
    }

    public void connect(final MqttAndroidClient client, MqttConnectOptions options) {

        try {
            if (!client.isConnected()) {
                IMqttToken token = client.connect(options);
                token.setActionCallback(new IMqttActionListener() {

                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d("TAG", "onSuccess");
                        String payload = "Hola XD";
                        byte[] encodedPayload;
                        encodedPayload = payload.getBytes(UTF_8);
                        MqttMessage message = new MqttMessage(encodedPayload);
                        try {
                            client.publish("Hola", message);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.d("TAG", "onFailure");
                    }
                });
                client.setCallback(new MqttCallback() {

                    @Override
                    public void connectionLost(Throwable cause) {

                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) {

                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });
            }
        } catch (MqttException e) {
            //handle e
        }
    }

}
