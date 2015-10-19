package com.stuartvancampen.myblog.post;

import android.util.JsonReader;
import android.util.JsonToken;

import com.stuartvancampen.myblog.util.MyObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Stuart on 15/10/2015.
 */
public class Post extends MyObject {
    private static final String ROOT_JSON = "post";

    private static final String BODY = "body";
    private static final String TITLE = "title";
    private static final String ID = "id";

    private String mBody;
    private String mTitle;
    private Long mId;

    public Post(String jsonString) {
        super(jsonString);
    }

    public Post(JsonReader reader) {
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
            else if (name.equals(BODY)) {
                mBody = reader.nextString();
            }
            else if (name.equals(TITLE)) {
                mTitle = reader.nextString();
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
        addIfNotNull(user, TITLE, String.valueOf(mTitle));
        addIfNotNull(user, BODY, String.valueOf(mBody));
        addIfNotNull(user, ID, String.valueOf(mId));
        return user;
    }

    @Override
    public String getRootJson() {
        return ROOT_JSON;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public long getId() {
        return mId == null ? 0 : mId;
    }
}
