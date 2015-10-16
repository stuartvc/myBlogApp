package com.stuartvancampen.myblog.util;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Stuart on 25/09/2015.
 */
public abstract class MyList<T extends MyObject> extends ArrayList<T> {

    public static MyList newList(Class<MyList> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public MyList() {
    }

    public void loadList(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name == null) {
                reader.skipValue();
            }
            else if (name.equals(getListRoot())) {
                reader.beginArray();
                while(reader.hasNext()) {
                    this.add(createObject(reader));
                }
                reader.endArray();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    public abstract T createObject(JsonReader reader);
    public abstract String getListRoot();
}
