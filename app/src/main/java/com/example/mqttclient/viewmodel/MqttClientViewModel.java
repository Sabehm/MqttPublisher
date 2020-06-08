package com.example.mqttclient.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

import com.example.mqttclient.R;
import com.example.mqttclient.repositories.DeviceInfoJsonRepository;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import static java.nio.charset.StandardCharsets.*;

public class MqttClientViewModel extends AndroidViewModel {

    private static final String TAG = "MqttClientViewModel";

    private DeviceInfoJsonRepository deviceInfoJsonRepository;

    private MqttAndroidClient client;

    public MqttClientViewModel(@NonNull Application application) {
        super(application);
        deviceInfoJsonRepository = DeviceInfoJsonRepository.getInstance(getApplication().getApplicationContext());
    }

    public void mqttConnection() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getApplication().getApplicationContext(),
                getApplication().getApplicationContext().getResources().getString(R.string.brokerIp),
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
    public String publish(String topic, LifecycleOwner lifecycleOwner, Context context) throws JSONException {
        String payload = deviceInfoJsonRepository.getDeviceInfo(lifecycleOwner, context);
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
