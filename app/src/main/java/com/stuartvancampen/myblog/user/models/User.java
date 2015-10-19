package com.stuartvancampen.myblog.user.models;

import android.util.JsonReader;
import android.util.JsonToken;

import com.stuartvancampen.myblog.util.MyObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Stuart on 15/10/2015.
 */
public class User extends MyObject {
    private static final String ROOT_JSON = "user";

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String ID = "id";

    private String mName;
    private String mEmail;
    private Long mId;


    public User(String jsonString) {
        super(jsonString);
    }

    public User(JsonReader reader) {
        super(reader);
    }

    @Override
    public void loadFromJson(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name == null) {
                reader.skipValue();
            }
            else if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
            }
            else if (name.equals(NAME)) {
                mName = reader.nextString();
            }
            else if (name.equals(EMAIL)) {
                mEmail = reader.nextString();
            }
            else if (name.equals(ID)) {
                mId = reader.nextLong();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    @Override
    public JSONObject loadToJson() {
        JSONObject user = new JSONObject();
        addIfNotNull(user, NAME, String.valueOf(mName));
        addIfNotNull(user, EMAIL, String.valueOf(mEmail));
        addIfNotNull(user, ID, String.valueOf(mId));
        return user;
    }

    @Override
    public String getRootJson() {
        return ROOT_JSON;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public long getId() {
        return mId == null ? 0 : mId;
    }
}
