package com.stuartvancampen.myblog.comment;

import android.util.JsonReader;

import com.stuartvancampen.myblog.util.MyObject;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Stuart on 15/10/2015.
 */
public class Comment extends MyObject {
    private static final String ROOT_JSON = "post";

    private static final String BODY = "body";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";

    private String mBody;
    private Long mId;
    private Long mUserId;

    public Comment(String jsonString) {
        super(jsonString);
    }

    public Comment(JsonReader reader) {
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
            else if (name.equals(BODY)) {
                mBody = reader.nextString();
            }
            else if (name.equals(ID)) {
                mId = reader.nextLong();
            }
            else if (name.equals(USER_ID)) {
                mUserId = reader.nextLong();
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
        addIfNotNull(user, BODY, String.valueOf(mBody));
        addIfNotNull(user, ID, String.valueOf(mId));
        addIfNotNull(user, USER_ID, String.valueOf(mUserId));
        return user;
    }

    @Override
    protected String getRootJson() {
        return ROOT_JSON;
    }

    public String getBody() {
        return mBody;
    }
}
