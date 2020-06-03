package com.example.mqttclient.repositories;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SensorJsonGenerator {

    public JSONObject generate(ArrayList<Float> floatArrayList, String type) {

        JSONObject object = new JSONObject();
        JSONObject values = new JSONObject();

        int counter = 1;

        if (!floatArrayList.isEmpty()) {
            try {
                for (float aFloat : floatArrayList) {
                    values.put(String.valueOf(new StringBuilder("value").append(counter)), aFloat);
                    counter++;
                }
                object.put("type", type);
                object.put("values", values);
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
