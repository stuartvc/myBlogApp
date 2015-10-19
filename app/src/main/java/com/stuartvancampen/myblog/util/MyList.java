package com.stuartvancampen.myblog.util;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Stuart on 25/09/2015.
 */
public abstract class MyList<T extends MyObject> extends ArrayList<T>  implements SerializableObject {

    public MyList() {
    }

    public void loadFromJson(JsonReader reader) throws IOException {
        reader.beginArray();
        while(reader.hasNext()) {
            this.add(createObject(reader));
        }
        reader.endArray();
    }

    @Override
    public JSONObject loadToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int index = 0; index < size(); index++) {
            jsonArray.put(this.get(index).loadToJson());
        }
        try {
            return new JSONObject().put(getRootJson(), jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract T createObject(JsonReader reader);
}
