package com.stuartvancampen.myblog.util;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Stuart on 25/09/2015.
 */
public abstract class MyObject implements SerializableObject {

    private static final String TAG = MyObject.class.getSimpleName();

    public MyObject(String jsonString) {
        this(new JsonReader(new StringReader(jsonString)));
    }

    public MyObject(JsonReader reader){
        try {
            loadFromJson(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return loadToJson().toString();
    }

    protected static void addIfNotNull(JSONObject jsonObject, String tag, String value) {
        try {
            jsonObject.put(tag, value);
        } catch (JSONException e) {
            Log.d(TAG, "tag is null");
        }
    }
}
