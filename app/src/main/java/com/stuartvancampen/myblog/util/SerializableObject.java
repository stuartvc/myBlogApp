package com.stuartvancampen.myblog.util;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Stuart on 17/10/2015.
 */
public interface SerializableObject {

    void loadFromJson(JsonReader reader) throws IOException;
    JSONObject loadToJson();
    String getRootJson();
}
