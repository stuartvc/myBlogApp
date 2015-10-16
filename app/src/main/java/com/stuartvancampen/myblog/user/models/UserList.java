package com.stuartvancampen.myblog.user.models;

import android.util.JsonReader;

import com.stuartvancampen.myblog.util.MyList;

/**
 * Created by Stuart on 13/10/2015.
 */
public class UserList extends MyList<UserPreview> {
    private static final String JSON_ROOT = "users";

    @Override
    public UserPreview createObject(JsonReader reader){
        return new UserPreview(reader);
    }

    @Override
    public String getListRoot() {
        return JSON_ROOT;
    }


}
