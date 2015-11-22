package com.stuartvancampen.myblog.post;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.stuartvancampen.myblog.R;
import com.stuartvancampen.myblog.session.Session;
import com.stuartvancampen.myblog.user.models.User;
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
    private static final String USER = "user";

    private String mBody;
    private String mTitle;
    private Long mId;
    private User mUser;

    @SuppressWarnings("unused")
    public Post() {
    }

    public Post(String jsonString) {
        super(jsonString);
    }

    public Post(JsonReader reader) {
        super(reader);
    }

    public Post(String title, String body) {
        mBody = body;
        mTitle = title;
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
            else if (name.equals(USER)) {
                //Log.d("POST", reader.nextString());
                mUser = new User(reader);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    @Override
    public JSONObject loadToJson() {
        JSONObject post = new JSONObject();
        addIfNotNull(post, TITLE, String.valueOf(mTitle));
        addIfNotNull(post, BODY, String.valueOf(mBody));
        addIfNotNull(post, ID, String.valueOf(mId));
        addIfNotNull(post, USER, mUser);
        return post;
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

    public User getUser() {
        return mUser;
    }

    public static String getCreateUrl(Context context) {
        return context.getString(R.string.base_url) + context.getString(R.string.posts_url);
    }
}
