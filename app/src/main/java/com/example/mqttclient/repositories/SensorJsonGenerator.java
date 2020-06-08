package com.example.mqttclient.repositories;

import android.content.Context;

import com.example.mqttclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SensorJsonGenerator {

    public JSONObject generate(ArrayList<Float> floatArrayList, Context context, String type) {

        JSONObject object = new JSONObject();
        JSONObject values = new JSONObject();

        int counter = 1;

        if (!floatArrayList.isEmpty()) {
            try {
                for (float aFloat : floatArrayList) {
                    values.put(context.getResources().getString(R.string.value) + counter, aFloat);
                    counter++;
                }
                object.put(context.getResources().getString(R.string.type), type);
                object.put(context.getResources().getString(R.string.values), values);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            return null;
        }
        return object;
    }
}
